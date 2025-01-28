package org.scf.springcloud.mvsc.cursos.msvc_cursos.repositories;

import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {
}
