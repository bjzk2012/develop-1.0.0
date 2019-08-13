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
     * @param account
     * @return
     */
    ShiroUser getUser(String account);

    /**
     * 更新登录明细
     *
     * @param id
     * @param account
     * @param ip
     * @param type
     * @param success
     * @return
     */
    ShiroUser loginLogger(Long id, String account, String ip, int type, boolean success);

    /**
     * 检查用户
     *
     * @param detail
     * @return
     */
    ShiroUser checkUser(JSONObject detail);

}
