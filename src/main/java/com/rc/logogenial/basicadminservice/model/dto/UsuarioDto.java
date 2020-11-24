package com.rc.logogenial.basicadminservice.model.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UsuarioDto {
    private int id;
    private String username;
    private String password;
    private Integer estado;
    private String nombre;
    private String apellido;
    private String avatar;
    private String email;
    private List<String> roles= new ArrayList<>();
    private Boolean activo;
}
