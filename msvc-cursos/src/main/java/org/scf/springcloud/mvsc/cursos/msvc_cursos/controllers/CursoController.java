package org.scf.springcloud.mvsc.cursos.msvc_cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.Usuario;
import org.scf.springcloud.mvsc.cursos.msvc_cursos.models.entity.Curso;
import org.scf.springcloud.mvsc.cursos.msvc_cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.porId(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }
        Curso cursoGuardado = cursoService.guardar(curso);
        return ResponseEntity.status(201).body(cursoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return validar(result);
        }
        Optional<Curso> cursoOptional = cursoService.porId(id);
        if (cursoOptional.isPresent()) {
            Curso cursoDb = cursoOptional.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(201).body(cursoService.guardar(cursoDb));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.porId(id);
        if (curso.isPresent()) {
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = cursoService.asignarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por id o error en la comunicacion: " + e.getMessage()));
        }
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.status(201).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = cursoService.crearUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario o error en la comunicacion: " + e.getMessage()));
        }
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.status(201).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/desasignar-usuario/{cursoId}")
    public ResponseEntity<?> desasignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = cursoService.desasignarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por id o error en la comunicacion: " + e.getMessage()));
        }
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.status(200).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, Object>> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
