package org.scf.springcloud.mvsc.cursos.msvc_cursos.services;

import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.Usuario;
import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Optional<Curso> porIdConUsuarios(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);

    void deleteCursoUsuarioByUsuarioId(Long id);


    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> desasignarUsuario(Usuario usuario, Long cursoId);
}
