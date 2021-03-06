package com.elcajamarquino.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "platos_carta")
public class Plato implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty(message="no puede ser vacio")
	@Size(min = 4, max = 20)
	@Column(nullable = false, unique = true)
	private String nombre;

	@NotEmpty(message="no puede ser vacio")
	private long cantidad;

	@NotEmpty(message="no puede ser vacio")
	private long precio;

	public long getPrecio() {
		return precio;
	}

	public void setPrecio(long precio) {
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
