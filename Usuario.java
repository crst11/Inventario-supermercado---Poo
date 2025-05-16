package Inventario;

abstract class Usuario {

    private String usuario;
    private String contraseña;

    public Usuario(String usuario, String contrasena) {
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        this.usuario = usuario;
        this.contraseña = contrasena;
    }

    public Usuario() {
    }

    protected abstract boolean esAdmin();

    public abstract void mostrarMenu();

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contraseña;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contraseña = contrasena;
    }

}