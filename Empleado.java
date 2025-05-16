package Inventario;

class Empleado extends Usuario {

    public Empleado(String usuario, String contraseña) {
        super(usuario, contraseña);
    }

    public Empleado(){
    }

    @Override
    public void mostrarMenu() {
        Main.mostrarMenuEmpleado();
    }

    @Override
    public boolean esAdmin() {
        return false;
    }
}