package Inventario;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoTxt {

    public static class archivoTxt {
        private final File archivoUsuarios = new File("usuarios.txt");
        private final File archivoInventario = new File("inventario.txt");
        private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        public archivoTxt() {
            try {
                if (!archivoUsuarios.exists()) {
                    archivoUsuarios.createNewFile();
                }
                if (!archivoInventario.exists()) {
                    archivoInventario.createNewFile();
                }
            } catch (IOException e) {
                System.err.println("Error al crear archivos de datos: " + e.getMessage());
            }
        }

        public void agregarUsuario(String nombre, String contraseña, String rol) {
            List<Usuario> usuariosActuales = listarUsuarios();

            if (usuariosActuales.stream().anyMatch(u -> u.getUsuario().equalsIgnoreCase(nombre))) {
                System.out.println(" ");
            }

            if (rol.equalsIgnoreCase("ADMIN")) {
                usuariosActuales.add(new Administrador(nombre, contraseña));
            } else if (rol.equalsIgnoreCase("EMPLEADO")) {
                usuariosActuales.add(new Empleado(nombre, contraseña));
            } else {
                System.err.println("Rol de usuario desconocido: " + rol);
                return;
            }
            guardarUsuarios(usuariosActuales);
        }

        public Usuario buscarUsuario(String nombre, String contraseña) {
            List<Usuario> todosLosUsuarios = listarUsuarios();
            for (Usuario u : todosLosUsuarios) {
                if (u.getUsuario().equalsIgnoreCase(nombre) && u.getContrasena().equals(contraseña)) {
                    if (u instanceof Administrador) {
                        return new Administrador(u.getUsuario(), u.getContrasena());
                    } else if (u instanceof Empleado) {
                        return new Empleado(u.getUsuario(), u.getContrasena());
                    }
                }
            }
            return null;
        }

        public List<Usuario> listarUsuarios() {
            List<Usuario> lista = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivoUsuarios))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        String nombre = partes[0];
                        String contraseña = partes[1];
                        String rol = partes[2];
                        if (rol.equalsIgnoreCase("ADMIN")) {
                            lista.add(new Administrador(nombre, contraseña));
                        } else {
                            lista.add(new Empleado(nombre, contraseña));
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al listar usuarios desde el archivo: " + e.getMessage());
            }
            return lista;
        }

        public void guardarUsuarios(List<Usuario> usuarios) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, false))) {
                for (Usuario user : usuarios) {
                    String rol = "";
                    if (user instanceof Administrador) {
                        rol = "ADMIN";
                    } else if (user instanceof Empleado) {
                        rol = "EMPLEADO";
                    }
                    writer.write(user.getUsuario() + "," + user.getContrasena() + "," + rol);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error al guardar usuarios en el archivo: " + e.getMessage());
            }
        }

        public void guardarProductos(List<Producto> productos) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoInventario, false))) {
                for (Producto producto : productos) {
                    writer.write(producto.toCsvString());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error al guardar inventario en el archivo: " + e.getMessage());
            }
        }

        public List<Producto> listarProductos() {
            List<Producto> lista = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivoInventario))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 4) {
                        String tipo = partes[0];
                        String nombre = partes[1];
                        double precio = Double.parseDouble(partes[2]);
                        int stock = Integer.parseInt(partes[3]);

                        if (tipo.equalsIgnoreCase("NO_PERECEDERO")) {
                            lista.add(new Producto(nombre, precio, stock));
                        } else if (tipo.equalsIgnoreCase("PERECEDERO") && partes.length == 5) {
                            try {
                                LocalDate fechaVencimiento = LocalDate.parse(partes[4], FORMATO_FECHA);
                                lista.add(new Perecedero(nombre, precio, stock, fechaVencimiento));
                            } catch (DateTimeParseException e) {
                                System.err.println("Error de formato de fecha para producto perecedero: " + linea);
                            }
                        } else {
                            System.err.println("Formato de línea de producto desconocido o inválido: " + linea);
                        }
                    } else {
                        System.err.println("Línea de inventario inválida: " + linea);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al listar productos desde el archivo: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error de formato numérico al leer producto: " + e.getMessage());
            }
            return lista;
        }
    }
}
