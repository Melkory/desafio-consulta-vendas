package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SellerProjection;

import java.util.ArrayList;
import java.util.List;

public class SellerMinDTO {

    private String name;
    private Double totalSum;

    public SellerMinDTO() {
    }

    public SellerMinDTO(String name, Double totalSum) {
        this.name = name;
        this.totalSum = totalSum;
    }

    public SellerMinDTO(SellerProjection projection) {
        name = projection.getName();
        totalSum = projection.getTotalSum();
    }

    public String getName() {
        return name;
    }

    public Double getTotalSum() {
        return totalSum;
    }
}
