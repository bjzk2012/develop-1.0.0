package cn.kcyf.orm.jpa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * jpa工具类
 *
 * @author Tom
 */
public class JpaUtil {

    /**
     * @param fieldName
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Specification<T> specEq(final String fieldName, final Object value) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (!StringUtils.isEmpty(fieldName) && value != null) {
                    Path exp = root;
                    exp = exp.get(fieldName);
                    return builder.equal(exp, value);
                }
                return builder.conjunction();
            }
        };
    }

}
