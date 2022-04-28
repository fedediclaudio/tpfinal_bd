package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class HistoricalProductPriceServiceImpl implements HistoricalProductPriceService
{
    private final HistoricalProductPriceRepository historicalProductPriceRepository;

    @Autowired
    public HistoricalProductPriceServiceImpl(HistoricalProductPriceRepository historicalProductPriceRepository)
    {
        this.historicalProductPriceRepository = historicalProductPriceRepository;
    }

    @Override
    public void newHistoricalProductPrice(HistoricalProductPrice newHistoricalProductPrice)
    {
        this.historicalProductPriceRepository.save(newHistoricalProductPrice);
    }

    /**
     *
     * @param
     * @return Listado de los HistoricalProductPrice de un Producto.
     * Busca por product.id
     */
    @Override
    public List<HistoricalProductPrice> getAll(Long productId)
    {
        List<HistoricalProductPrice> hpp = null;
        if(isNull(productId))
        {
            hpp = this.historicalProductPriceRepository.findAll();
        }
        else
        {
            hpp = this.historicalProductPriceRepository.findByProductId(productId);
        }
        return hpp;
    }

    @Override
    public HistoricalProductPrice getById(Long id)
    {
        return this.historicalProductPriceRepository.getById(id);
    }
}
