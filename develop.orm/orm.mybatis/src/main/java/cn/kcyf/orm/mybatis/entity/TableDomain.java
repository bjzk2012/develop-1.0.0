package cn.kcyf.orm.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class TableDomain extends IdDomain {
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    protected Date createTime;
    /**
     * 创建人用户编号
     */
    @TableField(value = "create_user_id")
    protected Long createUserId;
    /**
     * 创建人用户名称
     */
    @TableField(value = "create_user_name")
    protected String createUserName;
    /**
     * 最后更新时间
     */
    @TableField(value = "last_update_time")
    protected Date lastUpdateTime;
    /**
     * 最后更新用户编号
     */
    @TableField(value = "last_update_user_id")
    protected Long lastUpdateUserId;
    /**
     * 最后更新用户名称
     */
    @TableField(value = "last_update_user_name")
    protected String lastUpdateUserName;

    /**
     * 版本号,用于实现乐观锁
     */
    @TableField(value = "version")
    protected int version;

}
