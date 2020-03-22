package com.patricio.contreras.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patricio.contreras.entity.Producto;
import com.patricio.contreras.service.ProductoService;

import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoRestController {

	@Autowired
	ProductoService productoService;
	
	@GetMapping("/lista")
	public List<Producto> getLista(){
		return productoService.obtenerProductos();
	}
	
	
	@GetMapping("detalle/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		
		Producto producto = null;
		Map<String, Object> response  = new HashMap<>();
		try {
			 productoService.existePorId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		
		if(!productoService.existePorId(id)){
			response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		producto = productoService.obtenerPorId(id); 
		return new ResponseEntity<Producto>(producto,HttpStatus.OK);
	}
	
	@PostMapping("/producto")
	@NonNull
	public ResponseEntity<?> create(@RequestBody Producto producto){
		//Es el nuevo producto creado	
		Producto productoNew = null;
		Map<String, Object> response  = new HashMap<>();
		
		try {
			if(producto.getNombreProducto().isBlank()) {
				response.put("mensaje", "El nombre del producto es obligatorio!!!");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			}
			
			if(productoService.existePorNombre(producto.getNombreProducto())) {
				response.put("mensaje", "Ese nombre de producto ya se encuentra registrado!!");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			}
			if( (Integer)producto.getPrecio() == null || producto.getPrecio() == 0) {
				response.put("mensaje", "El Precio del producto es obligatorio!!!");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			}
			
			
			productoNew = productoService.guardar(producto);
			 
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//se podria pasar un map con un mensaje y con el producto creado
			response.put("mensaje", "El producto ha sido creado con éxito! ");
			response.put("producto", productoNew);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
				
	}
	
	@PutMapping("/actualiza/{id}")
	public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Long id){
		//obtenemos el producto la bd por Id
		  Producto productoActual = productoService.obtenerPorId(id);
		  
		  // producto ya actualizado
		  Producto productoUpdated = null;
		  
		  Map<String, Object> response = new HashMap<>();
		  
		  if(productoActual == null) {
			  response.put("mensaje", "Error: no se pudo editar, el producto ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		  }
		  
		  if(productoService.existePorNombre(producto.getNombreProducto())&& productoService.obtenerPorNombre(producto.getNombreProducto()).getId() !=id) {
				response.put("mensaje", "Ese nombre de producto ya existe!!!");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		  }
		try {
			//modificamos los datos del producto actual con los datos del producto que te envien
			productoActual.setNombreProducto(producto.getNombreProducto());
			productoActual.setPrecio(producto.getPrecio());
			productoActual.setCreateAt(producto.getCreateAt());
			
			productoUpdated = productoService.guardar(productoActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el producto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido actualizado con éxito!");
		response.put("producto", productoUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
	}
	
	@DeleteMapping("producto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		//Map para guardar el contenido que enviaremos en el ResponseEntity con mensajes
		Map<String, Object> response = new HashMap<>();
		try {
			//Automaticamente se valida que el id del producto existe en la BD
			productoService.borrar(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto se ha  eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
	}
}
