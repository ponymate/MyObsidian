package com.yonyou.iuapmdm.remotesysreg.controller;

import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.pap.base.i18n.MessageSourceUtil;
import com.yonyou.iuap.yms.multitenant.PrivilegeExecutors;
import com.yonyou.iuapmdm.common.common.*;
import com.yonyou.iuapmdm.common.constrant.MDMConsts;
import com.yonyou.iuapmdm.common.persist.page.MdmPage;
import com.yonyou.iuapmdm.common.sqlinjection.UAPESAPI;
import com.yonyou.iuapmdm.remotesysreg.dto.RemoveSysDTO;
import com.yonyou.iuapmdm.remotesysreg.entity.MDSysRegisterVO;
import com.yonyou.iuapmdm.remotesysreg.service.impl.MDSysRegisterServiceImpl;
import com.yonyou.iuapmdm.syslog.constant.ModuleConstant;
import com.yonyou.iuapmdm.syslog.util.MessageWrapper;


import com.yonyou.iuapmdm.user.entity.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author konglk
 */
@Controller
@RequestMapping("/sysRegister")

public class MDSysRegisterController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String SUCCESS = "success";


    @Autowired
    private MDSysRegisterServiceImpl sysRegisterService;


    /**
     * 新增保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO save(@RequestBody MDSysRegisterVO entity) {
        String msg = MessageSourceUtil.getMessage("ja.rem.con.0001", "保存成功");
        ResultVO resultVO = new ResultVO();
        String name = "";
        try {
            if (entity.getCode().length() >= 50) {
                resultVO.setFlag(false);
				resultVO.setMsg(MessageSourceUtil.getMessage("ja.rem.con.0002", "code长度不可超过50"));
                return resultVO;
            }
            //校验分发服务类型
            sysRegisterService.distributeTypeCheckBeforeSave(entity);
            //设置状态为NEW，才会插入新数据
            entity.setStatus(VOStatus.NEW);
            entity.setDr(0);
            entity = sysRegisterService.saveEntity(entity);
            name = entity.getName();
            resultVO.setFlag(true);
            resultVO.setMsg(MessageWrapper.giraffe(name, msg));
            resultVO.setData(entity);
        } catch (Exception e) {
			msg = MessageSourceUtil.getMessage("ja.rem.con.0003", "保存失败")+e.getMessage();
            resultVO.setFlag(false);
            resultVO.setMsg(MessageWrapper.giraffe(name, msg));
			logger.error(name + MessageSourceUtil.getMessage("ja.rem.con.0003", "保存失败") + e.getMessage(), e);
        }

        resultVO.setBizObj(entity.getName());
        return resultVO;
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO update(@RequestBody MDSysRegisterVO entity) {
		String msg = MessageSourceUtil.getMessage("ja.rem.con.0005", "更新成功！");
        ResultVO resultVO = new ResultVO();
        String name = entity.getName();
        try {
            //是否存在检查
            if (StringUtils.isEmpty(entity.getPk_sysregister())) {
                throw new MdmBusinessException(MessageSourceUtil.getMessage("sys.not.exist.try.later", "该集成系统不存在！请刷新后重试！"));
            } else {
                MDSysRegisterVO exist = sysRegisterService.getSysRegisterById(entity.getPk_sysregister());
                if (exist==null) {
                    throw new MdmBusinessException(MessageSourceUtil.getMessage("sys.not.exist.try.later", "该集成系统不存在！请刷新后重试！"));
                }
                if(exist.getDr() == null || exist.getDr() == 2) {
                    throw new MdmBusinessException(MessageSourceUtil.getMessage("sys.not.exist.try.later", "该集成系统不存在！请刷新后重试！"));
                }
                //校验分发服务类型
                sysRegisterService.distributeTypeCheckBeforeSave(entity);
                //设置状态为更新，才会持久化
                entity.setStatus(VOStatus.UPDATED);
                sysRegisterService.updateSysAndAuthSube(exist,entity);
            }
            resultVO.setFlag(true);
            resultVO.setMsg(MessageWrapper.giraffe(name, msg));
            resultVO.setData(entity);
        } catch (Exception e) {
        	if(e instanceof MdmDuplicateCodeException) {
        		msg = e.getMessage();
        	} else if(e instanceof SQLException){
                msg = MessageWrapper.giraffe(name, MessageSourceUtil.getMessage("ja.rem.con.0006", "更新失败！"));
            } else {
                msg = MessageWrapper.giraffe(name, MessageSourceUtil.getMessage("ja.rem.con.0006", "更新失败！") + e.getMessage());
        	}
            resultVO.setFlag(false);
            resultVO.setMsg(msg);
			logger.error(name + MessageSourceUtil.getMessage("ja.rem.con.0007", "更新出错!"), e);
        }


        resultVO.setBizObj(entity.getName());
        return resultVO;
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/refleshToken/{pk_sysregister}", method = RequestMethod.GET)
    public @ResponseBody
    ResultVO refleshToken(@PathVariable("pk_sysregister") String pk_sysregister) {
        String msg = "Token更新成功";
        ResultVO result = new ResultVO();
        MDSysRegisterVO entity = null;
        try {
            //设置状态为更新，才会持久化
            entity = sysRegisterService.queryMDSysRegisterVOByPK(UAPESAPI.sqlEncode(pk_sysregister));
            if (entity == null) {
				throw new MdmRuntimeException(MessageSourceUtil.getMessage("ja.rem.con.0009", "远程系统不存在！"));
            }
            entity.setToken(AppUtils.getOId(null));
            entity.setStatus(VOStatus.UPDATED);
            entity.setDr(0);
            sysRegisterService.update(entity);
            result.setData(entity);
            result.setMsg(msg);
            result.setFlag(true);
        } catch (Exception e) {
            msg = MessageSourceUtil.getMessage("ja.rem.con.0010", "Token更新失败，详情为：") + e.getMessage();
            result.setMsg(msg);
            result.setFlag(false);
            logger.error(MessageSourceUtil.getMessage("ja.rem.con.0010", "Token更新失败，详情为：") + e.getMessage(), e);
        }
        result.setBizObj(entity != null ? entity.getName() : null);
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "orderno"));
            MdmPage<List<MDSysRegisterVO>> sysRegisters = sysRegisterService.queryVOs(pageRequest, " dr!=2 ", null); // 2 状态是特殊数据，不显示给前端
            result.put("data", sysRegisters.getData());
            result.put("pageCount", sysRegisters.getTotalPages());
            result.put("total", sysRegisters.getTotalElements());
            result.put("flag", SUCCESS);
			result.put("msg", MessageSourceUtil.getMessage("ja.rem.con.0011", "查询数据成功!"));
        } catch (Exception e) {
			String errMsg = MessageSourceUtil.getMessage("ja.rem.con.0012", "查询数据详情失败!");
            result.put("flag", "fail");
            result.put("msg", errMsg);
            logger.error(errMsg, e);
        }
        return result;
    }
    @RequestMapping(value = "/connectorSysRegister", method = RequestMethod.GET)
    @ResponseBody
    public Object connectorSysRegister(HttpServletRequest request) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String tenantId = request.getHeader("tenantId");
//            InvocationInfoProxy.setTenantid(tenantId);
            String sysCode = request.getParameter("sysCode");
            StringBuffer sb = new StringBuffer(" dr!=2 ");
            if(sysCode != null){
                sb.append(MDMConsts.AND);
                sb.append(" code = '");
                sb.append(sysCode);
                sb.append("'");
            }
            List<MDSysRegisterVO> sysRegisters = sysRegisterService.queryVOsByCon(sb.toString(),null); // 2 状态是特殊数据，不显示给前端
            result.put("data", sysRegisters);
            result.put("flag", true);
            result.put("msg", MessageSourceUtil.getMessage("ja.rem.con.0011", "查询数据成功!"));
        } catch (Exception e) {
            String errMsg = MessageSourceUtil.getMessage("ja.rem.con.0012", "查询数据详情失败!");
            result.put("flag", false);
            result.put("msg", errMsg);
            logger.error(errMsg, e);
        }
        return result;
    }

    @RequestMapping(value = "/queryByPk", method = RequestMethod.GET)
    @ResponseBody
    public Object queryByPk(HttpServletRequest request) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success",false);
        result.put("msg", "systemId不能为空！");
        String tenantId = request.getHeader("tenantId");
//        InvocationInfoProxy.setTenantid(tenantId);
        String systemId = request.getParameter("systemId");
        if (systemId != null) {
            MDSysRegisterVO mdSysRegisterVO = sysRegisterService.queryByPk(systemId);
            if (mdSysRegisterVO == null) {
                result.put("success", false);
                result.put("msg", "获取集成系统编码失败！");
                return result;
            } else {
                result.put("success",true);
                result.put("msg", "获取集成系统编码成功！");
                result.put("data",mdSysRegisterVO.getCode());
                }
            }
        return result;
    }

    /**
     * 主数据连接器校验集成系统和令牌是否合法
     */
    @RequestMapping(value = "/validationSysRegister", method = RequestMethod.GET)
    @ResponseBody
    public Object validationSysRegister(HttpServletRequest request) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success",true);
        result.put("msg", "集成系统和认证令牌校验成功！");
        String tenantId = request.getHeader("tenantId");
