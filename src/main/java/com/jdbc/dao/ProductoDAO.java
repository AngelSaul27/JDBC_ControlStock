package com.jdbc.dao;

import com.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    final private Connection con;

    public ProductoDAO(Connection con){
        this.con = con;
    }

    public List<Producto> listar(){
        List<Producto> resultado = new ArrayList<>();

        try{
            final PreparedStatement statement = con.prepareStatement("SELECT * FROM producto");

            try(statement){
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try(resultSet){
                    while(resultSet.next()){
                        Producto producto = new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("Cantidad")
                        );
                        resultado.add(producto);
                    }
                    return resultado;
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(Integer categoriaId) {
        List<Producto> resultado = new ArrayList<>();

        try{
            final PreparedStatement statement = con.prepareStatement("SELECT * FROM producto WHERE categoria_id = ?");

            try(statement){
                statement.setInt(1, categoriaId);
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try(resultSet){
                    while(resultSet.next()){
                        Producto producto = new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("Cantidad")
                        );
                        resultado.add(producto);
                    }
                    return resultado;
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Integer actualizar(Producto producto){
        try{
            final PreparedStatement preparedStatement = con.prepareStatement("UPDATE producto SET nombre = ?, descripcion = ?, cantidad = ? where id = ?");

            try(preparedStatement){
                preparedStatement.setString(1, producto.getNombre());
                preparedStatement.setString(2, producto.getDescripcion());
                preparedStatement.setInt(3, producto.getCantidad());
                preparedStatement.setInt(4, producto.getId());
                preparedStatement.execute();

                return preparedStatement.getUpdateCount();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public Integer eliminar(Integer id){
        try{
            final PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM producto WHERE id = ?");

            try(preparedStatement){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();

                return preparedStatement.getUpdateCount();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void guardar(Producto producto){
        try{
            con.setAutoCommit(false);

            final PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO producto (NOMBRE, DESCRIPCION, CANTIDAD, CATEGORIA_ID) VALUE (?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            try(preparedStatement){
                ejecutarRegistro(preparedStatement, producto);
                con.commit();
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void ejecutarRegistro(PreparedStatement preparedStatement, Producto producto) throws SQLException {
        preparedStatement.setString(1, producto.getNombre());
        preparedStatement.setString(2, producto.getDescripcion());
        preparedStatement.setInt(3, producto.getCantidad());
        preparedStatement.setInt(4, producto.getCategoriaID());
        preparedStatement.execute();

        final ResultSet resultSet = preparedStatement.getGeneratedKeys();

        try(resultSet){
            while (resultSet.next()){
                producto.setId(resultSet.getInt(1));
                System.out.println("Fue insertado: "+producto);
            }
        }
    }
}
