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

    @Query(value = "SELECT DISTINCT sup FROM Supplier sup WHERE sup.supplierType.id = :supplier_type_id")
    List<Supplier> findBySupplierTypeId(@Param("supplier_type_id") Long supplier_type_id);

    //11) Obtener los diez proveedores que más órdenes despacharon.
    //Se puede resolver con una unica consulta.
    //Te dejo algunas pistas: con el GROUP BY podes agrupar por un cierto parámetro, tenes el COUNT para contar la cantidad de elementos estos grupos
    // y luego tenes el ORDER BY para ordenar estos resultados.
    //Asi podrias agrupar las ordenes por su deliveryMan y ver que cual es el grupo que mas tiene.
    //Vas a tener que usar el @Query por que el gruoup by no puede colocarse en la cabecera de un metodo.

    //Ordenes de los supplier
    // seleccionar items agrupados por supplier sin repetir orden

    String q11_0 = "SELECT item.order FROM Item item  WHERE item IN " +  //seleccionar todas las ordenes a partir de los items
            "(SELECT item FROM Item item WHERE item.product.supplier IN " + // seleccionar items que tienen sus suppliers
            "(SELECT supplier FROM Supplier supplier)) " +
            "GROUP BY item.order";

    @Query(value = q11_0)
    List<Order> findOrderBYSupplier();


    String q11_1 = "SELECT item.product.supplier FROM Item item ORDER BY item.product.supplier";
    @Query(value=q11_1)
    List<Supplier> findTopTenSupplier();



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

    String q14 = "SELECT supplier FROM Supplier supplier WHERE ROUND(supplier.qualificationOfUsers) = 2.F";

    @Query(value = q14)
    List<Supplier> findByQualification1();

    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.
    //Corregir esto
    String q15 = "SELECT product.supplier FROM Product product WHERE product.type IN " +
            "( select product.type, count(product.type) FROM Product product WHERE count(product.type) = 3) " +
            "GROUP BY product.supplier";

    @Query(value = q15)
    List<Supplier> findSupplierWithAllTypes();
}
