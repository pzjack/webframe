/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.web;

import com.dinglicom.frame.sys.domain.AccountInfoBean;
import com.dinglicom.frame.sys.domain.AdminGetAllUserReq;
import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.AdminUserInfoReq;
import com.dinglicom.frame.sys.domain.AdminUseraddResp;
import com.dinglicom.frame.sys.domain.AppAccountBean;
import com.dinglicom.frame.sys.domain.AppPageReqBase;
import com.dinglicom.frame.sys.domain.AppRequestBase;
import com.dinglicom.frame.sys.domain.AppUserReq;
import com.dinglicom.frame.sys.domain.BaiduReq;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.domain.CustomeDetailReq;
import com.dinglicom.frame.sys.domain.CustomerDetailResp;
import com.dinglicom.frame.sys.domain.CustomerPhoneReq;
import com.dinglicom.frame.sys.domain.DeliveryOrgResp;
import com.dinglicom.frame.sys.domain.LoginReturnBean;
import com.dinglicom.frame.sys.domain.UserDetailMsg;
import com.dinglicom.frame.sys.domain.UserInfoUpdateReq;
import com.dinglicom.frame.sys.domain.WorkerCustomAddBean;
import com.dinglicom.frame.sys.domain.WorkerOrgReq;
import com.dinglicom.frame.sys.domain.WorkerOrgResp;
import com.dinglicom.frame.sys.domain.WorkerQueryCustomResp;
import com.dinglicom.frame.sys.domain.WorkerUpdateCustomerReq;
import com.dinglicom.frame.sys.entity.SysToken;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.GeneratorUUID;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.sys.service.SysUserAccountService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.web.AppControllerBase;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/app")
public class AppLoginController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(AppLoginController.class);
    @Resource
    private SysUserAccountService sysUserAccountService;
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private SysOranizagionService sysOranizagionService;

    /**
     * app用户登录验证，生成唯一token值
     *
     * @param request
     * @param response
     * @param appAccountBean
     * @return
     */
    @RequestMapping(value = "/login")
    public @ResponseBody
    LoginReturnBean login(HttpServletRequest request, HttpServletResponse response, AppAccountBean appAccountBean) {
        LoginReturnBean b = new LoginReturnBean();
        if (null == appAccountBean || null == appAccountBean.getAccount() || null == appAccountBean.getPwd()) {
            b.setCode(2);
            b.setResult("账号密码缺失");
            return b;
        }
//        int code = sysTokenService.findByAccout(appAccountBean.getAccount());
//        if (code < 0) {
//            b.setCode(1);
//            b.setResult("账号已经登录");
//            return b;
//        }
        org.apache.shiro.mgt.SecurityManager securityManager = SecurityUtils.getSecurityManager();
        if (null == securityManager) {
            LOG.warn("No SecurityManager, return.");
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(appAccountBean.getAccount(), appAccountBean.getPwd(), true, getHost(request));
        AccountInfoBean sus = null;
        try {
            subject.login(token);
            sus = (AccountInfoBean) subject.getPrincipals().getPrimaryPrincipal();
            b.setUid(sus.getUserId());
            b.setToken(GeneratorUUID.getRadomUUID());
            b.setRole(sus.getUserType());
            b.setCode(0);
            b.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Login fail:", e);
            b.setCode(2);
            b.setResult("账号或者密码错误");
        }
        if (null != sus) {
            try {
                SysToken sysToken = sysUserAccountService.createToken(sus.getAccount(), b.getToken());
                sysTokenService.deleteByAccountId(sysToken.getSysUserAccount().getId());
                sysTokenService.save(sysToken);
            } catch (Exception e) {
                LOG.warn("Login fail:", e);
                b.setCode(2);
                b.setResult("系统错误");
            }
        }
        return b;
    }

    /**
     * app用户登出
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/logout")
    public @ResponseBody
    BaseMsgBean logout(AppRequestBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo customer = validateToken(sysTokenService, req, msg);
        if (null == customer || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            if (null != subject) {
                subject.logout();
            }
        } catch (Exception e) {
            LOG.warn("Logout fail:", e);
            msg.setCode(1);
            msg.setResult("登出失败");
        }
        try {
            sysTokenService.deleteByUserid(customer.getId());
        } catch (Exception e) {
            LOG.warn("Logout fail:", e);
            msg.setCode(1);
            msg.setResult("系统错误");
        }
        return msg;
    }

    /**
     * 账号注册
     *
     * @param appAccountBean
     * @return
     */
    @RequestMapping(value = "/registuser")
    public @ResponseBody
    BaseMsgBean registUser(AppAccountBean appAccountBean) {
        BaseMsgBean msg = new BaseMsgBean();
        if (null == appAccountBean || null == appAccountBean.getAccount() || null == appAccountBean.getPwd()) {
            msg.setCode(1);
            msg.setResult("请初始化账号密码");
            return msg;
        }

        try {
            sysUserAccountService.createUserAccountByApp(appAccountBean);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("regist user account fail.", e);
            msg.setCode(1);
            msg.setResult("创建失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * 用户认证信息认证
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/tokenvalidate")
    public @ResponseBody
    BaseMsgBean userTokenValidate(AppRequestBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo user = validateToken(sysTokenService, req, msg);
        if (null == user || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("无有效权限");
            return msg;
        }
        return msg;
    }

    /**
     * 送奶工根据用户名和电话查询订户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/qryuser")
    public @ResponseBody
    BaseMsgBean queryUser(AppUserReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || null == req.getQuery()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findByWorkerQuery(req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 送奶工根据手机号码查询收货人
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/qryphone")
    public @ResponseBody
    BaseMsgBean queryCustomser(CustomerPhoneReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid() || null == req.getQuery()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findByWorkerPhone(req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 系统管理员查找所有用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/userinfopage")
    public @ResponseBody
    BaseMsgBean adminGetUserInfo(AdminReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findUserInfoPage(req);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 送奶工添加订户
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/customadd")
    public @ResponseBody
    BaseMsgBean workerAddCustomer(WorkerCustomAddBean req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid() || null == req.getMobile_phone() || null == req.getPwd()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            sysUserAccountService.createUserAccountByWorker(req, worker);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("保存失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * 添加送奶工
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/workeradd")
    public @ResponseBody
    BaseMsgBean addWorker(WorkerCustomAddBean req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo nzmanager = validateToken(sysTokenService, req, msg);
        if (null == nzmanager || 0 == req.getUid() || null == req.getMobile_phone() || null == req.getPwd()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            sysUserAccountService.addWorker(req, nzmanager);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 分页显示送奶工
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/workerpage")
    public @ResponseBody
    WorkerQueryCustomResp findWorkerPage(AppPageReqBase req) {
        WorkerQueryCustomResp msg = new WorkerQueryCustomResp();
        UserInfo nzmanger = validateToken(sysTokenService, req, msg);
        if (null == nzmanger || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findWorkerPage(nzmanger, req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 送奶工分页显示所有订户
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/pagebyworker")
    public @ResponseBody
    WorkerQueryCustomResp workerFindAllCustomerPage(AppPageReqBase req) {
        WorkerQueryCustomResp msg = new WorkerQueryCustomResp();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findAllCustomByWorker(worker, req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 送奶工显示订户详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/detailbyworker")
    public @ResponseBody
    CustomerDetailResp workerFindAllCustomerDetail(CustomeDetailReq req) {
        CustomerDetailResp msg = new CustomerDetailResp();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findUserInfoById(req.getUser_id());
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * 查询订奶工详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/workerdetail")
    public @ResponseBody
    UserDetailMsg findWorkerDetail(CustomeDetailReq req) {
        UserDetailMsg msg = new UserDetailMsg();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findWorkerById(req.getUser_id());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 用户自己修改自己信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/infoupdate")
    public @ResponseBody
    BaseMsgBean updateUserInfoSelf(UserInfoUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo customer = validateToken(sysTokenService, req, msg);
        if (null == customer || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysUserAccountService.updateUserAccountByWorker(customer, req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 用户自己的信息信息详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/infoget")
    public @ResponseBody
    BaseMsgBean getUserInfoSelf(UserInfoUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        SysUserAccount account = validateTokenAccount(sysTokenService, req, msg);
        UserInfo customer = account.getUserInfo();
        if (null == customer || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.getMyInfo(customer);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("获取我的信息失败：" + e.getMessage());
        }
        return msg;
    }

    /**
     * 修改送奶工信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateworker")
    public @ResponseBody
    BaseMsgBean updateWorker(WorkerUpdateCustomerReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo nzmanager = validateToken(sysTokenService, req, msg);
        if (null == nzmanager || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            userInfoService.updateUserInfo(req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 送奶工修改订户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/infoupdatebyworker")
    public @ResponseBody
    BaseMsgBean updateCustomByWorker(WorkerUpdateCustomerReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo nzmanager = validateToken(sysTokenService, req, msg);
        if (null == nzmanager || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            userInfoService.updateUserInfo(req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("保存失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * 送奶工删除订户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/infodeletebyworker")
    public @ResponseBody
    BaseMsgBean workerDeleteCustomer(CustomeDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysUserAccountService.deleteByUserId(req.getUser_id());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("删除失败");
        }
        return msg;
    }

    /**
     * 删除送奶工信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/deleteworker")
    public @ResponseBody
    BaseMsgBean DeleteWorker(CustomeDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo nzmanager = validateToken(sysTokenService, req, msg);
        if (null == nzmanager || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysUserAccountService.deleteByUserId(req.getUser_id());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("删除失败");
        }
        return msg;
    }

    /**
     * 获取对应区域的奶站
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/workstation")
    public @ResponseBody
    BaseMsgBean findworkStation(WorkerOrgReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        if(null == req.getProvince() || null == req.getCity() || null == req.getRegion()) {
            msg.setCode(1);
            msg.setResult("请输入位置区域编码");
            return msg;
        }
        LOG.info("query station\tprivince:{}\tcity:{}\tregion:{}", req.getProvince(), req.getCity(), req.getRegion());
        try {
            WorkerOrgResp workstation = new WorkerOrgResp();
            workstation.setData(sysOranizagionService.findAllWorkerOrg(req));
            msg = workstation;
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    /**
     * 获取对应区域的配送商
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delivers")
    public @ResponseBody
    BaseMsgBean findDelivers(WorkerOrgReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        if(null == req.getProvince() || null == req.getCity() || null == req.getRegion()) {
            msg.setCode(1);
            msg.setResult("请输入位置区域编码");
            return msg;
        }
        try {
            DeliveryOrgResp dlv = new DeliveryOrgResp();
            dlv.setData(sysOranizagionService.findAllDeliveryOrg(req));
            msg = dlv;
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("查询失败");
        }
        return msg;
    }

    protected String getHost(ServletRequest request) {
        return request.getRemoteHost();
    }

    /**
     * 系统管理员添加用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/userinfoadd")
    public @ResponseBody
    AdminUseraddResp adminAddUserInfo(AdminUserInfoReq req) {
        AdminUseraddResp msg = new AdminUseraddResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Admin add user:role:{} name:{},sup_id:{}, sup_name:{}", req.getRole(), req.getRealname(), req.getSup_id(),req.getSup_name());
        if (null == admin || 0 >= req.getMid() || null == req.getAccount() || null == req.getPwd()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserInfo user = userInfoService.addUserByAdmin(req);
            if (null != user) {
                msg.setUid(user.getId());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
            LOG.warn("Admin add user fail:", e);
        }
        return msg;
    }

    /**
     * 系统管理员修改用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/userinfoupdate")
    public @ResponseBody
    BaseMsgBean adminUpdateUserInfo(AdminUserInfoReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Admin update userid:{} :role:{} name:{}", req.getUid(), req.getRole(), req.getRealname());
        if (null == admin || 0 == req.getMid() || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserInfo userinfo = userInfoService.read(req.getUid());
            if (null == userinfo) {
                throw new RuntimeException("用户编号无效！");
            }
            userInfoService.updateUserByAdmin(req, userinfo);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
            LOG.warn("Admin update user fail:", e);
        }
        return msg;
    }

    /**
     * 系统管理员删除用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/userinfodelete")
    public @ResponseBody
    BaseMsgBean adminDeleteUserInfo(AdminUserInfoReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Admin delete userid:{}", req.getUid());
        if (null == admin || 0 == req.getMid() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserInfo userinfo = userInfoService.read(req.getUid());
            if (null == userinfo) {
                throw new RuntimeException("用户编号无效！");
            }
            userInfoService.deleteUserByAdmin(req.getUid());
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
            LOG.info("Admin delete fail.", e);
        }
        return msg;
    }

    /**
     * web界面系统管理员 查询所有用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/webuserpage")
    public @ResponseBody
    BaseMsgBean userinfopage(AdminGetAllUserReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("web user page(/webuserpage) parama,page:{}, pagenum:{},role:{}", req.getPage(), req.getNum(), req.getRole());
        if (null == admin || 0 == req.getMid() || 0 >= req.getPage() || 0 >= req.getNum() || null == req.getRole()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("web user page parama,userinfo,userid:{}, usertype:{}",admin.getId(), admin.getUserType());
        try {
            msg = userInfoService.findAllUserPage(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * web界面系统管理员 查询所有用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/webuserget")
    public @ResponseBody
    BaseMsgBean userinfoget(AdminUserInfoReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("web user details(/webuserget) get parama,uid:{}", req.getUid());
        if (null == admin || 0 == req.getMid() || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userInfoService.findByUserId(req);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * 用户更新自己生气的百度ID号
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updatebaiduid")
    public @ResponseBody
    BaseMsgBean updatebaidu(BaiduReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo user = validateToken(sysTokenService, req, msg);
        if (null == user || 0 >= req.getUid() || null == req.getBaiduId()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            user.setBaiduid(req.getBaiduId());
            userInfoService.save(user);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("删除失败");
        }
        return msg;
    }
}
