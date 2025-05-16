package Inventario;

class Administrador extends Usuario {

    public Administrador(String usuario, String contraseña) {
        super(usuario, contraseña);
    }

    public Administrador(){
    }

    @Override
    public boolean esAdmin() {
        return true;
    }

    @Override
    public void mostrarMenu() {
        Main.mostrarMenuAdministrador();
    }
}