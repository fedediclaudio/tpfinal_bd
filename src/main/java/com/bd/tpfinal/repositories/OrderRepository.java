package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    //@Query(value = "SELECT o FROM Order o WHERE o.order.number = :number")
    Order findByNumber(@Param("number") Long number);

    @Query(value = "SELECT orden FROM Order orden")
    List<Order> findAll();


    //encontrar todas las ordenes sin califiación
    String q1="SELECT orden FROM Order orden WHERE orden.qualification.score = 0F" +
            "AND orden.orderStatus.name = 'Delivered'";
    @Query(value=q1)
    List<Order> findAllWithoutQual();

    //https://docs.jboss.org/hibernate/orm/3.5/reference/es-ES/html/queryhql.html
    String query1 = "SELECT orden FROM Order orden WHERE orden.items.product.supplier.id = :id_supplier";
    String query2 = "SELECT orden FROM Order orden WHERE orden.items[].product.supplier.id = :id_supplier";
    String query3 = "SELECT item FROM Item item WHERE item.product.supplier = :id_supplier";
    String query4 = "SELECT orden FROM Order orden WHERE orden.items IN (SELECT item FROM Item item WHERE item.product.supplier = :id_supplier)";


    //todos los items de un supplier
    String queryA = "SELECT item from Item item WHERE item.product.supplier.id = :id_supplier";

    //todas las Order de un Supplier-----------------------
    String query5 = "SELECT DISTINCT item.order FROM Item item  WHERE item IN (SELECT item FROM Item item WHERE item.product.supplier.id = :id_supplier)";
    @Query(value = query5)
    List<Order> findBySupplier(@Param("id_supplier") Long id_supplier);

    String query6 = "SELECT avg(orden.qualification.score) FROM Order orden WHERE orden IN" + "("+ query5 + ")";
    @Query(value = query6)
    float findQualificationSupplier(@Param("id_supplier") Long id_supplier);

    @Query(value = "SELECT orden FROM Order orden WHERE orden.client.id = :id_user ")
    List<Order> findByClient(@Param("id_user") Long id_user);


    //cantidad de items de una orden que son de un supplier
    String query7 = "SELECT count(item.product.supplier.id = :id_supplier), Order orden FROM Item item WHERE orden IN " + "("+ query5 + ")";
     //findOrderBySupplierItems(Long id_supplier);
    // Obtener las órdenes con más productos de un proveedor específico.
    String query8 = "SELECT orden FROM Order orden WHERE IN"+ "("+ query5 + ")";

    //con join-las ordenes de un supplier
    String query9="SELECT DISTINCT orden FROM Order orden, Item item left join item.order WHERE item.product.supplier.id = :id_supplier";

    String query10 = "SELECT DISTINCT orden FROM Order orden, Item item inner join item.order "+
            "WHERE item.product.supplier.id = :id_supplier GROUP BY orden.id "+
            "ORDER BY count(item)";

    //   //todas las Order de un Supplier-----------------------
    String query11 = "SELECT DISTINCT item.order FROM Item item  WHERE item IN (SELECT item FROM Item item WHERE item.product.supplier.id = :id_supplier) group by order";
    @Query(value=query5)
    List<Order> findOrderBySupplierItems(@Param( "id_supplier")Long id_supplier);

    //9) Obtener la orden de mayor precio total de un día dado.
    String query_9_1 = "SELECT orden, max(orden.totalPrice) FROM Order orden WHERE orden.totalPrice = max(orden.totalPrice) AND orden.dateOfOrder = :fecha)";
    String query_9_2 = "SELECT orden FROM Order orden WHERE orden.dateOfOrder = :fecha AND orden.totalPrice= (SELECT MAX(orden.totalPrice) FROM Order orden)";

    @Query(value = query_9_2)
    List <Order> findMaxOrderByDate(@Param("fecha") Date fecha);

    Order findByDateOfOrder(@Param("fecha") Date fecha);

    @Modifying
    @Query("UPDATE Order orden SET orden.qualification =:qualification WHERE orden.number = :number")
    void updateQualification(@Param(value= "number") Long number, @Param(value = "qualification") Qualification qualification);




}
