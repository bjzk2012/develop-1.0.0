package cn.kcyf.orm.jpa.service;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.entity.IdDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

public interface BasicService<T extends IdDomain, ID extends Serializable> {
    BasicDao<T, ID> getRepository();

    List<T> findAll();

    Page<T> findList(Specification<T> specification, Pageable pageable);

    List<T> findList(Specification<T> specification);

    T getOne(ID id);

    T create(T o);

    T update(T o);

    void deletes(List<ID> ids);

    void delete(ID id);

    long count();
}
