package com.elcajamarquino.springboot.backend.apirest.models.services;

import java.util.List;

import com.elcajamarquino.springboot.backend.apirest.models.entity.Plato;

public interface iPlatoService {
	public List <Plato> findAll();
	
	public Plato findById(Long id);
	
	public Plato save(Plato plato);
	
	public void delete(Long id);
	
		

}
