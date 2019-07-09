package cn.kcyf.orm.jpa.entity;

import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 统一定义id的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略
 *
 */
@MappedSuperclass
@Setter
public abstract class IdDomain implements Serializable {

    private static final long serialVersionUID = -6604117477081010952L;

    /**
     * 表主键
     */
    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
