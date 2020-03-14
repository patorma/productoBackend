package com.patricio.contreras.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NonNull
@Entity
@Table(name="productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name="nombre_producto",unique = true)
	private String nombreProducto;

	@NotNull
	private int precio;
	
	@Column(name = "create_at")
	private LocalDate createAt;
	
	@PrePersist
	public void prePersist(){
		createAt= LocalDate.now();
	}
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
