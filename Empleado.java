package Inventario;

public class Empleado extends Usuario {

    public Empleado(String usuario, String contraseña) {
        super(usuario, contraseña);
    }

    public void mostrarMenu() {
        getSistema().mostrarMenuEmpleado();
    }

    @Override
    public boolean esAdmin() {
        return false;
    }
}