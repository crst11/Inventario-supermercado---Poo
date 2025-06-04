package Inventario;

public class Administrador extends Usuario {

    public Administrador(String usuario, String contraseña) {
        super(usuario, contraseña);
    }

    @Override
    public boolean esAdmin() {
        return true;
    }

    @Override
    public void mostrarMenu() {
        getSistema().mostrarMenuAdministrador();
    }
}