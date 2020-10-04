package com.qa.springust.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springust.dto.BandDTO;
import com.qa.springust.exception.BandNotFoundException;
import com.qa.springust.persistence.domain.Band;
import com.qa.springust.persistence.repository.BandRepository;
import com.qa.springust.utils.SpringustBeanUtils;


@Service
public class BandService {

    private BandRepository repo;

    private ModelMapper mapper;

    @Autowired
    public BandService(BandRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    private BandDTO mapToDTO(Band band) {
        return this.mapper.map(band, BandDTO.class);
    }

    private Band mapFromDTO(BandDTO bandDTO) {
        return this.mapper.map(bandDTO, Band.class);
    }

//    public BandDTO create(BandDTO bandDTO) {
//        Band toSave = this.mapFromDTO(bandDTO);
//        Band saved = this.repo.save(toSave);
//        return this.mapToDTO(saved);
//    }

    public BandDTO create(Band band) {
        Band created = this.repo.save(band);
        BandDTO mapped = this.mapToDTO(created);
        return mapped;
    }

    public List<BandDTO> read() {
        List<Band> found = this.repo.findAll();
        List<BandDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());
        return streamed;
    }

    public BandDTO read(Long id) {
        Band found = this.repo.findById(id).orElseThrow(BandNotFoundException::new);
        return this.mapToDTO(found);
    }

 // Update
 //	public BandDTO update(Band band, Long id) {
 //		Band toUpdate = this.repo.findById(id).orElseThrow(BandNotFoundException::new);
 //		toUpdate.setName(band.getName());
 //		toUpdate.setGuitarists(band.getGuitarists());
 //		return this.mapToDTO(this.repo.save(toUpdate));
 //   }
    
    public BandDTO update(Band newBand, Long id) {
        Band toUpdate = this.repo.findById(id).orElseThrow(BandNotFoundException::new);
        SpringustBeanUtils.mergeNotNull(newBand, toUpdate);
        return this.mapToDTO(this.repo.save(toUpdate));
    
    }
    

    public boolean delete(Long id) {
        if (!this.repo.existsById(id)) {
            throw new BandNotFoundException();
        }
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

	}


