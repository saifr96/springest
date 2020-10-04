package com.qa.springust.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.springust.dto.GuitaristDTO;
import com.qa.springust.persistence.domain.Guitarist;
import com.qa.springust.service.GuitaristService;

@SpringBootTest
class GuitaristControllerUnitTest {

    // the thing we're actually testing
    // (this is the real thing we've made)
    @Autowired
    private GuitaristController controller;

    // the mock thing that we're connecting to
    // so that any requests we receive are always valid
    @MockBean
    private GuitaristService service;

    // we need the mapper, because it works with the mock service layer
    @Autowired
    private ModelMapper modelMapper;

    // and we need the dto mapping as well, otherwise we can't test
    // our controller methods (which rely on RE<xDTO>)
    private GuitaristDTO mapToDTO(Guitarist guitarist) {
        return this.modelMapper.map(guitarist, GuitaristDTO.class);
    }

    private List<Guitarist> guitaristList;
    private Guitarist testGuitarist;
    private Guitarist testGuitaristWithId;
    private GuitaristDTO guitaristDTO;

    private final String name = "John Darnielle";
    private final Integer strings = 6;
    private final String type = "Ibanez Talman";
    private final Long id = 1L;

    @BeforeEach
    void init() {
        this.guitaristList = new ArrayList<>();
        this.testGuitarist = new Guitarist(name, strings, type);
        this.testGuitaristWithId = new Guitarist(testGuitarist.getName(), testGuitarist.getStrings(),
                testGuitarist.getType());
        this.testGuitaristWithId.setId(id);
        this.guitaristList.add(testGuitaristWithId);
        this.guitaristDTO = this.mapToDTO(testGuitaristWithId);
    }

    @Test
    void createTest() {
        // set up what the mock is doing
        // when running some method, return some value we've predefined up there ^
        when(this.service.create(testGuitarist)).thenReturn(this.guitaristDTO);

        // these are the same thing:
        // JUNIT: assertEquals(expected, actual)
        // MOCKITO: assertThat(expected).isEqualTo(actual);
        // .isEqualTo(what is the method actually returning?)
        // assertThat(what do we want to compare the method to?)
        GuitaristDTO testCreated = this.guitaristDTO;
        assertThat(new ResponseEntity<GuitaristDTO>(testCreated, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testGuitarist));

        // check that the mocked method we ran our assertion on ... actually ran!
        verify(this.service, times(1)).create(this.testGuitarist);
    }

    @Test
    void readTest() {
        when(this.service.read(this.id)).thenReturn(this.guitaristDTO);

        GuitaristDTO testReadOne = this.guitaristDTO;
        assertThat(new ResponseEntity<GuitaristDTO>(testReadOne, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));

        verify(this.service, times(1)).read(this.id);
    }

    // controller <- service
    @Test
    void readAllTest() {
        when(this.service.read())
                .thenReturn(this.guitaristList.stream().map(this::mapToDTO).collect(Collectors.toList()));

        // getBody() = get the list returned from the controller.read() method
        // isEmpty()).isFalse() - check that that list HAS SOMETHING IN IT
        // we can reason that if the list has something in it, it has a guitarist
        assertThat(this.controller.read().getBody().isEmpty()).isFalse();

        verify(this.service, times(1)).read();
    }

    // controller <- service
    @Test
    void updateTest() {
        // we need to feed the mocked service some updated data values
        // that way we can test if our 6-string guitarist changes to a 4-string
        // 'guitarist'
        GuitaristDTO newGuitarist = new GuitaristDTO(null, "Peter Peter Hughes", 4, "Fender American");
        GuitaristDTO newGuitaristWithId = new GuitaristDTO(this.id, newGuitarist.getName(), newGuitarist.getStrings(),
                newGuitarist.getType());

        // feed the mock service the values we made up here ^
        when(this.service.update(newGuitarist, this.id)).thenReturn(newGuitaristWithId);

        assertThat(new ResponseEntity<GuitaristDTO>(newGuitaristWithId, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(this.id, newGuitarist));

        verify(this.service, times(1)).update(newGuitarist, this.id);
    }

    // controller -> service
    @Test
    void deleteTest() {
        this.controller.delete(id); // this will ping the service, which is mocked!

        // if the delete function ran, then it pinged the service successfully
        // since our service is a mocked one, we don't need to test anything in it
        // therefore: check if the controller delete function runs
        // if it does, then the test passes
        verify(this.service, times(1)).delete(id);
    }

}