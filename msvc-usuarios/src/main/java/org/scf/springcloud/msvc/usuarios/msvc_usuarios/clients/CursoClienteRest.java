package org.scf.springcloud.msvc.usuarios.msvc_usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002")
public interface CursoClienteRest {
    @DeleteMapping("/eliminar-usuario/{id}")
    void eliminarUsuario(@PathVariable Long id);
}
