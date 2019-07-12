package cn.kcyf.tools.es.repository;

import cn.kcyf.tools.es.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
    Page<Book> findByName(String name, Pageable pageable);
}
