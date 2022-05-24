package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>
{
    List<Supplier> findByName(@Param("name") String name);

    @Query(value= "SELECT DISTINCT sup FROM Supplier sup WHERE sup.supplierType.id = :supplier_type_id")
    List<Supplier> findBySupplierTypeId(@Param("supplier_type_id") Long supplier_type_id);

    //11) Obtener los diez proveedores que más órdenes despacharon.
    //todas las Order de un Supplier-----------------------
    String query5 = "SELECT DISTINCT item.order FROM Item item  WHERE item IN (SELECT item FROM Item item WHERE item.product.supplier.id = :id_supplier)";
    String ordenes_entregadas = "(SELECT orden FROM Order orden WHERE orden.orderStatus.name = 'Delivered')";
    //String items_de_ordenes_entregadas = "SELECT item FROM Item item WHERE item.orden IN ((SELECT DISTINCT orden FROM Order orden WHERE orden.orderStatus.name = Delivered) GROUP BY item.product.supplier)";

    String items_de_ordenes_entregadas = "SELECT item FROM Item item WHERE item.order.orderStatus.name = 'Delivered'";
    String suppliers_de_ordenes_entregadas = "SELECT DISTINCT item.product.supplier FROM Item item WHERE item IN ("+items_de_ordenes_entregadas +" )";
    @Query(value= suppliers_de_ordenes_entregadas)
    List<Supplier> findSuppliers();

    //muestra las ordenes pero con un mismo supplier repetido
    String ordenes_por_supplier ="SELECT item.order FROM Item item WHERE item.product.supplier IN (" + suppliers_de_ordenes_entregadas +" )";
    @Query(value = ordenes_por_supplier)
    List<Order> findOrder();

    String q1 = "SELECT item.product.supplier FROM Item item WHERE item.order IN ( " + ordenes_por_supplier + " )";
    //@Query(value = q1)
    //List<Supplier> findSuppliers();

    String q2 = "SELECT item.order, item.product.supplier FROM Item item";

    //14) Obtener la información de los proveedores que tengan al menos una calificación de una estrella (la más baja).
    // Es necesario también el número de estas calificaciones que el proveedor posee.
    // las calificaciones salen de la calificacion que da el cliente a cada orden. Cada item de la orden se queda con esa calificación
    // de esa forma se lo vincula con el supplier. Item->Product->Supplier  =  Item->Order->Qualification

    String q14 = "SELECT supplier FROM Supplier supplier WHERE supplier.qualificationOfUsers < 2.F";
    @Query(value=q14)
    List<Supplier> findByQualification1();
}
