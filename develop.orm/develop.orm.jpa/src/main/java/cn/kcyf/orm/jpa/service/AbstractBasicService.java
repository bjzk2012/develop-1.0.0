package cn.kcyf.orm.jpa.service;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.entity.IdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Tom
 * 总业务接口的默认实现，实现了大多数业务通用方法，子类继承就可以使用
 */
public abstract class AbstractBasicService<T extends IdDomain, ID extends Serializable> implements BasicService<T, ID> {
    public abstract BasicDao<T, ID> getRepository();

    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findList(Specification<T> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findList(Specification<T> specification) {
        return getRepository().findAll(specification);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findList(Specification<T> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    @Override
    public T getOne(Specification<T> specification) {
        Optional<T> optional = getRepository().findOne(specification);
        if (optional != null) {
            return optional.get();
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public T getOne(ID id) {
        Optional<T> optional = getRepository().findById(id);
        if (optional != null) {
            return optional.get();
        }
        return null;
    }

    @Transactional
    @Override
    public T create(T o) {
        o.setId(null);
        return getRepository().save(o);
    }

    @Transactional
    @Override
    public T update(T o) {
        return getRepository().save(o);
    }

    @Transactional
    @Override
    public void deletes(List<ID> ids) {
        if (ids != null && !ids.isEmpty()) {
            for (ID id : ids) {
                getRepository().deleteById(id);
            }
        }
    }

    @Transactional
    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return getRepository().count();
    }

    @Transactional(readOnly = true)
    @Override
    public long countBy(Specification<T> specification) {
        return getRepository().count(specification);
    }

    private Page<T> page(Long count, Query query, Map<String, Object> params, Pageable pageable){
        if (params != null && !params.isEmpty()){
            for (String key : params.keySet()){
                query.setParameter(key, params.get(key));
            }
        }
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults((pageable.getPageNumber() + 1) * pageable.getPageSize());
        List resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            return new PageImpl<T>(resultList, pageable, count);
        } else {
            return new PageImpl<T>(new ArrayList<T>(), pageable, 0);
        }
    }

    private List<T> list(Query query, Map<String, Object> params){
        if (params != null && !params.isEmpty()){
            for (String key : params.keySet()){
                query.setParameter(key, params.get(key));
            }
        }
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findListBySql(String sql, Map<String, Object> params, Pageable pageable, Class<T> clazz) {
        Long count = countBySql(sql, params);
        Query query = entityManager.createNativeQuery(sql, clazz);
        return page(count, query, params, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findListBySql(String sql, Map<String, Object> params, Class<T> clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        return list(query, params);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findListByHql(String hql, Map<String, Object> params, Pageable pageable) {
        Long count = countByHql(hql, params);
        Query query = entityManager.createQuery(hql);
        return page(count, query, params, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findListByHql(String hql, Map<String, Object> params) {
        Query query = entityManager.createQuery(hql);
        return list(query, params);
    }

    @Transactional(readOnly = true)
    @Override
    public long countBySql(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        List<T> list = list(query, params);
        return list == null ? 0 : list.size();
    }

    @Transactional(readOnly = true)
    @Override
    public long countByHql(String hql, Map<String, Object> params) {
        Query query = entityManager.createQuery(hql);
        List<T> list = list(query, params);
        return list == null ? 0 : list.size();
    }
}
