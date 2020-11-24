package com.rc.logogenial.basicadminservice.controller;

import com.rc.logogenial.basicadminservice.entity.Role;
import com.rc.logogenial.basicadminservice.entity.Usuario;
import com.rc.logogenial.basicadminservice.exception.ResourceFoundException;
import com.rc.logogenial.basicadminservice.exception.ResourceNotFoundException;
import com.rc.logogenial.basicadminservice.exception.UnauthorizedRequestException;
import com.rc.logogenial.basicadminservice.model.dto.UsuarioDto;
import com.rc.logogenial.basicadminservice.model.shared.ResultSearchData;
import com.rc.logogenial.basicadminservice.service.IGenericService;
import com.rc.logogenial.basicadminservice.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({ "v1/usuario-api" })
@CrossOrigin
public class UsuarioController {

    @Autowired
    private IUsuarioService<Usuario, UsuarioDto> usuarioService;

    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) throws ResourceFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.create(usuario), HttpStatus.OK);
    }

    @PostMapping("/createSecure")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDto> createSecure(@RequestBody UsuarioDto usuario) throws ResourceFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.createSecure(usuario), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Usuario> findOne(@RequestParam int id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Usuario> update(@RequestBody Usuario Usuario) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.update(Usuario), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Object> delete(@RequestParam int id) throws ResourceNotFoundException {
        usuarioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<Usuario>> findAll() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findAllStudends")
    public ResponseEntity<Iterable<Usuario>> findAllStudends() {
        return new ResponseEntity<>(usuarioService.findAllStudends(), HttpStatus.OK);
    }

    @GetMapping("/findAllTeachers")
    public ResponseEntity<Iterable<Usuario>> findAllTeachers() {
        return new ResponseEntity<>(usuarioService.findAllTeachers(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResultSearchData<Usuario>> search(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "sortBy") String sortBy
            , @RequestParam(name = "sortOrder") String sortOrder ) {
        ResultSearchData<Usuario> datos = usuarioService.findAllSearch(page, size,sortBy, sortOrder);
        return new ResponseEntity<ResultSearchData<Usuario>>(datos, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/getRoles")
    public ResponseEntity<List<String>> getRoles() throws UnauthorizedRequestException {
        Usuario usuarioLogueado = usuarioService.getUserLogged();
        List<String> roles =  usuarioLogueado.getRoles().stream().map(Role::getNombre).collect(Collectors.toList());
        return  ResponseEntity.ok(roles);
    }
}
