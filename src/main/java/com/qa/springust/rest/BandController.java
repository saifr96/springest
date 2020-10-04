package com.qa.springust.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springust.dto.BandDTO;
import com.qa.springust.persistence.domain.Band;
import com.qa.springust.service.BandService;
@CrossOrigin
@RestController
@RequestMapping("/band")
public class BandController {

    private BandService service;

    @Autowired
    public BandController(BandService service) {
        super();
        this.service = service;
    }

//    @PostMapping("/create")
//    public ResponseEntity<BandDTO> create(@RequestBody BandDTO bandDTO) {
//        return new ResponseEntity<>(this.service.create(bandDTO), HttpStatus.CREATED);
//    }

    @PostMapping("/create")
    public ResponseEntity<BandDTO> create(@RequestBody Band band) {
        BandDTO created = this.service.create(band);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<BandDTO>> read() {
        return ResponseEntity.ok(this.service.read());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BandDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.read(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BandDTO> update(@PathVariable Long id, @RequestBody Band bandDTO) {
        return new ResponseEntity<>(this.service.update(bandDTO, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BandDTO> delete(@PathVariable Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
