package cn.kcyf.orm.jpa.criteria;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

/**
 * @author Tom
 */
public class SimpleExpression implements Criterion {
    // 属性名
    private String fieldName;
    // 对应值
    private Object value;
    // 计算符
    private Operator operator;

    protected SimpleExpression(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    protected SimpleExpression(String fieldName, Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        Path expression;
        if (fieldName.contains(".")) {
            String[] names = StringUtils.split(fieldName, ".");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        } else {
            expression = root.get(fieldName);
        }

        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case NE:
                return builder.notEqual(expression, value);
            case LIKE:
                return builder.like((Expression<String>) expression, "%" + value + "%");
            case LEFTLIKE:
                return builder.like((Expression<String>) expression, "%" + value);
            case RIGHTLIKE:
                return builder.like((Expression<String>) expression, value + "%");
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case ISNULL:
                return builder.isNull(expression);
            case ISNOTNULL:
                return builder.isNotNull(expression);
            case NOTLIKE:
                return builder.notLike(expression, "%" + value + "%");
            default:
                return null;
        }
    }
}
