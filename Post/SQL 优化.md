![[d79060fc050d60dea201639644d668aa.png]]

#### 1. 避免使用select *

第一，不需要的数据会增加网络IO传输的时间。
第二，select * 不会走覆盖索引，会出现大量的回表操作，降低查询sql的性能。

#### 2. 使用union all 代替union

union all 相比 union ，union 可以获取排除重复后的数据，需要cpu进行数据的遍历、排序和比价。union all 会出现重复的数据，如果业务逻辑不需要排重，则可以用union all 替换union。

#### 3. 小表驱动大表

假如有order和user两张表，其中order表有10000条数据，而user表有100条数据。

可以用`in`和`exists`关键字实现：

```sql
select * from order
where user_id in (select id from user where status=1)
```

```sql
select * from order
where exists (select 1 from user where order.user_id = user.id and status=1)
```

前面提到的这种业务场景，使用in关键字去实现业务需求，更加合适。

因为如果sql语句中包含了in关键字，则它会优先执行in里面的`子查询语句`，然后再执行in外面的语句。如果in里面的数据量很少，作为条件查询速度更快。

而如果sql语句中包含了exists关键字，它优先执行exists左边的语句（即主查询语句）。然后把它作为条件，去跟右边的语句匹配。如果匹配上，则可以查询出数据。如果匹配不上，数据就被过滤掉了。

这个需求中，order表有10000条数据，而user表有100条数据。order表是大表，user表是小表。如果order表在左边，则用in关键字性能更好。

总结一下：

- `in` 适用于左边大表，右边小表。
- `exists` 适用于左边小表，右边大表。

不管是用in，还是exists关键字，其核心思想都是用小表驱动大表。

#### 4 多用limit

需要查询某些数据中的第一条，比如：查询某个用户下的第一个订单，想看看他第一次的首单时间。

**反例：**

```js
select id, create_date 
 from order 
where user_id=123 
order by create_date asc;
```

根据用户id查询订单，按下单时间排序，先查出该用户所有的订单数据，得到一个订单集合。 然后在代码中，获取第一个元素的数据，即首单的数据，就能获取首单时间。

虽说这种做法在功能上没有问题，但它的效率非常不高，需要先查询出所有的数据，有点浪费资源。

**正例：**

```js
select id, create_date 
 from order 
where user_id=123 
order by create_date asc 
limit 1;
```

使用`limit 1`，只返回该用户下单时间最小的那一条数据即可。

#### 5 in中值太多

对于批量查询接口，我们通常会使用`in`关键字过滤出数据。比如：想通过指定的一些id，批量查询出用户信息。

sql语句如下：

```js
select id,name from category
where id in (1,2,3...100000000);
```

如果我们不做任何限制，该查询语句一次性可能会查询出非常多的数据，很容易导致接口超时。

这时该怎么办呢？

```js
select id,name from category
where id in (1,2,3...100)
limit 500;
```

可以在sql中对数据用limit做限制。

不过我们更多的是要在业务代码中加限制，伪代码如下：

```js
public List<Category> getCategory(List<Long> ids) {
   if(CollectionUtils.isEmpty(ids)) {
      return null;
   }
   if(ids.size() > 500) {
      throw new BusinessException("一次最多允许查询500条记录")
   }
   return mapper.getCategoryList(ids);
}
```


还有一个方案就是：如果ids超过500条记录，可以分批用多线程去查询数据。每批只查500条记录，最后把查询到的数据汇总到一起返回。

不过这只是一个临时方案，不适合于ids实在太多的场景。因为ids太多，即使能快速查出数据，但如果返回的数据量太大了，网络传输也是非常消耗性能的，接口性能始终好不到哪里去。

#### 6 用连接查询代替子查询

mysql中如果需要从两张以上的表中查询出数据的话，一般有两种实现方式：`子查询` 和 `连接查询`

子查询的例子如下：

```js
select * from order
where user_id in (select id from user where status=1)
```

子查询语句可以通过`in`关键字实现，一个查询语句的条件落在另一个select语句的查询结果中。程序先运行在嵌套在最内层的语句，再运行外层的语句。

子查询语句的优点是简单，结构化，如果涉及的表数量不多的话。

但缺点是mysql执行子查询时，需要创建临时表，查询完毕后，需要再删除这些临时表，有一些额外的性能消耗。

这时可以改成连接查询。 具体例子如下：

```js
select o.* from order o
inner join user u on o.user_id = u.id
where u.status=1
```

#### 7 join时要注意

我们在涉及到多张表联合查询的时候，一般会使用`join`关键字。

而join使用最多的是left join和inner join。

- `left join`：求两个表的交集外加左表剩下的数据。
- `inner join`：求两个表交集的数据。

使用inner join的示例如下：

