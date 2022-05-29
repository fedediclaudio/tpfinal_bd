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
    //Claro, una orden es de un solo proveedor, todos los ítems de productos son del mismo proveedor.
    // Bueno ahi vas a tenes que buscar como hacer la conexion entre una orden y un proovedor
    // y ahi agrupar por proveedor las ordenes y poder hacer el count de cada uno, me explico?
    // Fijate que el item deberia tener el id de la orden y el producto tiene el id del proveedor,
    // uniendo estas dos tablas con un inner join tenes todos los datos que precisas para agrupar y contar.

    //SELECT id_supplier, id_order_number
    //FROM db_delivery.items INNER JOIN db_delivery.orders INNER JOIN db_delivery.products
    //WHERE (db_delivery.orders.number = db_delivery.items.id_order_number AND db_delivery.items.product_id_product = db_delivery.products.id_product )
    //GROUP BY id_supplier, id_order_number
    //ORDER BY id_supplier, id_order_number;

    //SELECT * FROM db_delivery.items;
    //SELECT * FROM db_delivery.items INNER JOIN db_delivery.orders INNER JOIN db_delivery.products WHERE (db_delivery.orders.number = db_delivery.items.id_order_number);
    //
    //
    //SELECT id_supplier, id_order_number
    //FROM db_delivery.items INNER JOIN db_delivery.orders INNER JOIN db_delivery.products
    //WHERE (db_delivery.orders.number = db_delivery.items.id_order_number AND db_delivery.items.product_id_product = db_delivery.products.id_product )
    //GROUP BY id_supplier, id_order_number
    //ORDER BY id_supplier, id_order_number;
    //
    //SELECT number, count(number) as cantidad FROM db_delivery.items INNER JOIN db_delivery.orders INNER JOIN db_delivery.products WHERE (db_delivery.orders.number = db_delivery.items.id_order_number) GROUP BY number;
    //
    //
    //SELECT id_supplier, id_product FROM db_delivery.products WHERE id_supplier IN (
    //
    //SELECT DISTINCT id_supplier,  count(id_order_number)
    //FROM db_delivery.items INNER JOIN db_delivery.products
    //WHERE db_delivery.items.product_id_product = db_delivery.products.id_product
    //GROUP BY id_supplier, id_order_number
    //ORDER BY count(id_order_number)
    //
    //);
    //
    //SELECT * FROM db_delivery.items;

    String aux = "SELECT item.order FROM Item item ";
   // String q11_1= "SELECT orden FROM Order orden  WHERE orden IN " +
           // "( SELECT item.order FROM Item item INNER JOIN item.product.supplier WHERE item.product.supplier IN " +
          //  "( ";


    @Query(value="SELECT supplier FROM Supplier supplier")
    List<Supplier> findTopTenSupplier();

    @Query(value = aux)
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

    String q14 = "SELECT supplier FROM Supplier supplier WHERE ROUND(supplier.qualificationOfUsers) = 2.F";

    @Query(value = q14)
    List<Supplier> findByQualification1();

    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.
    //Corregir esto
    //String q15 = "SELECT product.supplier FROM Product product WHERE product.type IN " +
           // "( select product.type, count(product.type) FROM Product product WHERE count(product.type) = 3) " +
         //   "GROUP BY product.supplier";

    String q15 = "SELECT product.supplier FROM Product product WHERE product.type IN " +
            "       ( SELECT product.type, count(product.type) FROM Product product " +
            "           WHERE count(product.type) = (SELECT count(type) FROM ProductType type)) " +
            "   GROUP BY product.supplier";

    @Query(value = q15)
    List<Supplier> findSupplierWithAllTypes();
}
