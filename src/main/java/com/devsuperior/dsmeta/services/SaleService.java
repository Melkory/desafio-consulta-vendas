package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.projection.SaleProjection;
import org.springframework.beans.factory.annotation.Autowired;
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

	public List<SaleMinDTO> getReport(String minDate, String maxDate, String name) {

		LocalDate dateMax = (maxDate == null) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);

		LocalDate dateMin = (minDate == null) ? dateMax.minusYears(1L) : LocalDate.parse(minDate);

		if (name == null || name.isEmpty()) {
			List<SaleProjection> result =repository.getReport(dateMin, dateMax, "");
			return result.stream().map(x -> new SaleMinDTO(x)).collect(Collectors.toList());
		}

		List<SaleProjection> result = repository.getReport(dateMin, dateMax, name);
		return result.stream().map(x -> new SaleMinDTO(x)).collect(Collectors.toList());

	}
}
