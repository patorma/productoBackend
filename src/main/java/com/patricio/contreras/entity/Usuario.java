package com.patricio.contreras.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;

import lombok.NonNull;

@Entity
@Data
@Table(name = "usuarios")
@NonNull
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String apellido;
	
	@NotNull
	@Column(name = "nombre_usuario",unique = true)
	private String nombreUsuario;
	
	@Column(unique = true )
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	
	@NotNull

	private Set<Rol> roles  =new HashSet<>();
	
	

}