```js
select o.id,o.code,u.name 
from order o 
inner join user u on o.user_id = u.id
where u.status=1;
```

如果两张表使用inner join关联，mysql会自动选择两张表中的小表，去驱动大表，所以性能上不会有太大的问题。

使用left join的示例如下：

```js
select o.id,o.code,u.name 
from order o 
left join user u on o.user_id = u.id
where u.status=1;
```

如果两张表使用left join关联，mysql会默认用left join关键字左边的表，去驱动它右边的表。如果左边的表数据很多时，就会出现性能问题。

> 要特别注意的是在用left join关联查询时，左边要用小表，右边可以用大表。如果能用inner join的地方，尽量少用left join。

#### 8 控制索引的数量

众所周知，索引能够显著的提升查询sql的性能，但索引数量并非越多越好。

因为表中新增数据时，需要同时为它创建索引，而索引是需要额外的存储空间的，而且还会有一定的性能消耗。

阿里巴巴的开发者手册中规定，单表的索引数量应该尽量控制在`5`个以内，并且单个索引中的字段数不超过`5`个。

mysql使用的B+树的结构来保存索引的，在insert、update和delete操作时，需要更新B+树索引。如果索引过多，会消耗很多额外的性能。

如果你的系统并发量不高，表中的数据量也不多，其实超过5个也可以，只要不要超过太多就行。

但对于一些高并发的系统，请务必遵守单表索引数量不要超过5的限制。

高并发系统可以通过建联合索引，删除无用的单个索引来优化索引。

将部分查询功能迁移到其他类型的数据库中，比如：Elastic Seach、HBase等，在业务表中只需要建几个关键索引即可。

#### 9 能写在 WHERE 子句里的条件不要写在 HAVING 子句里

下列 SQL 语句返回的结果是一样的:

```sql
-- 聚合后使用 HAVING 子句过滤
SELECT sale_date, SUM(quantity)  FROM SalesHistory GROUP BY sale_dateHAVING sale_date = '2007-10-01';
-- 聚合前使用 WHERE 子句过滤
SELECT sale_date, SUM(quantity)  FROM SalesHistory WHERE sale_date = '2007-10-01'  GROUP BY sale_date;
```

使用第二条语句效率更高，原因主要有两点

1. 使用 GROUP BY 子句进行聚合时会进行排序，如果事先通过 WHERE 子句能筛选出一部分行，能减轻排序的负担
    
2. 在 WHERE 子句中可以使用索引，而 HAVING 子句是针对聚合后生成的视频进行筛选的，但很多时候聚合后生成的视图并没有保留原表的索引结构

#### 10 在 GROUP BY 子句和 ORDER BY 子句中使用索引

GROUP BY 子句和 ORDER BY 子句一般都会进行排序，以对行进行排列和替换，不过如果指定带有索引的列作为这两者的参数列，由于用到了索引，可以实现高速查询，由于索引是有序的，排序本身都会被省略掉

#### 11 使用索引时，条件表达式的左侧应该是原始字段

假设我们在 col 列上建立了索引，则下面这些 SQL 语句无法用到索引

```sql
SELECT *  FROM SomeTable WHERE col * 1.1 > 100;
SELECT *  FROM SomeTable WHERE SUBSTR(col, 1, 1) = 'a';
```

以上第一个 SQL 在索引列上进行了运算, 第二个 SQL 对索引列使用了函数，均无法用到索引，正确方式是把列单独放在左侧,如下:

```sql
SELECT *  FROM SomeTable WHERE col_1 > 100 / 1.1;
```

当然如果需要对此列使用函数，则无法避免在左侧运算，可以考虑使用函数索引，不过一般不推荐随意这么做。

#### 12 尽量避免使用否定形式

如下的几种否定形式不能用到索引：

- <>
    
- !=
    
- NOT IN

所以以下SQL 语句会导致全表扫描

```sql
SELECT *  FROM SomeTable WHERE col_1 <> 100;
```

可以改成以下形式

```sql
SELECT *  FROM SomeTable WHERE col_1 > 100 or col_1 < 100;
```

#### 13 注意组合索引，要符合最左匹配原则才能生效

假设存在这样顺序的一个联合索引“col_1, col_2, col_3”。这时，指定条件的顺序就很重要。

`○ SELECT * FROM SomeTable WHERE col_1 = 10 AND col_2 = 100 AND col_3 = 500;   ○ SELECT * FROM SomeTable WHERE col_1 = 10 AND col_2 = 100 ;   × SELECT * FROM SomeTable WHERE col_2 = 100 AND col_3 = 500 ;   `

前面两条会命中索引，第三条由于没有先匹配 col_1，导致无法命中索引， 另外如果无法保证查询条件里列的顺序与索引一致，可以考虑将联合索引 拆分为多个索引。

