package com.qa.springust.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.springust.dto.GuitaristDTO;
import com.qa.springust.persistence.domain.Guitarist;
import com.qa.springust.persistence.repository.GuitaristRepository;

@SpringBootTest
class GuitaristServiceIntegrationTest {

    @Autowired
    private GuitaristService service;

    @Autowired
    private GuitaristRepository repo;

    private Guitarist testGuitarist;
    private Guitarist testGuitaristWithId;

    @Autowired
    private ModelMapper modelMapper;

    private GuitaristDTO mapToDTO(Guitarist guitarist) {
        return this.modelMapper.map(guitarist, GuitaristDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testGuitarist = new Guitarist("John Darnielle", 6, "Ibanez Talman");
        this.testGuitaristWithId = this.repo.save(this.testGuitarist);
    }

    @Test
    void testCreate() {
        assertThat(this.mapToDTO(this.testGuitaristWithId))
            .isEqualTo(this.service.create(testGuitarist));
    }

    @Test
    void testRead() {
        assertThat(this.service.read(this.testGuitaristWithId.getId()))
            .isEqualTo(this.mapToDTO(this.testGuitaristWithId));
    }

    @Test
    void testReadAll() {
        assertThat(this.service.read())
            .isEqualTo(Stream.of(this.mapToDTO(testGuitaristWithId)).collect(Collectors.toList()));
    }

    @Test
    void testUpdate() {
        GuitaristDTO newGuitarist = new GuitaristDTO(null, "Peter Peter Hughes", 4, "Fender American");
        GuitaristDTO updatedGuitarist = new GuitaristDTO(this.testGuitaristWithId.getId(), newGuitarist.getName(), newGuitarist.getStrings(), newGuitarist.getType());

        assertThat(this.service.update(newGuitarist, this.testGuitaristWithId.getId()))
            .isEqualTo(updatedGuitarist);
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.testGuitaristWithId.getId()))
            .isTrue();
    }

}
