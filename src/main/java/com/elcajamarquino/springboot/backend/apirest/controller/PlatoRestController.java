package com.elcajamarquino.springboot.backend.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin(origins = { "http://localhost:4200" })
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
	// la interrogacion indica un tipo de dato generico
	public ResponseEntity<?> show(@PathVariable Long id) {
		Plato plato = null;
		Map<String, Object> response = new HashMap<>();
		try {
			plato = platoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (plato == null) {
			response.put("mensaje",
					"El plato con el id ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Plato>(plato, HttpStatus.OK);
	}

	@PostMapping("/platos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Plato plato) {
		Plato nuevoPlato = null;
		Map<String, Object> response = new HashMap<>();
		try {
			nuevoPlato = platoService.save(plato);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al ingresar información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido creado con éxito");
		response.put("plato", nuevoPlato);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/platos/{id}")
	public ResponseEntity<?> update(@RequestBody Plato plato, @PathVariable Long id) {
		Plato platoActual = platoService.findById(id);
		Plato updatedPlato = null;
		Map<String, Object> response = new HashMap<>();
		if (platoActual == null) {
			response.put("mensaje",
					"Error: no se pudo editar ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			platoActual.setNombre(plato.getNombre());
			platoActual.setCantidad(plato.getCantidad());
			platoActual.setPrecio(plato.getPrecio());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		response.put("plato", updatedPlato);
		updatedPlato = platoService.save(platoActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/platos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
		platoService.delete(id);}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Mensaje", "El cliente ha sido eliminado!");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
