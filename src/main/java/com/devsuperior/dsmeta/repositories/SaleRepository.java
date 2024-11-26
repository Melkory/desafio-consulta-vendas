package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projection.SaleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    "where UPPER(tb_seller.name) like CONCAT('%', UPPER(:name), '%') " +
                    "and tb_sales.date between :minDate and :maxDate ",
                    countQuery = "select count (*)" +
                            "from tb_sales " +
                            "inner join tb_seller " +
                            "on tb_sales.seller_id = tb_seller.id " +
                            "where UPPER(tb_seller.name) like CONCAT('%', UPPER(:name), '%') " +
                            "and tb_sales.date between :minDate and :maxDate "
                    )
    Page<SaleProjection> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name, Pageable pageable);

    @Query("select new com.devsuperior.dsmeta.dto.SellerMinDTO (obj.name, sum(s.amount)) " +
            "from Seller obj join obj.sales s " +
            "where s.date between :minDate and :maxDate " +
            "group by obj.name ")
    List<SellerMinDTO> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

}
