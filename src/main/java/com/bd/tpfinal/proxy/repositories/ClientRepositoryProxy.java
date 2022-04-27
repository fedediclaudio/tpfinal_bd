package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

public interface ClientRepositoryProxy {
    ClientDto findById(String id) throws PersistenceEntityException;
}
