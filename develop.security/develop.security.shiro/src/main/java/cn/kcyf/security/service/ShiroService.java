package cn.kcyf.security.service;

import cn.kcyf.security.domain.ShiroUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户登录权限专用Service
 *
 * @author Tom.
 */
@Service("shiroService")
public interface ShiroService {

    /**
     * 根据用户名及用户类型查找用户
     *
     * @param username
     * @return
     */
    ShiroUser getUser(String username);

    /**
     * 根据用户名及用户类型查找用户详细信息
     *
     * @param username
     * @return
     */
    Object getUserInfo(String username);

    /**
     * 获取用户详细信息
     * @param username
     * @return
     */
    JSONObject getDetail(String username);

    /**
     * 根据用户名及用户类型获取用户角色
     *
     * @param username
     * @return
     */
    Set<String> getRoles(String username);

    /**
     * 根据用户名及用户类型获取用户权限
     *
     * @param username
     * @return
     */
    Set<String> getPermissions(String username);

    /**
     * 更新登录明细
     *
     * @param loginId
     * @param loginName
     * @param loginIp
     * @param loginType
     * @param success
     * @return
     */
    ShiroUser loginLogger(Long loginId, String loginName, String loginIp, int loginType, boolean success);

    /**
     * 检查用户
     *
     * @param user
     * @return
     */
    ShiroUser checkUser(JSONObject user);

}
