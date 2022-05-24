package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductTypeAvgPrice_DTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    @Query(value="SELECT producto FROM Product producto WHERE (producto.id = :id_product AND producto.eliminado != 1)")
    Optional<Product> findById(@Param("id_product") Long id_product);

    List<Product> findByName(@Param("name") String name);

    @Query(value="SELECT producto FROM Product producto WHERE (producto.type.id = :id_type AND producto.eliminado != 1)")
    List<Product> findByTypeId(@Param("id_type") Long id_type );

    //13) Obtener el precio promedio de los productos de cada tipo, para todos los tipos.
    /*
      @Query("SELECT new com.devutil.examples.spring.jpa.repository.customobjects.EntidadNombre(e.nombre, COUNT(*)) "
       + "FROM Entidad e "
       + "GROUP BY e.nombre "
       + "HAVING COUNT(*) > 1 "
       + "ORDER BY 2 DESC, 1 ASC")
  List<EntidadNombre> calcularGroupByEntidadNombre();
     */
    //SQL: SELECT id_product_type,AVG(price) FROM db_delivery.products group by id_product_type;
    String q13 = "SELECT new com.bd.tpfinal.model.ProductTypeAvgPrice_DTO(p.id, avg_price,productType)"
            +" FROM Product p "
            +" WHERE p.id avg_price productType IN ( SELECT AVG(p.price),  FROM Product p GROUP BY p.type.id)";

    //@Query(value = "select count(v) as cnt, v.answer from Survey v group by v.answer")
    //public List<?> findSurveyCount();

    //@Query("SELECT " +
    //           "    new com.path.to.SurveyAnswerStatistics(v.answer, COUNT(v)) " +
    //           "FROM " +
    //           "    Survey v " +
    //           "GROUP BY " +
    //           "    v.answer")
    //    List<SurveyAnswerStatistics> findSurveyCount();
    String q13_1 = "SELECT p.type.id, AVG(p.price) as promedio,  FROM Product p GROUP BY p.type.id)";
    String q13_2 = "SELECT new com.bd.tpfinal.model.ProductTypeAvgPrice_DTO(p.type.id, AVG(p.price) ) "
            +" FROM Product p "
            +" GROUP BY p.type.id";
    @Query(value=q13_2)
    List<ProductTypeAvgPrice_DTO> findAllAvgPriceForProductType();

}
