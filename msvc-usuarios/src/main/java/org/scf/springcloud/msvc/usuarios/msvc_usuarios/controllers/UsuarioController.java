package org.scf.springcloud.msvc.usuarios.msvc_usuarios.controllers;

import jakarta.validation.Valid;
import org.scf.springcloud.msvc.usuarios.msvc_usuarios.models.entiy.Usuario;
import org.scf.springcloud.msvc.usuarios.msvc_usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.porId(id);
        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }
        if (!usuario.getEmail().isEmpty() && usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "El email ya existe"));
        }
        Usuario usuarioDb = usuarioService.guardar(usuario);
        return ResponseEntity.status(201).body(usuarioDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return validar(result);
        }
        Optional<Usuario> usuarioDb = usuarioService.porId(id);
        if(usuarioDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!usuario.getEmail().isEmpty() &&
                !usuario.getEmail().equalsIgnoreCase(usuarioDb.get().getEmail()) &&
                usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "El email ya existe"));
        }
        usuarioDb.get().setNombre(usuario.getNombre());
        usuarioDb.get().setEmail(usuario.getEmail());
        usuarioDb.get().setPassword(usuario.getPassword());
        return ResponseEntity.status(201).body(usuarioService.guardar(usuarioDb.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.porId(id);
        if(usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, Object>> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
