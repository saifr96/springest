package com.qa.springust.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springust.dto.GuitaristDTO;
import com.qa.springust.persistence.domain.Guitarist;
import com.qa.springust.service.GuitaristService;

@RestController
@RequestMapping("/guitarist")
public class GuitaristController {

    private GuitaristService service;

    @Autowired
    public GuitaristController(GuitaristService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<GuitaristDTO> create(@RequestBody Guitarist guitarist) {
        GuitaristDTO created = this.service.create(guitarist);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<GuitaristDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.read(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<GuitaristDTO>> read() {
        return ResponseEntity.ok(this.service.read());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GuitaristDTO> update(@PathVariable Long id, @RequestBody GuitaristDTO guitaristDTO) {
        return new ResponseEntity<>(this.service.update(guitaristDTO, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GuitaristDTO> delete(@PathVariable Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) // 204
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

}
