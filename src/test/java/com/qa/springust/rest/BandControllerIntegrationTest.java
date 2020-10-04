package com.qa.springust.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springust.dto.BandDTO;
import com.qa.springust.dto.GuitaristDTO;
import com.qa.springust.persistence.domain.Band;
import com.qa.springust.persistence.repository.BandRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BandControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private BandRepository repo;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Long id;
    private Band testBand;
    private Band testBandWithId;
    private BandDTO dto;
    private List<GuitaristDTO> guitarist;
    
    private BandDTO mapToDTO(Band band) {
        return this.modelMapper.map(band, BandDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testBand = new Band("The Mountain Goats");
        this.testBandWithId = this.repo.save(this.testBand);
        this.dto = this.mapToDTO(testBand);
        this.id = this.testBandWithId.getId();
        this.guitarist = new ArrayList<>();
    }

    @Test
    void testCreate() throws Exception {
        this.mock
            .perform(request(HttpMethod.POST, "/band/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(testBand))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
    }

    @Test
    void testRead() throws Exception {
        this.mock.perform
            (request(HttpMethod.GET, "/band/read/" + this.id)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
    }

    @Test
    void testReadAll() throws Exception {
        List<BandDTO> bandList = new ArrayList<>();
        bandList.add(this.dto);
        
        String expected = this.objectMapper.writeValueAsString(bandList);
        String content = this.mock
            .perform(request(HttpMethod.GET, "/band/read")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        assertEquals(this.objectMapper.writeValueAsString(bandList), content);
    }

    @Test
 //   void testUpdate()  throws Exception {
		// Initialise list of Guitarists for use
		
	//	BandDTO newDTO = new BandDTO(this.id, "Pink Floyd", this.guitarist);
	//	Band updatedBand = new Band(
	//			newDTO.getName());
	//	updatedBand.setId(this.id);
		
		// Stringify for comparison
	//	String expected = this.objectMapper.writeValueAsString(
	//			this.mapToDTO(updatedBand));
		
	//	String actual = this.mock.perform(
	//			request(HttpMethod.PUT, "/band/update/" + this.id)
	//			.contentType(MediaType.APPLICATION_JSON).content(
	//					this.objectMapper.writeValueAsString(newDTO))
	//			.accept(MediaType.APPLICATION_JSON))
	//			.andExpect(status().isAccepted())
	//			.andReturn().getResponse().getContentAsString();
		
		// Test assertion
	//	assertEquals(expected, actual);
    
    
	 void testUpdate() throws Exception {
    	BandDTO newBand = new BandDTO(null, "take the car to the carwash", null);
    	Band updatedBand = new Band(newBand.getName());
        updatedBand.setId(this.id);
        String expected = this.objectMapper.writeValueAsString(this.mapToDTO(updatedBand));

        String actual = this.mock.perform(request(HttpMethod.PUT, "/band/update/" + this.id) 
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(newBand))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()) // 201
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    
    
    
    
    
    
    
    
    
    
    }
    
    @Test
    void testDelete() throws Exception {
        this.mock
            .perform(request(HttpMethod.DELETE, "/band/delete/" + this.id))
            .andExpect(status().isNoContent());
    }

}
