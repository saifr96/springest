package com.qa.springust.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.springust.dto.BandDTO;
import com.qa.springust.persistence.domain.Band;
import com.qa.springust.persistence.repository.BandRepository;

@SpringBootTest
public class BandServiceUnitTest {
	
	@Autowired
	private BandService service;
	
	@MockBean
	private BandRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	// Testing Variables
	private List<Band> bandList;
	private Band tester;
	private Band testerId;
	private Band emptyTester;
	private BandDTO dto;
	
	// Testing constants
	final Long id = 1L;
	final String exampleName = "Pink Floyd";
	// List of Guitarists here ? <== <== <==
	
	// Define Mapping Function
	private BandDTO mapToDTO(Band band) {
		return this.modelMapper.map(band, BandDTO.class);
	}
	
	// Initialise variables before each test
	@BeforeEach
	void init() {
		// Instantiate lists to emulate returned data
		this.bandList = new ArrayList<>();
		this.bandList.add(tester);
		
		// Create example band
		this.tester = new Band(
				this.exampleName);
		
		// Create id band
		this.testerId = new Band(
				this.exampleName);
		
		testerId.setId(this.id);
		
		// Create example empty band
		this.emptyTester = new Band();
		
		// Create DTO from band with Id
		this.dto = new ModelMapper().map(testerId, BandDTO.class);
	}
	
	@Test
	void createTest() {
		// Setup mock data
		when(this.modelMapper.map(mapToDTO(tester), Band.class))
		.thenReturn(tester);
		when(this.repo.save(tester))
		.thenReturn(testerId);
		when(this.modelMapper.map(testerId, BandDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.dto)
		.isEqualTo(this.service.create(tester));
		
		// Check repo methods used in this test
		verify(this.repo, times(1)).save(this.tester);
		
	}
}