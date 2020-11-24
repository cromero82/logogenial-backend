package com.rc.logogenial.basicadminservice.service;

import com.rc.logogenial.basicadminservice.entity.Usuario;
import com.rc.logogenial.basicadminservice.exception.ResourceFoundException;
import com.rc.logogenial.basicadminservice.exception.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService<T, Tdto> extends IGenericService<T> {
    Usuario getUserLogged();

    Tdto createSecure(Tdto tdto) throws ResourceFoundException, ResourceNotFoundException;

    Usuario findByUsername(String name);
    List<Usuario> findAllStudends ();
    List<Usuario> findAllTeachers ();
}
