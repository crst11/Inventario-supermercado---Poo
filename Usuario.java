package Inventario;

public abstract class Usuario {

    private String usuario;
    private String contraseña;
    private SistemaSupermercado sistema;

    public Usuario(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contraseña = contrasena;
    }

    protected void mostrarMenu(){
    }

    protected abstract boolean esAdmin();

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contraseña;
    }

    public SistemaSupermercado getSistema() {
        return sistema;
    }

    public void setSistema(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

}