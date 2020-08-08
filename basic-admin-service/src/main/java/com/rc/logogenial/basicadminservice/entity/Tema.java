package com.rc.logogenial.basicadminservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name="tema", uniqueConstraints = @UniqueConstraint(name = "tema_uk", columnNames = "nombre"))
@SQLDelete(sql = "UPDATE tema SET eliminado = current_date WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "eliminado is  null")
public class Tema    {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String nombre;

    /** The activo. */
    @Column(name = "activo", columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    @Builder.Default
    public Boolean activo = true;

    /** The eliminado. */
    @Column(name = "eliminado")
    public Date eliminado;


}