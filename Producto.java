package Inventario;

import java.util.Scanner;

public class Producto {
    private String nombre;
    private Double precio;
    private Integer stock;

    public Producto(String nombre, Double precio, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
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
        String stockString = consola.nextLine().trim();
        if (!stockString.isEmpty()) {
            try {
                stock = Integer.parseInt(stockString);
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido.");
            }
        }
    }

    public String toCsvString() {
        return "NO_PERECEDERO," + nombre + "," + precio + "," + stock;
    }

    @Override
    public String toString() {
        return String.format("%s - Precio: $%.2f - Stock: %d", nombre, precio, stock);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }


}