package cn.kcyf.orm.jpa.service;

import cn.kcyf.orm.jpa.entity.IdDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface BasicService<T extends IdDomain, ID extends Serializable> {
    JpaRepository<T, ID> getRepository();

    List<T> findAll();

    T getOne(ID id);

    T create(T o);

    T update(T o);

    void deletes(List<ID> ids);

    long count();
}
