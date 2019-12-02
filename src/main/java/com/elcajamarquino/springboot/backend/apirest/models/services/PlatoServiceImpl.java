package com.elcajamarquino.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elcajamarquino.springboot.backend.apirest.models.dao.iPlatoDao;
import com.elcajamarquino.springboot.backend.apirest.models.entity.Plato;

@Service
public class PlatoServiceImpl implements iPlatoService {

	@Autowired
	private iPlatoDao platoDao; 
	
	@Override
	@Transactional(readOnly = true)
	public List<Plato> findAll() {
		return (List<Plato>) platoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Plato findById(Long id) {
		// TODO Auto-generated method stub
		return platoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public Plato save(Plato plato) {
		// TODO Auto-generated method stub
		return platoDao.save(plato);
	}

	@Override
	@Transactional(readOnly = true)
	public void delete(Long id) {
		// TODO Auto-generated method stub
		platoDao.deleteById(id);
		
	}

}
