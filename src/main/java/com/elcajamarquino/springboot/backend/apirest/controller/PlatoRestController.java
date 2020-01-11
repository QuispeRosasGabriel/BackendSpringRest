package com.elcajamarquino.springboot.backend.apirest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elcajamarquino.springboot.backend.apirest.models.entity.Plato;
import com.elcajamarquino.springboot.backend.apirest.models.services.iPlatoService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class PlatoRestController {

	@Autowired
	private iPlatoService platoService;

	@GetMapping("/platos")
	public List<Plato> index() {
		return platoService.findAll();
	}

	@GetMapping("/platos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Plato plato = null;
		// Map es la interfaz (creacion), hashmap es la implementacion
		Map<String, Object> response = new HashMap<>();
		try {
			// Creando Objeto plato e igualandolo al plato que sera encontrado por el
			// servicio
			plato = platoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al encontrar el id: ".concat(id.toString()).concat(" en la base de datos"));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			// En el response entity se pasa el tipo de dato que es response, en este caso
			// es un tipo map
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (plato == null) {
			response.put("mensaje",
					"El cliente con id: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		// Al response entity se le coloca el tipo de objeto que devolvera y los
		// parametros que contiene
		return new ResponseEntity<Plato>(plato, HttpStatus.OK);
	}

	@PostMapping("/platos")
	@ResponseStatus(HttpStatus.CREATED)
	// Interceptor de Spring
	// @Valid inyeccion para validacion de lo que viene del angular
	// BindingResult herramienta para empezar a buscar errores
	public ResponseEntity<?> create(@Valid @RequestBody Plato plato, BindingResult result) {
		Plato nuevoPlato = null;
		Map<String, Object> res = new HashMap<>();
		if (result.hasErrors()) {
			/*
			 * List<String> errors = new ArrayList<>(); for (FieldError err :
			 * result.getFieldErrors()) { errors.add("El campo " + err.getField() +
			 * err.getDefaultMessage()); }
			 */
			List<String> err = result.getFieldErrors().stream()
					.map(r -> "El campo " + r.getField() + r.getDefaultMessage()).collect(Collectors.toList());
			res.put("errors", err);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.BAD_REQUEST);

		}
		try {
			nuevoPlato = platoService.save(plato);

		} catch (DataAccessException e) {
			res.put("error", "Error en la base de datos ".concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		res.put("mensaje", "El Plato fue añadido con exito");
		res.put("plato", nuevoPlato);
		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);
	}

	@PutMapping("/platos/{id}")
	public ResponseEntity<?> update(@RequestBody Plato plato, @PathVariable Long id) {
		Plato platoActual = platoService.findById(id);
		Plato platoUpdated = null;
		Map<String, Object> resp = new HashMap<>();
		if (platoActual == null) {
			resp.put("error", "no introdujo dato a actualizar");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
		}
		try {
			platoActual.setCantidad(plato.getCantidad());
			platoActual.setNombre(plato.getNombre());
			platoActual.setPrecio(plato.getPrecio());
			platoUpdated = platoService.save(platoActual);
		} catch (Exception e) {
			resp.put("error", "error en la base de datos".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp.put("mensaje", "cliente ha sido actualizado con exito");
		resp.put("plato", platoUpdated);
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/platos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			platoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Mensaje", "El cliente ha sido eliminado!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
