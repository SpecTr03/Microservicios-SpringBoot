package org.scf.springcloud.mvsc.cursos.msvc_cursos.clients;

import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-curso")
    List <Usuario> obtenerUsuariosCurso(@RequestParam Iterable <Long> ids);
}
