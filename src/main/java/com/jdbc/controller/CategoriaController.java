package com.jdbc.controller;

import com.jdbc.dao.CategoriaDAO;
import com.jdbc.factory.ConnectionFactoy;
import com.jdbc.modelo.Categoria;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private final CategoriaDAO categoriaDAO;

    public CategoriaController(){
        this.categoriaDAO = new CategoriaDAO(new ConnectionFactoy().recuperaConexion());
    }

	public List<Categoria> listar() {
		return categoriaDAO.listado();
	}

    public List<Categoria> cargaReporte() {
        return this.categoriaDAO.listarConProductos();
    }

}
