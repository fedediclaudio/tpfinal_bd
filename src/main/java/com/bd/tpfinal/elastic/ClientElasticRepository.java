package com.bd.tpfinal.elastic;

import com.bd.tpfinal.model.ClientElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ClientElasticRepository extends ElasticsearchRepository<ClientElastic, String> {

}
