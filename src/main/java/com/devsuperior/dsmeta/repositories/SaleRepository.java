package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projection.SaleMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "AND tb_seller.name LIKE %:name%")
    List<SaleMinProjection> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, String name);

    @Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "AND tb_seller.name LIKE %:name%")
    List<SaleMinProjection> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, String name);

}
