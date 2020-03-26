package cn.kcyf.orm.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Tom
 */
public interface Criterion {
    enum Operator {
        EQ("等于"),
        NE("不等于"),

        LIKE("字符串匹配"),
        LEFTLIKE("左字符串匹配"),
        RIGHTLIKE("右字符串匹配"),
        NOTLIKE("字符串不匹配"),

        GT("大于"),
        LT("小于"),

        GTE("大于等于"),
        LTE("小于等于"),

        ISNULL("是NULL"),
        ISNOTNULL("不是NULL"),

        IN("在范围"),
        NOTIN("不在范围"),

        AND("且"),
        OR("或");

        Operator(String remark) {
            this.remark = remark;
        }

        private String remark;

        public String getRemark() {
            return remark;
        }
    }

    Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
