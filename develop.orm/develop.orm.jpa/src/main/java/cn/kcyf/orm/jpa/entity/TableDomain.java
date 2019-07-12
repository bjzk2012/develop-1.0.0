package cn.kcyf.orm.jpa.entity;

import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 包含了创建时间，更新时间和乐观锁的
 */

@MappedSuperclass
@Setter
public abstract class TableDomain extends IdDomain {

    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 创建人用户编号
     */
    protected Long createUserId;
    /**
     * 创建人用户名称
     */
    protected String createUserName;
    /**
     * 最后更新时间
     */
    protected Date lastUpdateTime;
    /**
     * 最后更新用户编号
     */
    protected Long lastUpdateUserId;
    /**
     * 最后更新用户名称
     */
    protected String lastUpdateUserName;

    /**
     * 版本号,用于实现乐观锁
     */
    protected int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    public Date getCreateTime() {
        return createTime;
    }

    @Column(name = "create_user_id", nullable = false, updatable = false)
    public Long getCreateUserId() {
        return createUserId;
    }

    @Column(name = "create_user_name", nullable = false, updatable = false)
    public String getCreateUserName() {
        return createUserName;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time", insertable = false)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Column(name = "last_update_user_id", insertable = false)
    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    @Column(name = "last_update_user_name", insertable = false)
    public String getLastUpdateUserName() {
        return lastUpdateUserName;
    }

    @Version
    @Column(name = "version", nullable = false)
    public int getVersion() {
        return version;
    }

    @PrePersist
    @PreUpdate
    protected void updateDate() {
        if (createTime == null) {
            createTime = new Date();
        }
        lastUpdateTime = new Date();
    }
}
