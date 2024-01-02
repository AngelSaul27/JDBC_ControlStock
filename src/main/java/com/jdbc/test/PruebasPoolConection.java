package com.jdbc.test;

import com.jdbc.factory.ConnectionFactoy;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebasPoolConection {
    public static void main(String[] args) throws SQLException {
        ConnectionFactoy connectionFactoy = new ConnectionFactoy();

        for (int i = 0; i < 20; i++){
            Connection con = connectionFactoy.recuperaConexion();
            System.out.println("Abriendo la conexión numero: " + (i+1));
            /*
            if(i >= 9){
                con.close();
            }
            * */
        }
    }
}
