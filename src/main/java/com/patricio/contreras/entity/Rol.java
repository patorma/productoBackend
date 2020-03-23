package com.patricio.contreras.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.patricio.contreras.enums.RolNombre;


import lombok.Data;
import lombok.NonNull;

@Data

@NonNull
@Table(name = "roles")
@Entity
public class Rol implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "rol_nombre")
	@NotNull
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;
	
	@ManyToMany(mappedBy = "roles")
	Set<Usuario> usuarios;
	
	public Rol(@NotNull RolNombre rolNombre) {
		super();
		this.rolNombre = rolNombre;
	}


	public Rol() {
		
	}


	private static final long serialVersionUID = 1L;

}
