package Inventario;

import java.util.Scanner;

public class Producto {
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
        System.out.print("Nuevo nombre (ENTER para mantener): ");
        String nuevoNombre = consola.nextLine().trim();
        if (!nuevoNombre.isEmpty()) nombre = nuevoNombre;

        System.out.print("Nuevo precio (ENTER para mantener): ");
        String precioStr = consola.nextLine().trim();
        if (!precioStr.isEmpty()) {
            try {
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido.");
            }
        }

        System.out.print("Nuevo stock (ENTER para mantener): ");
        String stockStr = consola.nextLine().trim();
        if (!stockStr.isEmpty()) {
            try {
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido.");
            }
        }
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