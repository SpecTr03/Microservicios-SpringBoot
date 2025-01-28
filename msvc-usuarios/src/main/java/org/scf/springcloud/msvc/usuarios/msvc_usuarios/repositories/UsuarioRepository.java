package org.scf.springcloud.msvc.usuarios.msvc_usuarios.repositories;

import org.scf.springcloud.msvc.usuarios.msvc_usuarios.models.entiy.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
