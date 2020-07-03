package com.loserclub.pushdata.datacenter.config;

import com.loserclub.pushdata.common.utils.elasticsearch.ElasticUtils;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

    private String host;
    private int port;
    private String clusterName;
    private String schema;
    private List<Index> index;

    @Data
    public class Index {
        private String name;
        private int shardNum;
        private int replicasNum;
        private String[] properties;

    }

    @Bean(name="restHighLevelClient")
    public RestHighLevelClient getRestClient() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, schema)));
        return client;
    }

    @Bean
    public ElasticUtils getEsUtils(@Qualifier("restHighLevelClient") RestHighLevelClient client){
        return ElasticUtils.getInstance(client);
    }

    @Component
    public class EsRunner implements ApplicationRunner {
        @Autowired
        private ElasticUtils esUtils;
        private Object lock = new Object();
        @Override
        public void run(ApplicationArguments args) throws Exception {

            for (Index i : index) {
                if (!esUtils.existIndex(i.getName())) {

                    synchronized (lock) {

                        if (!esUtils.existIndex(i.getName())) {
                            //设置mapping
                            XContentBuilder builder = null;
                            try {
                                builder = XContentFactory.jsonBuilder();

                                builder.startObject();
                                {
                                    builder.startObject("properties");
                                    {
                                        for (String s : i.getProperties()) {
                                            builder.startObject(s);
                                            {
                                                if(!s.equals("date")) {
                                                    builder.field("type", "string");
                                                    builder.field("analyzer", "ik_max_word");
                                                }
                                                else{
                                                    builder.field("type", "datetime");
                                                }
                                            }
                                            builder.endObject();
                                        }
                                    }
                                    builder.endObject();
                                }
                                builder.endObject();

                                //初始化索引
                                Boolean isSuccess = esUtils.initIndex(i.getName(), i.getShardNum(), i.getReplicasNum(), builder);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

}
