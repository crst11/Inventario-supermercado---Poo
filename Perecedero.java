package Inventario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Perecedero extends Producto {

    private LocalDate fechaVencimiento;

    public Perecedero(String nombre, double precio, int stock, LocalDate fechaVencimiento) {
        super(nombre, precio, stock);
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean estaVencido() {
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        String vencido = estaVencido() ? " (VENCIDO)" : "";
        return
                super.toString() + " | Vence: " +
                fechaVencimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + vencido;
    }

    @Override
    public void editar(Scanner consola) {
        super.editar(consola);
        System.out.print("Nueva fecha de vencimiento (dd/MM/yyyy) o ENTER para mantener: ");
        String entrada = consola.nextLine().trim();
        if (!entrada.isEmpty()) {
            try {
                LocalDate nuevaFecha = LocalDate.parse(entrada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (nuevaFecha.isBefore(LocalDate.now())) {
                    System.out.println("Error: La fecha de vencimiento no puede ser anterior a hoy.");
                } else {
                    this.fechaVencimiento = nuevaFecha;
                }
            } catch (DateTimeParseException excepcion) {
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
