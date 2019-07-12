package cn.kcyf.tools.solr.controller;

import cn.kcyf.tools.solr.models.SolrContent;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SolrController {

    @Autowired
    private SolrClient solrClient;

    //单个增加
    @RequestMapping("/add")
    public SolrContent add() throws IOException, SolrServerException {
        SolrContent content = new SolrContent();
        content.setId("456788");
        content.setName("1");
        content.setTitle("jajaj");
        content.setSubject("asdasd");
        content.setDescription("wwwwwwwwwwwwww");
        content.setUrl("123123");
        solrClient.addBean(content);
        solrClient.commit();
        return content;
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public SolrDocument get(@PathVariable("id") String id) throws IOException, SolrServerException {
        return solrClient.getById(id);
    }

    @RequestMapping("/del/{id}")
    public UpdateResponse del(@PathVariable("id") String id) throws IOException, SolrServerException {
        return solrClient.deleteById(id);
    }

    @RequestMapping("/query")
    public Object query() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.add("q", "id:4567");
        solrQuery.setSort("id", SolrQuery.ORDER.asc);
        solrQuery.setRows(50);
        solrQuery.setStart(0);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        System.out.println(solrQuery);
        QueryResponse response = solrClient.query(solrQuery);
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        SolrDocumentList documentList = response.getResults();
        return documentList;
    }
}
