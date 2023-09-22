#### 避免使用select *

不需要的数据会增加网络IO传输的时间。
#### 小表驱动大表

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

#### 连接查询代替子查询

mysql中如果需要从两张以上的表中查询出数据的话，一般有两种实现方式：`子查询` 和 `连接查询`

子查询的缺点是mysql执行子查询时，需要创建临时表，查询完毕后，需要再删除这些临时表，有一些额外的性能消耗。

#### 14 使用 LIKE 谓词时，只有前方一致的匹配才能用到索引（最左匹配原则）

`× SELECT * FROM SomeTable WHERE col_1 LIKE '%a';   × SELECT * FROM SomeTable WHERE col_1 LIKE '%a%';   ○ SELECT * FROM SomeTable WHERE col_1 LIKE 'a%';   `

上例中，只有第三条会命中索引，前面两条进行后方一致或中间一致的匹配无法命中索引

#### 18 or查询导致失效

**低版本避免使用 or 查询**

在 MySQL 5.0 之前的版本要尽量避免使用 or 查询，可以使用 union 或者子查询来替代，因为早期的 MySQL 版本使用 or 查询可能会导致索引失效，高版本引入了索引合并，解决了这个问题。

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