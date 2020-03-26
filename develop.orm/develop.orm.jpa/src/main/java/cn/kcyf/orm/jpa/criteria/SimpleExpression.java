package cn.kcyf.orm.jpa.criteria;

import javax.persistence.criteria.*;
import java.util.Collection;

/**
 * @author Tom
 */
public class SimpleExpression implements Criterion {
    private String fieldName;
    private Object value;
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
        Collection values;
        CriteriaBuilder.In<Object> in = null;
        if (fieldName.contains(".")) {
            String[] names = fieldName.split("\\.");
            expression = root.join(names[0], JoinType.LEFT);
            for (int i = 0; i < names.length; i++) {
                if (i > 0 && i < names.length - 1) {
                    expression = ((Join) expression).join(names[i], JoinType.LEFT);
                } else if (i != 0) {
                    expression = expression.get(names[i]);
                }
            }
        } else {
            expression = root.get(fieldName);
        }
        if (operator.equals(Operator.IN) || operator.equals(Operator.NOTIN)) {
            values = (Collection) value;
            in = builder.in(expression);
            for (Object object : values) {
                in.value(object);
            }
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
            case NOTLIKE:
                return builder.notLike(expression, "%" + value + "%");
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
            case IN:
                return in;
            case NOTIN:
                return builder.not(in);
            default:
                return null;
        }
    }
}
