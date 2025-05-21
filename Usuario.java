package Inventario;

abstract class Usuario {

    private String usuario;
    private String contraseña;
    private SistemaSupermercado sistema;

    public Usuario(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contraseña = contrasena;
    }

    public Usuario() {
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

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contraseña = contrasena;
    }


    public SistemaSupermercado getSistema() {
        return sistema;
    }

    public void setSistema(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

}