package com.devsuperior.dsmeta.projection;

import com.devsuperior.dsmeta.entities.Seller;

import java.time.LocalDate;

public interface SaleMinProjection {

    Long getId();
    LocalDate getDate();
    Double getAmount();
    Seller getName();

}
