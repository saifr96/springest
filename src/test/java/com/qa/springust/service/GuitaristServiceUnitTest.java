package com.qa.springust.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.springust.dto.GuitaristDTO;
import com.qa.springust.persistence.domain.Guitarist;
import com.qa.springust.persistence.repository.GuitaristRepository;

@SpringBootTest
class GuitaristServiceUnitTest {

    @Autowired
    private GuitaristService service;

    @MockBean
    private GuitaristRepository repo;

    @MockBean
    private ModelMapper modelMapper;

    private List<Guitarist> guitaristList;
    private Guitarist testGuitarist;
    private Guitarist testGuitaristWithId;
    private Guitarist emptyGuitarist;
    private GuitaristDTO guitaristDTO;

    final Long id = 1L;
    final String testName = "John Darnielle";
    final Integer testStrings = 6;
    final String testType = "Ibanez Talman";

    private GuitaristDTO mapToDTO(Guitarist guitarist) {
        return this.modelMapper.map(guitarist, GuitaristDTO.class);
    }

    @BeforeEach
    void init() {
        this.guitaristList = new ArrayList<>();
        this.guitaristList.add(testGuitarist);
        this.testGuitarist = new Guitarist("John Darnielle", 6, "Ibanez Talman");
        this.testGuitaristWithId = new Guitarist(testGuitarist.getName(), testGuitarist.getStrings(),
                testGuitarist.getType());
        this.emptyGuitarist = new Guitarist();
        this.testGuitaristWithId.setId(id);
        this.guitaristDTO = new ModelMapper().map(testGuitaristWithId, GuitaristDTO.class);
    }

    @Test
    void createTest() {
        when(this.modelMapper.map(mapToDTO(testGuitarist), Guitarist.class)).thenReturn(testGuitarist);

        when(this.repo.save(testGuitarist)).thenReturn(testGuitaristWithId);

        when(this.modelMapper.map(testGuitaristWithId, GuitaristDTO.class)).thenReturn(guitaristDTO);

        assertThat(this.guitaristDTO).isEqualTo(this.service.create(testGuitarist));

        verify(this.repo, times(1)).save(this.testGuitarist);
    }

    @Test
    void readTest() {
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testGuitaristWithId));

        when(this.modelMapper.map(testGuitaristWithId, GuitaristDTO.class)).thenReturn(guitaristDTO);

        assertThat(this.guitaristDTO).isEqualTo(this.service.read(this.id));

        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    void readAllTest() {
        when(repo.findAll()).thenReturn(this.guitaristList);

        when(this.modelMapper.map(testGuitaristWithId, GuitaristDTO.class)).thenReturn(guitaristDTO);

        assertThat(this.service.read().isEmpty()).isFalse();

        verify(repo, times(1)).findAll();
    }

    @Test
    void updateTest() {
        final long ID = 1L;

        GuitaristDTO newGuitarist = new GuitaristDTO(null, "Peter Peter Hughes", 4, "Fender American");

        Guitarist guitarist = new Guitarist("Peter Peter Hughes", 4, "Fender American");
        guitarist.setId(ID);

        Guitarist updatedGuitarist = new Guitarist(newGuitarist.getName(), newGuitarist.getStrings(),
                newGuitarist.getType());
        updatedGuitarist.setId(ID);

        GuitaristDTO updatedDTO = new GuitaristDTO(ID, updatedGuitarist.getName(), updatedGuitarist.getStrings(),
                updatedGuitarist.getType());

        when(this.repo.findById(this.id)).thenReturn(Optional.of(guitarist));

        when(this.repo.save(updatedGuitarist)).thenReturn(updatedGuitarist);

        when(this.modelMapper.map(updatedGuitarist, GuitaristDTO.class)).thenReturn(updatedDTO);

        assertThat(updatedDTO).isEqualTo(this.service.update(newGuitarist, this.id));

        verify(this.repo, times(1)).findById(1L);

        verify(this.repo, times(1)).save(updatedGuitarist);
    }

    @Test
    void deleteTest() {
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        verify(this.repo, times(1)).deleteById(id);

        verify(this.repo, times(2)).existsById(id);
    }

}