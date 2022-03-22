package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Prueba;
import com.bd.tpfinal.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PruebaService
{
    private final PruebaRepository pruebaRepository;

    @Autowired
    public PruebaService(PruebaRepository pruebaRepository)
    {
        this.pruebaRepository = pruebaRepository;
    }

    public void addPrueba(Prueba newPrueba)
    {
        this.pruebaRepository.save(newPrueba);
    }

    public List<Prueba> getAll()
    {
        return this.pruebaRepository.findAll();
    }
}
