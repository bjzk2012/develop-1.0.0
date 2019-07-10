package cn.kcyf.tools.es.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "book", type = "book", shards = 1, replicas = 0, refreshInterval = "-1")
public class Book {
    @Id
    private String id;
    private String name;
    private String author;
}
