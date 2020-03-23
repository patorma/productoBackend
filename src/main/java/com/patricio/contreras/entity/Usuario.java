package com.patricio.contreras.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;

import lombok.NonNull;

@Entity
@Data
@Table(name = "usuarios")
@NonNull
public class Usuario implements Serializable {
	


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
	@ManyToMany
	@JoinTable(name = "usuario_rol",joinColumns = @JoinColumn(name="usuario_id"),inverseJoinColumns = @JoinColumn(name="rol_id"))
	private Set<Rol> roles;
	
	public Usuario() {
		
	}
	

	public Usuario(@NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario,
			@NotNull String email, @NotNull String password) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
