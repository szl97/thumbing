package com.thumbing.contentserver.elasticsearch;

import com.thumbing.contentserver.elasticsearch.ElasticUtils;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Stan Sai
 * @date 2020-07-03
 */
@Data
@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private String port;
    @Value("${elasticsearch.clusterName}")
    private String clusterName;
    @Value("${elasticsearch.schema}")
    private String schema;

    @Autowired
    private Index index;

    @Data
    @Configuration
    public class Index {
        @Value("${elasticsearch.index.name}")
        private String name;
        @Value("${elasticsearch.index.shardNum}")
        private int shardNum;
        @Value("${elasticsearch.index.replicasNum}")
        private int replicasNum;
        @Value("${elasticsearch.index.properties}")
        private String[] properties;

    }

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient getRestClient() {
        String[] hosts = host.split(",");
        String[] ports = port.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for(int i = 0; i < hosts.length; i++){
            httpHosts[i] = new HttpHost(hosts[i].trim(), Integer.parseInt(ports[i].trim()), schema);
        }
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHosts));
        return client;
    }

    @Bean
    public ElasticUtils getEsUtils(@Qualifier("restHighLevelClient") RestHighLevelClient client) {
        return ElasticUtils.getInstance(client);
    }

    @Component
    public class EsRunner implements ApplicationRunner {
        @Autowired
        private ElasticUtils esUtils;
        private Object lock = new Object();

        @Override
        public void run(ApplicationArguments args) throws Exception {


            if (!esUtils.existIndex(index.getName())) {

                synchronized (lock) {

                    if (!esUtils.existIndex(index.getName())) {
                        //设置mapping
                        XContentBuilder builder = null;
                        try {
                            builder = XContentFactory.jsonBuilder();

                            builder.startObject();
                            {
                                builder.startObject("properties");
                                {
                                    for (String s : index.getProperties()) {
                                        builder.startObject(s);
                                        {
                                            if (s.equals("date")) {
                                                builder.field("type", "datetime");
                                            } else if (s.equals("userId") || s.equals("id")) {
                                                builder.field("type", "long");
                                            } else {
                                                builder.field("type", "text");
                                                builder.field("analyzer", "ik_max_word");
                                            }
                                        }
                                        builder.endObject();
                                    }
                                }
                                builder.endObject();
                            }
                            builder.endObject();

                            //初始化索引
                            Boolean isSuccess = esUtils.initIndex(index.getName(), index.getShardNum(), index.getReplicasNum(), builder);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

}
