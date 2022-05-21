package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricalProductPriceRepository extends MongoRepository<HistoricalProductPrice, String> {

    /*
     trate de realizar la consulta de forma nativa, pero en el shell de mongodb debia declarar una variable date,
     para el caso en el que finishDate no existiese (el caso del precio actual), pero no fue posible.
     Tampoco fue posible crear un metodo de spring data, me daba siempre resultados vacios.
     de modo que opte por buscar el producto por ID y recuperar los precios mediante una busqueda
     */
    @Query("{$and:[{startDate: {$gt: ?0 }, " +
            "{$or: [{finishDate: {$lt: ?1 }}, " +
            "{$and: [{finishDate:{$exists:false}},{ date: {$gt: ?1 }}]}]}, " +
            "{'product.$id': {$eq:  ObjectId(?2)}}]}")
    List<HistoricalProductPrice> findByProductIdAndStartDateAndFinishDate(String from, String to, String id );

    List<HistoricalProductPrice> findByStartDateGreaterThanAndFinishDateLessThanAndProduct_Id(Date fromDate, Date toDate, String id);

    List<HistoricalProductPrice> findByProductId(String id);
}
