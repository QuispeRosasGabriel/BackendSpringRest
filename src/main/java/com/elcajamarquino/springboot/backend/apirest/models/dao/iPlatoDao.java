package com.elcajamarquino.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.elcajamarquino.springboot.backend.apirest.models.entity.Plato;

public interface iPlatoDao extends CrudRepository<Plato, Long> {

}
