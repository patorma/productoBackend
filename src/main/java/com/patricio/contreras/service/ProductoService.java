package com.patricio.contreras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import com.patricio.contreras.entity.Producto;
import com.patricio.contreras.repository.ProductoRepository;

@Service
public class ProductoService {
	
  @Autowired	
  ProductoRepository productoRepository;
  
  @Transactional(readOnly = true)
  public List<Producto> obtenerProductos(){
	  List<Producto> lista = productoRepository.findAll();
	  return lista;
	  
  }
  @Transactional(readOnly = true)
  public Producto obtenerPorId(Long id){
      return  productoRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public Producto obtenerPorNombre(String np){
      return productoRepository.findByNombreProducto(np);
  }

  @Transactional
  public Producto guardar(Producto producto){
      return productoRepository.save(producto);
  }

  @Transactional
  public void borrar(Long id){
      productoRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public boolean existePorNombre(String np){
      return productoRepository.existsByNombreProducto(np);
  }

  @Transactional(readOnly = true)
  public boolean existePorId(Long id){
      return productoRepository.existsById(id);
  }
  
}
