package Inventario;

import java.util.Scanner;

class Producto {
    private String nombre;
    private Double precio;
    private Integer stock;

    public Producto(String nombre, Double precio, Integer stock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto() {
    }

    public void editar(Scanner consola) {
        nombre = leerCampo(consola, "nombre", nombre);
        precio = Double.parseDouble(leerCampo(consola, "precio", String.valueOf(precio)));
        stock = Integer.parseInt(leerCampo(consola, "stock", String.valueOf(stock)));
    }

    private String leerCampo(Scanner consola, String campo, String actual) {
        System.out.print("Nuevo " + campo + " (" + actual + "): ");
        String entrada = consola.nextLine();
        return entrada.isEmpty() ? actual : entrada;
    }

    @Override
    public String toString() {
        return String.format("%s - Precio: $%.2f - Stock: %d", nombre, precio, stock);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.stock = stock;
    }
}