//        InvocationInfoProxy.setTenantid(tenantId);
        String systemCode = request.getParameter("systemCode");
        String registerToken = request.getParameter("registerToken");
        //PreparedStatementSetterImpl pss = new PreparedStatementSetterImpl();
        StringBuffer sb = new StringBuffer(" dr != 2 ");
        if (systemCode != null) {
            sb.append(MDMConsts.AND);
            sb.append(" code = '");
            sb.append(systemCode + "' ");
            //pss.addParam(systemId);
            AtomicReference<List<MDSysRegisterVO>> sysRegisters = new AtomicReference<List<MDSysRegisterVO>>();
            PrivilegeExecutors.executeInCurrentThread(tenantId, () -> {
                logger.info("以租户[{}]初始化上下文成功", tenantId);
                try {
                    sysRegisters.set(sysRegisterService.queryVOsByCon(sb.toString(), null)); // 2 状态是特殊数据，不显示给前端
                } catch (MdmBusinessException e) {
                    e.printStackTrace();
                }
            });
            if (sysRegisters.get() == null || sysRegisters.get().size() == 0) {
                result.put("success", false);
                result.put("msg", "集成系统不存在！");
                return result;
            } else {
                if (registerToken != null) {
                    List<String> tokenList = sysRegisters.get().stream().map(MDSysRegisterVO::getToken).collect(Collectors.toList());
                    if (!tokenList.contains(registerToken)) {
                        result.put("success", false);
                        result.put("msg", "认证令牌校验错误！");
                    }
                }
            }
        }
