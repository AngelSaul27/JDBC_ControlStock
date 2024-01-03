package com.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

    private Integer id;
    protected String nombre;

    public void agregarProduucto(Producto producto) {
        if(this.productos == null){
            this.productos = new ArrayList<>();
        }

        this.productos.add(producto);
    }

    protected List<Producto> productos;

    public Categoria(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public Integer getId() {
        return this.id;
    }

    public List<Producto> getProductos() {
        return this.productos;
    }
}
