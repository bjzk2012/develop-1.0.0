package cn.kcyf.orm.jpa.service;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.entity.IdDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Tom
 * 总业务接口的默认实现，实现了大多数业务通用方法，子类继承就可以使用
 */
public abstract class AbstractBasicService<T extends IdDomain, ID extends Serializable> implements BasicService<T, ID>{
    public abstract BasicDao<T, ID> getRepository();

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
    public void delete(ID id){
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return 0;
    }
}
