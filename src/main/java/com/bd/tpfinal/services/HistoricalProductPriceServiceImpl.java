package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public HistoricalProductPrice getById(Long id)
    {
        return this.historicalProductPriceRepository.getById(id);
    }

    @Override
    @Transactional
    public List<HistoricalProductPrice> getPrices(Long id_product, Date desde, Date hasta)
    {
        System.out.println("desde: " + desde);
        //return this.historicalProductPriceRepository.findAllBetweenDates(id_product, desde, hasta);
        return this.historicalProductPriceRepository.findByProductId(id_product);

    }
    @Override
    @Transactional
    public void saveListHistoricalProductoPrice(List<HistoricalProductPrice> newHpp)
    {
        Iterator<HistoricalProductPrice> historicalProductPriceIterator = newHpp.iterator();
        while(historicalProductPriceIterator.hasNext())
        {
            HistoricalProductPrice hpp = (HistoricalProductPrice) historicalProductPriceIterator.next();
            this.historicalProductPriceRepository.save(hpp);
        }
    }
}
