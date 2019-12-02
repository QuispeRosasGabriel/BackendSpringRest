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
	public Plato create(@RequestBody Plato plato) {
		return platoService.save(plato);
	}

	@PutMapping("/platos/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Plato update(@RequestBody Plato plato, @PathVariable Long id) {
		Plato platoActual = platoService.findById(id);
		platoActual.setNombre(plato.getNombre());
		platoActual.setCantidad(plato.getCantidad());
		platoActual.setPrecio(plato.getPrecio());

		return platoService.save(platoActual);
	}

	@DeleteMapping("/platos/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		platoService.delete(id);
	}
}
