package com.jdbc.test;

import com.jdbc.factory.ConnectionFactoy;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        Connection con = new ConnectionFactoy().recuperaConexion();
        System.out.println("Conexión Cerrada");
        con.close();
    }
}
