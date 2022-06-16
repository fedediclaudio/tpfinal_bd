package com.bd.tpfinal.services;

import com.bd.tpfinal.elastic.ClientElasticRepository;
import com.bd.tpfinal.model.ClientElastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientElasticService {

    @Autowired
    ClientElasticRepository clientElasticRepository;

    public List<ClientElastic> getAll(){
        return StreamSupport
                .stream(clientElasticRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
