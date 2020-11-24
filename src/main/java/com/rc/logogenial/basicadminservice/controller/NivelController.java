package com.rc.logogenial.basicadminservice.controller;

import com.rc.logogenial.basicadminservice.entity.Nivel;
import com.rc.logogenial.basicadminservice.exception.ResourceFoundException;
import com.rc.logogenial.basicadminservice.exception.ResourceNotFoundException;
import com.rc.logogenial.basicadminservice.model.shared.ResultSearchData;
import com.rc.logogenial.basicadminservice.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/v1/nivel-api" })
@CrossOrigin(origins= {"*"})
public class NivelController {

    @Autowired
    private IGenericService<Nivel> service;

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('hasAccess','ADMINISTRADOR','TUTOR')")
    public ResponseEntity<Nivel> create(@RequestBody Nivel Nivel) throws ResourceFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(service.create(Nivel), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Nivel> findOne(@RequestParam int id) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Nivel> update(@RequestBody Nivel Nivel) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.update(Nivel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('TUTOR','ESTUDIANTE')")
    public ResponseEntity<Iterable<Nivel>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultSearchData<Nivel>> search(@RequestParam(name = "page", defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "sortBy") String sortBy
            , @RequestParam(name = "sortOrder") String sortOrder ) {
        ResultSearchData<Nivel> datos = service.findAllSearch(page, size,sortBy, sortOrder);
        return new ResponseEntity<ResultSearchData<Nivel>>(datos, new HttpHeaders(), HttpStatus.OK);
    }
}
