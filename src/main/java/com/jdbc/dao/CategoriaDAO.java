package com.jdbc.dao;

import com.jdbc.modelo.Categoria;
import com.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private final Connection con;

    public CategoriaDAO(Connection con){
        this.con = con;
    }

    public List<Categoria> listado(){
        List<Categoria> resultado = new ArrayList<>();

        try{
            final PreparedStatement statement = con.prepareStatement("SELECT * FROM Categoria");
            final ResultSet resultSet = statement.executeQuery();

            try(statement){
                try(resultSet){
                    while(resultSet.next()){
                        Categoria categoria = new Categoria(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE")
                        );
                        resultado.add(categoria);
                    }
                    return resultado;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();

        try{
            final PreparedStatement statement = con.prepareStatement("SELECT C.id, C.nombre, P.id, P.nombre, P.cantidad FROM Categoria C INNER JOIN producto P ON C.ID = P.CATEGORIA_ID");
            final ResultSet resultSet = statement.executeQuery();

            try(statement){
                try(resultSet){
                    while(resultSet.next()){
                        Integer categoriaID = resultSet.getInt("C.ID");
                        String categoriaNombre = resultSet.getString("C.NOMBRE");

                        var categoria = resultado
                                .stream()
                                .filter(cat-> cat.getId().equals(categoriaID))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(
                                            categoriaID, categoriaNombre
                                    );

                                    resultado.add(cat);

                                    return cat;
                                });

                        Producto producto = new Producto(
                                resultSet.getInt("P.ID"),
                                resultSet.getString("P.NOMBRE"),
                                resultSet.getInt("P.CANTIDAD")
                        );

                        categoria.agregarProduucto(producto);

                    }
                    return resultado;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
