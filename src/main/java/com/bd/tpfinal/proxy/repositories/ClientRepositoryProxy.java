package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

public interface ClientRepositoryProxy<T> {
    ClientDto findById(T id) throws PersistenceEntityException;
}
