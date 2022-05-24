package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.http.HttpHeaders.FROM;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long>
{
    String query = "Select dm from DeliveryMan dm where dm.active = true AND dm.free = true";
    @Query(value = query)
    List<DeliveryMan> findAllFree();

    String query10 = "SELECT dm FROM DeliveryMan dm ORDER BY dm.score DESC";
    @Query(value=query10)
    List<DeliveryMan> findTop10OrderByScore(Pageable pageable);
   // @Query("FROM DeliveryMan" )
    //@Query(value = query10)
    //List<DeliveryMan> findAllOrderByScore();
    //List<DeliveryMan> findAllOrderByScore(Sort sort);
    //List<DeliveryMan> findTop10OrderByScore(Sort sort, Pageable pageable);

    //@Query("SELECT a FROM Arcust a WHERE a.arcustno<='?1' ORDER BY a.arcustno DESC")
    //List<Arcust> findByTop(String arcustno, Pageable pageable);
}

