package cn.kcyf.tools.es.config;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "cn.kcyf.tools.es.repository")
public class EsConfig {
}
