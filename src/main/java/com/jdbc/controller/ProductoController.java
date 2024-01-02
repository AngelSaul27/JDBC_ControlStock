package com.jdbc.controller;

import com.jdbc.factory.ConnectionFactoy;
import com.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	private final ConnectionFactoy factory = new ConnectionFactoy();

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		final Connection con = factory.recuperaConexion();
		try(con){
			final PreparedStatement preparedStatement = con.prepareStatement("UPDATE producto SET nombre = ?, descripcion = ?, cantidad = ? where id = ?");

			try(preparedStatement){
				preparedStatement.setString(1,nombre);
				preparedStatement.setString(2, descripcion);
				preparedStatement.setInt(3, cantidad);
				preparedStatement.setInt(4, id);
				preparedStatement.execute();

                return preparedStatement.getUpdateCount();
			}
		}
	}


	public int eliminar(Integer id) throws SQLException {
		Connection con = factory.recuperaConexion();

		try(con){
			final PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM producto WHERE id = ?");

			try(preparedStatement){
				preparedStatement.setInt(1, id);
				preparedStatement.execute();

                return preparedStatement.getUpdateCount();
			}
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		final Connection con = factory.recuperaConexion();

		try(con){
			final PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM producto");

			try(preparedStatement){
				preparedStatement.execute();

				ResultSet resultSet = preparedStatement.getResultSet();

				List<Map<String, String>> resultado = new ArrayList<>();

				while(resultSet.next()){
					Map<String, String> fila = new HashMap<>();
					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("NOMBRE", resultSet.getString("NOMBRE"));
					fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
					fila.put("CANTIDAD", String.valueOf(resultSet.getInt("Cantidad")));
					resultado.add(fila);
				}

				return resultado;
			}
		}
	}

    public void guardar(Producto producto) throws SQLException {
		final Connection con = factory.recuperaConexion();

		try(con){
			con.setAutoCommit(false);

			final PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO producto (NOMBRE, DESCRIPCION, CANTIDAD) VALUE (?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			try(preparedStatement){
				ejecutarGuardado(preparedStatement, producto);
				con.commit();
			}catch (Exception e){
				con.rollback();
			}
		}
	}

	private static void ejecutarGuardado(PreparedStatement preparedStatement, Producto producto) throws SQLException {
		preparedStatement.setString(1, producto.getNombre());
		preparedStatement.setString(2, producto.getDescripcion());
		preparedStatement.setInt(3, producto.getCantidad());
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
