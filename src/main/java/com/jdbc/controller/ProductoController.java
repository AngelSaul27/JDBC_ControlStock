package com.jdbc.controller;

import com.jdbc.dao.CategoriaDAO;
import com.jdbc.factory.ConnectionFactoy;
import com.jdbc.modelo.Categoria;
import com.jdbc.modelo.Producto;
import com.jdbc.dao.ProductoDAO;

import java.util.List;

public class ProductoController {
	private final ProductoDAO productoDAO;

    public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConnectionFactoy().recuperaConexion());
    }

    public int modificar(Producto producto) {
		return productoDAO.actualizar(producto);
	}

	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);
	}

	public List<Producto> listar() {
		return productoDAO.listar();
	}

	public List<Producto> listar(Categoria categoria) {
		return productoDAO.listar(categoria.getId());
	}

    public void guardar(Producto producto, Integer categoriaId){
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}
}
