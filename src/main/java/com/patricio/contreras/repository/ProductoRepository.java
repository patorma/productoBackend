package com.patricio.contreras.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.patricio.contreras.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	Producto findByNombreProducto(String np);
    boolean existsByNombreProducto(String np);

}
