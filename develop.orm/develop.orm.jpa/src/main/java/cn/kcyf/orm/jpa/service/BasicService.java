package cn.kcyf.orm.jpa.service;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.entity.IdDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BasicService<T extends IdDomain, ID extends Serializable> {
    BasicDao<T, ID> getRepository();

    List<T> findAll();

    Page<T> findList(Specification<T> specification, Pageable pageable);

    List<T> findList(Specification<T> specification);

    List<T> findList(Specification<T> specification, Sort sort);

    T getOne(ID id);

    T getOne(Specification<T> specification);

    T create(T o);

    T update(T o);

    void deletes(List<ID> ids);

    void delete(ID id);

    long count();

    long countBy(Specification<T> specification);

    Page<T> findListBySql(String sql, Map<String, Object> params, Pageable pageable, Class<T> clazz);

    List<T> findListBySql(String sql, Map<String, Object> params, Class<T> clazz);

    Page<T> findListByHql(String hql, Map<String, Object> params, Pageable pageable);

    List<T> findListByHql(String hql, Map<String, Object> params);

    long countBySql(String sql, Map<String, Object> params);

    long countByHql(String hql, Map<String, Object> params);
}
