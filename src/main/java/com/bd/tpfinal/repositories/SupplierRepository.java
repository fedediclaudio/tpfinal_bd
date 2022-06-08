package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.Supplier_Order_DTO;
import com.bd.tpfinal.model.Supplier_Qualif_DTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>
{
    List<Supplier> findByName(@Param("name") String name);

    @Query(value = "SELECT DISTINCT sup FROM Supplier sup WHERE sup.supplierType.id = :supplier_type_id")
    List<Supplier> findBySupplierTypeId(@Param("supplier_type_id") Long supplier_type_id);

    //11) Obtener los diez proveedores que más órdenes despacharon.
    //Claro, una orden es de un solo proveedor, todos los ítems de productos son del mismo proveedor.
    // Bueno ahi vas a tenes que buscar como hacer la conexion entre una orden y un proovedor
    // y ahi agrupar por proveedor las ordenes y poder hacer el count de cada uno, me explico?
    // Fijate que el item deberia tener el id de la orden y el producto tiene el id del proveedor,
    // uniendo estas dos tablas con un inner join tenes todos los datos que precisas para agrupar y contar.

    //PageRequest.of(0, 10) en el service
    //findxxx(Pageable pageable); en el repositorio
     String q11_1= "SELECT DISTINCT new com.bd.tpfinal.model.Supplier_Order_DTO( item.product.supplier.id, count(item.order.number)) " +
            "   FROM Item item INNER JOIN Product product " +
            "   ON item.product.id = product.id " +
            "   GROUP BY item.product.supplier" +
            "   ORDER BY count(item.order.number) DESC, item.product.supplier" ;

    @Query(value= q11_1)
    List<Supplier_Order_DTO> findTopTenSupplierWithOrders(PageRequest pageable);


    //borradores
    @Query(value=q11_1)
    List<Supplier> findTopTenSupplier();

    @Query(value = q11_1)
    List<Order> findOrderBYSupplier();

    //todas las Order de un Supplier-----------------------
    String query5 = "SELECT DISTINCT item.order FROM Item item  WHERE item IN (SELECT item FROM Item item WHERE item.product.supplier.id = :id_supplier)";
    String ordenes_entregadas = "(SELECT orden FROM Order orden WHERE orden.orderStatus.name = 'Delivered')";
    //String items_de_ordenes_entregadas = "SELECT item FROM Item item WHERE item.orden IN ((SELECT DISTINCT orden FROM Order orden WHERE orden.orderStatus.name = Delivered) GROUP BY item.product.supplier)";

    String items_de_ordenes_entregadas = "SELECT item FROM Item item WHERE item.order.orderStatus.name = 'Delivered'";
    String suppliers_de_ordenes_entregadas = "SELECT DISTINCT item.product.supplier FROM Item item WHERE item IN (" + items_de_ordenes_entregadas + " )";
    //@Query(value= suppliers_de_ordenes_entregadas)
    //List<Supplier> findSuppliers();


    //14) Obtener la información de los proveedores que tengan al menos una calificación de una estrella (la más baja).
    // Es necesario también el número de estas calificaciones que el proveedor posee.
    // las calificaciones salen de la calificacion que da el cliente a cada orden. Cada item de la orden se queda con esa calificación
    // de esa forma se lo vincula con el supplier. Item->Product->Supplier  =  Item->Order->Qualification
   String q14 = "SELECT supplier FROM Supplier supplier WHERE ROUND(supplier.qualificationOfUsers) = :valor";
   @Query(value = q14)
   List<Supplier> findSuppliersQualif_1(@Param("valor") float valor);


    //Order.qualification.score
    String cant_ordenes_qual_1 = "" +
            "SELECT count(item.order) AS cuenta FROM Item item " +
            "WHERE item.product.supplier.id = :id_supplier AND ROUND(item.order.qualification.score)= :valor" ;

    @Query(value = cant_ordenes_qual_1)
   int findCountBySupplierIdAndScoreOne(@Param("id_supplier") Long id_supplier, @Param("valor") float valor);


    String q14_2 = "SELECT item.product.supplier FROM Item item  " +
            "WHERE ROUND(item.product.supplier.qualificationOfUsers) = :valor AND " +
            "ROUND(item.order.qualification.score) = :valor " +
            "GROUP BY item.product.supplier ";
    @Query(value=q14_2)
    List<Supplier> findSuppliers_1(@Param("valor") float valor);

    String orders_qualif_uno ="" +
            "SELECT DISTINCT item.order FROM Item item WHERE item IN " +
            "(" +
            "SELECT item FROM Item item " +
            "WHERE item.product.supplier IN " +
            "   ( SELECT supplier FROM Supplier supplier WHERE ROUND(supplier.qualificationOfUsers) = 2.F) " +
            "GROUP BY item" +
            ")";

    @Query(value = orders_qualif_uno)
    List<Supplier> findCount();


    String query = "" +
            "SELECT  new com.bd.tpfinal.model.Supplier_Qualif_DTO(item.product.supplier.id, count(DISTINCT item.order.number)) " +
            "FROM Item item " +
            "WHERE  item.product.supplier IN " +
            "(" +
            "  SELECT supplier FROM Supplier supplier WHERE ROUND(supplier.qualificationOfUsers) = 1.F" +
            ") ";
    //@Query(value = query)
   // List<Supplier_Qualif_DTO> findByQualification1();

    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.
    //Corregir esto








}
