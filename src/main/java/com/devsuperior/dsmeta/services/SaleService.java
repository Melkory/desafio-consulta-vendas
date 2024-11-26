package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projection.SaleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {

		LocalDate dateMax;

		LocalDate dateMin;

		if (maxDate == null) {
			dateMax = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			dateMax = LocalDate.parse(maxDate);
		}

		if (minDate == null) {
			dateMin = dateMax.minusYears(1L);
		} else {
			dateMin = LocalDate.parse(minDate);
		}

		if (name == null || name.isEmpty()) {
			Page<SaleProjection> result = repository.getReport(dateMin, dateMax, "", pageable);
			return result.map(x -> new SaleMinDTO(x));
		}

		Page<SaleProjection> result = repository.getReport(dateMin, dateMax, name,pageable);
		return result.map(x -> new SaleMinDTO(x));

	}

	public List<SellerMinDTO> getSummary(String minDate, String maxDate) {

		LocalDate dateMax;

		LocalDate dateMin;

		if (maxDate == null) {
			dateMax = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			dateMax = LocalDate.parse(maxDate);
		}

		if (minDate == null) {
			dateMin = dateMax.minusYears(1L);
		} else {
			dateMin = LocalDate.parse(minDate);
		}

		return repository.getSummary(dateMin, dateMax);
	}
}
