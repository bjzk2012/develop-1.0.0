package cn.kcyf.orm.jpa.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 包含了创建时间，更新时间和乐观锁的
 */

@MappedSuperclass
@Data
public abstract class TableDomain extends IdDomain {

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createTime;
    /**
     * 创建人用户编号
     */
    @Column(name = "create_user_id", nullable = false, updatable = false)
    protected Long createUserId;
    /**
     * 创建人用户名称
     */
    @Column(name = "create_user_name", nullable = false, updatable = false)
    protected String createUserName;
    /**
     * 最后更新时间
     */
    @Column(name = "last_update_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;
    /**
     * 最后更新用户编号
     */
    @Column(name = "last_update_user_id", insertable = false)
    protected Long lastUpdateUserId;
    /**
     * 最后更新用户名称
     */
    @Column(name = "last_update_user_name", insertable = false)
    protected String lastUpdateUserName;
    /**
     * 版本号,用于实现乐观锁
     */
    @Version
    @Column(name = "version", nullable = false)
    protected int version;

    @PrePersist
    @PreUpdate
    protected void updateDate() {
        if (createTime == null) {
            createTime = new Date();
        }
        lastUpdateTime = new Date();
    }
}
