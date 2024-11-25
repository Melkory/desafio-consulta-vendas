package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projection.SaleProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value =
            "select tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name " +
                    "from tb_sales " +
                    "inner join tb_seller " +
                    "on tb_sales.seller_id = tb_seller.id " +
                    "where (:name IS NULL OR UPPER(tb_seller.name) like CONCAT('%', UPPER(:name), '%')) " +
                    "and (:minDate IS NULL OR tb_sales.date >= :minDate) " +
                    "and (:maxDate IS NULL OR tb_sales.date <= :maxDate) " +
                    "order by tb_sales.id desc")
    List<SaleProjection> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name);


}