#### 14 使用 LIKE 谓词时，只有前方一致的匹配才能用到索引（最左匹配原则）

`× SELECT * FROM SomeTable WHERE col_1 LIKE '%a';   × SELECT * FROM SomeTable WHERE col_1 LIKE '%a%';   ○ SELECT * FROM SomeTable WHERE col_1 LIKE 'a%';   `

上例中，只有第三条会命中索引，前面两条进行后方一致或中间一致的匹配无法命中索引

#### 16 尽量使用自增 id 作为主键

比如现在有一个用户表，有人说身份证是唯一的，也可以用作主键，理论上确实可以，不过用身份证作主键的话，一是占用空间相对于自增主键大了很多，二是很容易引起频繁的页分裂，造成性能问题

主键选择的几个原则：自增，尽量小，不要对主键进行修改

#### 17 asc和desc混用

```sql
select * from my_table where col_a=1 order by col_b desc,col_c asc
```

desc 和asc混用时会导致索引失效，不建议混用。

#### 18 or查询导致失效

```sql
select * from table where col_a=1 or col_b=''
```

or查询会导致索引失效，可以将col_a和col_b分别建立索引，利用Mysql的index merge(索引合并)进行优化。本质上是分别两个字段分别走各自索引查出对应的数据，再将数据进行合并。

### 执行计划（explain）

explain 是 sql 优化的利器，除了优化慢 sql，平时的 sql 编写，也应该先 explain，查看一下执行计划，看看是否还有优化的空间。

直接在 select 语句之前增加`explain` 关键字，就会返回执行计划的信息。

![](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/sidebar/sanfene/mysql-77711553-bb7b-4580-968a-4a973e3a31ca.jpg)

![](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/sidebar/sanfene/mysql-e234658f-5672-4a8d-9a75-872b305a171d.jpg)

1. **id** 列：MySQL 会为每个 select 语句分配一个唯一的 id 值
2. **select_type** 列，查询的类型，根据关联、union、子查询等等分类，常见的查询类型有 SIMPLE、PRIMARY。
3. **table** 列：表示 explain 的一行正在访问哪个表。
4. **type** 列：最重要的列之一。表示关联类型或访问类型，即 MySQL 决定如何查找表中的行。

性能从最优到最差分别为：system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL

- system

`system`：当表仅有一行记录时(系统表)，数据量很少，往往不需要进行磁盘 IO，速度非常快

- const

`const`：表示查询时命中 `primary key` 主键或者 `unique` 唯一索引，或者被连接的部分是一个常量(`const`)值。这类扫描效率极高，返回数据量少，速度非常快。

- eq_ref

`eq_ref`：查询时命中主键`primary key` 或者 `unique key`索引， `type` 就是 `eq_ref`。

- ref_or_null

`ref_or_null`：这种连接类型类似于 ref，区别在于 `MySQL`会额外搜索包含`NULL`值的行。

- index_merge

`index_merge`：使用了索引合并优化方法，查询使用了两个以上的索引。

- unique_subquery

`unique_subquery`：替换下面的 `IN`子查询，子查询返回不重复的集合。

- index_subquery

`index_subquery`：区别于`unique_subquery`，用于非唯一索引，可以返回重复值。

- range

`range`：使用索引选择行，仅检索给定范围内的行。简单点说就是针对一个有索引的字段，给定范围检索数据。在`where`语句中使用 `bettween...and`、`<`、`>`、`<=`、`in` 等条件查询 `type` 都是 `range`。

- index

`index`：`Index` 与`ALL` 其实都是读全表，区别在于`index`是遍历索引树读取，而`ALL`是从硬盘中读取。

- ALL

就不用多说了，全表扫描。

6. **possible_keys** 列：显示查询可能使用哪些索引来查找，使用索引优化 sql 的时候比较重要。
7. **key** 列：这一列显示 mysql 实际采用哪个索引来优化对该表的访问，判断索引是否失效的时候常用。
8. **key_len** 列：显示了 MySQL 使用
9. **ref** 列：ref 列展示的就是与索引列作等值匹配的值，常见的有：const（常量），func，NULL，字段名。
10. **rows** 列：这也是一个重要的字段，MySQL 查询优化器根据统计信息，估算 SQL 要查到结果集需要扫描读取的数据行数，这个值非常直观显示 SQL 的效率好坏，原则上 rows 越少越好。
11. **Extra** 列：显示不适合在其它列的额外信息，虽然叫额外，但是也有一些重要的信息：

- Using index：表示 MySQL 将使用覆盖索引，以避免回表
- Using where：表示会在存储引擎检索之后再进行过滤
- Using temporary ：表示对查询结果排序时会使用一个临时表。