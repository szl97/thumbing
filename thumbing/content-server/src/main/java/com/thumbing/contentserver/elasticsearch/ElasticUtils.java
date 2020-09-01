package com.thumbing.contentserver.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.shared.utils.context.SpringContextUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Stan Sai
 * @date 2020-07-03
 */
@Slf4j
@Data
public class ElasticUtils {

    private ObjectMapper objectMapper = SpringContextUtils.getBean(ObjectMapper.class);

    private static ElasticUtils INSTANCE = new ElasticUtils();

    private RestHighLevelClient client;

    private static Object lock = new Object();

    private ElasticUtils() {

    }

    public static ElasticUtils getInstance(RestHighLevelClient esClient) {
        if (INSTANCE.getClient() == null) {
            synchronized (lock) {
                if (INSTANCE.getClient() == null) {
                    INSTANCE.setClient(esClient);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化索引
     *
     * @param indexName
     * @param shardNum
     * @param replicasNum
     * @param builder
     * @return
     */

    public Boolean initIndex(String indexName,
                             int shardNum,
                             int replicasNum,
                             XContentBuilder builder) {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        //设置分片和副本
        request.settings(Settings.builder()
                .put("index.number_of_shards", shardNum)
                .put("index.number_of_replicas", replicasNum)
        );
        request.mapping(builder);
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createIndexResponse.isAcknowledged();
    }

    /**
     * @param indexName
     * @param jsonString
     * @return
     */

    public boolean indexDoc(String indexName,
                            String jsonString) {

        IndexRequest indexRequest = new IndexRequest(indexName)
                .source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    public boolean existIndex(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName) {
        try {
            if (existIndex(indexName)) {
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
                AcknowledgedResponse acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                return acknowledgedResponse.isAcknowledged();
            } else {
                log.info("索引不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 批量索引文档
     * @param indexName
     * @param docList
     * @return
     */

    public boolean indexDoc(String indexName,
                            List docList) {
        BulkRequest bulkRequest = new BulkRequest();
        Iterator<String> iter = docList.iterator();
        while (iter.hasNext()) {
            String jsonString = iter.next();
            IndexRequest indexRequest = new IndexRequest(indexName)
                    .source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.error("批量索引失败");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 修改文档内容
     * @param indexName
     * @param type
     * @param id
     * @param content
     * @throws IOException
     */
    public void updateDocContent(String indexName,
                          String type,
                          String id,
                          String content) throws IOException {

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", type);
        boolBuilder.must(termQueryBuilder);
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("contentId", id);
        boolBuilder.must(termQueryBuilder1);
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(indexName);
        updateByQueryRequest.setQuery(boolBuilder);
        updateByQueryRequest.setMaxDocs(1);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("content", content);
        ScriptType scriptType = ScriptType.INLINE;
        String lang = "painless";
        String code = "ctx._source.content=params.content";
        Script script = new Script(scriptType, lang, code, params);
        updateByQueryRequest.setScript(script);
        client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除文档
     * @param indexName
     * @param type
     * @param id
     * @throws IOException
     */
    public void deleteDoc(String indexName,
                          String type,
                          String id) throws IOException {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", type);
        boolBuilder.must(termQueryBuilder);
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("contentId", id);
        boolBuilder.must(termQueryBuilder1);
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(indexName);
        deleteByQueryRequest.setQuery(boolBuilder);
        client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
    }

    /**
     * 搜索文档
     * @param indices
     * @param keyword
     * @param fieldNames
     * @param pageNum
     * @param pageSize
     * @return
     */
    public <T> List<T> searchDocs(String indices,
                                  String keyword,
                                  String[] fieldNames,
                                  int pageNum,
                                  int pageSize,
                                  Class<T> clazz){
        return searchDocs(indices, keyword, fieldNames, pageNum, pageSize, clazz, null, null, null);
    }

    /**
     * 搜索文档
     * @param indices
     * @param keyword
     * @param fieldNames
     * @param pageNum
     * @param pageSize
     * @return
     */
    public <T> List<T> searchDocs(String indices,
                                  String keyword,
                                  String[] fieldNames,
                                  int pageNum,
                                  int pageSize,
                                  Class<T> clazz,
                                  @Nullable List<Long> blackList,
                                  @Nullable String type,
                                  @Nullable LocalDateTime dateTime) {
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        //name代表查询的类别是文章还是帖子，所以必须是相同
        if (StringUtils.isNotBlank(type)) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", type);
            boolBuilder.must(termQueryBuilder);
        }
        //黑名单中的用户查不到
        if (CollectionUtils.isNotEmpty(blackList)) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", blackList.toArray());
            boolBuilder.mustNot(termQueryBuilder);
        }
        //日期太早的不去查
        if (dateTime != null) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                    .rangeQuery("date")
                    .gte(dateTime);
            boolBuilder.must(rangeQueryBuilder);
        }
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders
                .multiMatchQuery(keyword, fieldNames)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .tieBreaker(0.3f);
        boolBuilder.must(multiMatchQuery);
        //设置高亮 tags title content abstracts要设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content");
        highlightBuilder.field(highlightContent);
        HighlightBuilder.Field highlightTags = new HighlightBuilder.Field("tags");
        highlightBuilder.field(highlightTags);
        //设置高亮的格式为字体红色
        highlightBuilder
                .preTags("<span style=color:red>")
                .postTags("</span>");
        //高亮
        searchSourceBuilder.highlighter(highlightBuilder);
        //查询语句
        searchSourceBuilder.query(boolBuilder);
        //分页
        searchSourceBuilder.from((pageNum - 1) * pageSize);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        List<T> resultList = new ArrayList<>();
        try {
            SearchResponse searchResponse = client
                    .search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                String source = hit.getSourceAsString();
                T item = objectMapper.readValue(source, clazz);
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields){
                    field.setAccessible(true);
                    if (highlightFields.containsKey(field.getName())) {
                        try {
                            field.set(item, highlightFields.get(field.getName()).fragments()[0].toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                resultList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
