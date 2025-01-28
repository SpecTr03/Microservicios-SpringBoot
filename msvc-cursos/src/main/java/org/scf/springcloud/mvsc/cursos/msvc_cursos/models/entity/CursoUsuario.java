package org.scf.springcloud.mvsc.cursos.msvc_cursos.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long usuarioId;

    // Validacion de la relacion entre Curso y Usuario
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursoUsuario)) {
            return false;
        }
        CursoUsuario a = (CursoUsuario) obj;
        return this.usuarioId != null && this.usuarioId.equals(a.usuarioId);
    }
}
