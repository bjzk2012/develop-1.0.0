package cn.kcyf.orm.jpa.dao;

import cn.kcyf.orm.jpa.entity.IdDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface BasicDao<T extends IdDomain, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