//        else{
//            result.put("success", false);
//            result.put("msg", "集成系统编码为空！");
//        }

        return result;
    }



    /** 新改为禁用/启用 **/
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO delete(@RequestBody RemoveSysDTO removeSysDTO) {
        String msg = "";
        ResultVO resultVO = new ResultVO();
        MDSysRegisterVO entity = null;
        String pk_sysregister=removeSysDTO.getPk_sysregister();
        try {
            entity = sysRegisterService.getSysRegisterById(pk_sysregister);
            if (entity != null) {
                sysRegisterService.checkBeforeDisable(entity);
                entity.setStatus(VOStatus.UPDATED);
                if (0 == entity.getDr()) {
                    entity.setDr(1);
					msg = MessageSourceUtil.getMessage("ja.rem.con.0014", "禁用成功");
                } else {
                    entity.setDr(0);
					msg = MessageSourceUtil.getMessage("ja.rem.con.0015", "启用成功");
                }
                sysRegisterService.saveEntity(entity);
                resultVO.setFlag(true);
                resultVO.setMsg(MessageWrapper.giraffe(entity.getName(), msg));
            } else {
                msg = MessageWrapper.corgi(ModuleConstant.INTEGRATION_SYSTEM, "", "远程不存在，请确认pk_sysregister是否正确");
                resultVO.setFlag(false);
                resultVO.setMsg(msg);
            }
        } catch (Exception e) {
			msg = MessageSourceUtil.getMessage("ja.rem.con.0017", "启用/禁用远程系统失败！详情：") + e.getMessage();
            logger.error(msg, e);
            resultVO.setFlag(false);
            resultVO.setMsg(msg);
        }
        resultVO.setBizObj(entity != null ? entity.getName() : null);
        return resultVO;
    }

    /**
     * 移除集成系统记录
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO remove(@RequestBody RemoveSysDTO removeSysDTO) {

        String msg = "";
        ResultVO result = new ResultVO();
        MDSysRegisterVO entity = null;
        List<String> pk_sysregisters=new ArrayList<>();
        pk_sysregisters.add(removeSysDTO.getPk_sysregister());
        try {
            if (CollectionUtils.isEmpty(pk_sysregisters)) {
                msg =  MessageSourceUtil.getMessage("sys.ctrl.remove.empty", "远程集成系统不存在，请确认pk_sysregister参数是否正确！");
                result.setMsg(msg);
                result.setFlag(false);
            } else {
                //包括移除前条件校验
                entity = sysRegisterService.getSysRegisterById(pk_sysregisters.get(0));
                sysRegisterService.batchRemove(pk_sysregisters, result);
            }
        } catch (Exception ex) {
            msg = MessageSourceUtil.getMessage("sys.ctrl.remove.error", "移除远程集成系统失败！详情：") + ex.getMessage();
            logger.error(msg, ex);
            result.setFlag(false);
            result.setMsg(msg);
        }
        result.setBizObj(entity != null ? entity.getName() : null);
        return result;
    }

    /**
     * 进入详情界面
     *
     * @param id
     * @param model
     * @return 需要更新的实体的json结构
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@PathVariable("id") String id, Model model) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            MDSysRegisterVO entity = sysRegisterService.getSysRegisterById(UAPESAPI.sqlEncode(id));
            result.put("data", entity);
            result.put("flag", SUCCESS);
   			result.put("msg", MessageSourceUtil.getMessage("ja.rem.con.0018", "查询数据详情成功!"));
        } catch (Exception e) {
   			String errMsg = MessageSourceUtil.getMessage("ja.rem.con.0012", "查询数据详情失败!");
            result.put("flag", "fail");
            result.put("msg", errMsg);
            logger.error(errMsg, e);
        }
        return result;
    }

    @RequestMapping(value = "/queryDistributeNotifier", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO queryDistributeNotifier() {
        ResultVO result = new ResultVO();
//        @RequestParam(value = "noticeType") String noticeType
        String noticeType = "";
        List<UserInfo> userList = sysRegisterService.queryDistNotifierList(noticeType);
        result.setFlag(true);
        result.setMsg("查询成功！");
        result.setData(userList);
        return result;
    }

}
