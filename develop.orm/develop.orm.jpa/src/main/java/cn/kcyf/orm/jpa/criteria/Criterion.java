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
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, ISNULL, ISNOTNULL, LEFTLIKE, RIGHTLIKE, NOTLIKE
    }

    Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
