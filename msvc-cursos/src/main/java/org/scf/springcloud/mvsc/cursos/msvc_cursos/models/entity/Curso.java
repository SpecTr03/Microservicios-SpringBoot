package org.scf.springcloud.mvsc.cursos.msvc_cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.Usuario;

import java.util.List;

@Entity
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //Creacion de la relacion entre Curso y Usuario con una llave foranea
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuario;

    @Transient
    private List<Usuario> usuarios;

    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        this.cursoUsuario.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        this.cursoUsuario.remove(cursoUsuario);
    }
}
