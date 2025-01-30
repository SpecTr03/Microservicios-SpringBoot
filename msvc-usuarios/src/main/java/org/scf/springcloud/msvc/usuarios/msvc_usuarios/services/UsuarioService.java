package org.scf.springcloud.msvc.usuarios.msvc_usuarios.services;

import org.scf.springcloud.msvc.usuarios.msvc_usuarios.models.entiy.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    List<Usuario> listarPorIds(Iterable<Long> ids);

    Optional<Usuario> buscarPorEmail(String email);
}
