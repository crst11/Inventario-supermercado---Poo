package Inventario;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Scanner;

class Perecedero extends Producto {

    private LocalDate fechaVencimiento;

    public Perecedero(String nombre, double precio, int stock, LocalDate fechaVencimiento) {
        super(nombre, precio, stock);
        this.fechaVencimiento = fechaVencimiento;
    }

    public Perecedero(
            String nombre, Double precio, Integer stock) {
        super(nombre, precio, stock);
    }


    @Override
    public String toString() {
        return
                super.toString() + " | Vence: " + fechaVencimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public void editar(Scanner consola) {
        super.editar(consola);
        System.out.print("Nueva fecha de vencimiento (dd/MM/yyyy) o ENTER para mantener: ");
        String entrada = consola.nextLine().trim();
        if (!entrada.isEmpty()) {
            try {
                LocalDate nuevaFecha = LocalDate.parse(entrada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                this.fechaVencimiento = nuevaFecha;
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Se mantiene la anterior.");
            }
        }
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

}