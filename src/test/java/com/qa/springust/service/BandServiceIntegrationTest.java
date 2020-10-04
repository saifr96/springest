package com.qa.springust.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.springust.dto.BandDTO;
import com.qa.springust.dto.GuitaristDTO;
import com.qa.springust.persistence.domain.Band;
import com.qa.springust.persistence.domain.Guitarist;
import com.qa.springust.persistence.repository.BandRepository;

@SpringBootTest
public class BandServiceIntegrationTest {

	@MockBean
	private ModelMapper modelMapper;
	
	@Autowired
	private BandService service;
	
	@Autowired
	private BandRepository repo;
	
	private Band tester;
	private Band testerId;
	private List<Guitarist> guitarists;
	private BandDTO dto;
	
	// Mapping function

	private BandDTO mapToDTO(Band band) {
		return this.modelMapper.map(band, BandDTO.class);
	}
	
	// Testing constants
	final Long id = 1L;
	final String exampleName = "Pink Floyd";
	
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Clear
		this.tester = new Band(this.exampleName);
		this.testerId = this.repo.save(tester);
	}
	
	@Test
	void testCreate() {
		// Test assertion
		assertThat(this.mapToDTO(this.testerId))
		.isEqualTo(this.service.create(tester));
	}
	
	@Test
	void testRead() {
		assertThat(this.service.read(testerId.getId()))
		.isEqualTo(this.mapToDTO(this.testerId));
	}
	
	@Test
	void testReadAll() {
		assertThat(Stream.of(
				this.mapToDTO(testerId))
				.collect(Collectors.toList()))
		.isEqualTo(this.service.read());
	}
	
	@Test
	void testUpdate() {
		Band newBand = new Band("Orange Floyd");
		newBand.setId(this.id);
		BandDTO updatedDTO = mapToDTO(newBand);
		assertThat(updatedDTO)
	    .isEqualTo(this.service.update(newBand, this.testerId.getId()));
		

	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.tester.getId()))
		.isTrue();
	}
}