package Inventario;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;


public class Main {
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Producto> inventario = new ArrayList<>();
    private static Usuario usuarioActual = null;
    private static Scanner consola = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarSistema();
        mostrarMenuPrincipal();
    }

    // Inicializa sistema con admin
    private static void inicializarSistema() {
        usuarios.add(new Administrador("admin", "admin123"));
    }

    // Menú principal del sistema
    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== SISTEMA DE SUPERMERCADO ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());

                switch (opcion) {
                    case 1:
                        iniciarSesion();
                        break;
                    case 2:
                        System.out.println("Saliendo del sistema...");
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException excepcionNumerica) {
                System.out.println("Error ingrese un número.");
            }
        }
    }

    private static Usuario validarDatos(String usuario, String contraseña) {
        for (Usuario usuarioIngreasdo : usuarios) {
            if (usuarioIngreasdo.getUsuario().equals(usuario) && usuarioIngreasdo.getContrasena().equals(contraseña)) {
                return usuarioIngreasdo;
            }
        }
        return null;
    }

    private static void iniciarSesion() {
        System.out.println("\n=== INICIO DE SESIÓN ===");

        while (true) {

                System.out.print("Usuario: ");
                String usuario = consola.nextLine().trim().toLowerCase();

                System.out.print("Contraseña: ");
                String contrasena = consola.nextLine().trim();

                usuarioActual = validarDatos(usuario, contrasena);

                if (usuarioActual != null) {
                    System.out.println("\nBienvenido, " + usuarioActual.getUsuario() + "!");
                    usuarioActual.mostrarMenu();
                    return;
                } else {
                    System.out.println("Datos incorrectas. Intente nuevamente o presione 0 para volver.");
                }
        }
    }

    public static void mostrarMenuAdministrador() {
        while (true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar empleados");
            System.out.println("3. Cerrar sesión");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());

                switch (opcion) {
                    case 1:
                        gestionarProductos();
                        break;
                    case 2:
                        gestionarEmpleados();
                        break;
                    case 3:
                        usuarioActual = null;
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException excepcionNumerica) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    // Menú para empleados
    public static void mostrarMenuEmpleado() {
        while (true) {
            System.out.println("\n=== MENÚ EMPLEADO ===");
            System.out.println("1. Ver inventario");
            System.out.println("2. Buscar producto");
            System.out.println("3. Cerrar sesión");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());

                switch (opcion) {
                    case 1:
                        verInventario();
                        break;
                    case 2:
                        buscarProducto();
                        break;
                    case 3:
                        usuarioActual = null;
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException excepciionNumerica) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }


    private static void gestionarProductos() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Editar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Ver inventario");
            System.out.println("5. Volver");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());

                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        editarProducto();
                        break;
                    case 3:
                        eliminarProducto();
                        break;
                    case 4:
                        verInventario();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException excepcionNumerica) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private static void agregarProducto() {
        System.out.println("\n=== AGREGAR PRODUCTO ===");

        while (true) { // Bucle para agregar múltiples productos

            try {
                System.out.print("Nombre: ");
                String nombre = consola.nextLine();

                System.out.print("Precio: ");
                double precio = Double.parseDouble(consola.nextLine().replace(",", ".").trim());

                System.out.print("Stock: ");
                int stock = Integer.parseInt(consola.nextLine().trim());

                System.out.print("Tipo (1. Perecedero, 2. No perecedero): ");
                int tipo = Integer.parseInt(consola.nextLine());

                Producto producto;

                if (tipo == 1) {
                    LocalDate fechaVencimiento = null;
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    while (true) {
                        System.out.print("Fecha vencimiento (dd/MM/yyyy): ");
                        String fecha = consola.nextLine();

                        try {
                            fechaVencimiento = LocalDate.parse(fecha, formato);

                            if (fechaVencimiento.isBefore(LocalDate.now())) {
                                System.out.println("Error: la fecha no puede ser anterior al día de hoy.");
                            } else {
                                break;
                            }

                        } catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha inválido. Use dd/MM/yyyy.");
                        }
                    }

                    producto = new Perecedero(nombre, precio, stock, fechaVencimiento);
                } else {
                    producto = new Producto(nombre, precio, stock);
                }

                inventario.add(producto);
                System.out.println("Producto agregado exitosamente!");

            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese valores numéricos válidos para precio y stock.");
            }

            System.out.print("¿Desea agregar otro producto? (si/no): ");
            String continuar = consola.nextLine();
            if (!continuar.equalsIgnoreCase("si")) {
                return; // Sale del método y vuelve al menú anterior
            }
        }
    }


    private static void editarProducto() {
        System.out.println("\n=== EDITAR PRODUCTO ===");
        verInventario();

        if (inventario.isEmpty()) return;

        try {
            System.out.print("Ingrese el número del producto a editar (0 para cancelar): ");
            int indice = Integer.parseInt(consola.nextLine()) - 1;

            if (indice == -1) return;
            if (indice < 0 || indice >= inventario.size()) {
                System.out.println("Número inválido.");
                return;
            }

            Producto producto = inventario.get(indice);
            producto.editar(consola);
            System.out.println("Producto actualizado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n=== ELIMINAR PRODUCTO ===");
        verInventario();

        if (inventario.isEmpty()) return;

        while (true) {
            try {
                System.out.print("Ingrese el número del producto a eliminar (0 para cancelar): ");
                int indice = Integer.parseInt(consola.nextLine()) - 1;

                if (indice == -1) return;
                if (indice < 0 || indice >= inventario.size()) {
                    System.out.println("Número de producto inválido.");
                    continue;
                }

                Producto producto = inventario.remove(indice);
                System.out.println("Producto '" + producto.getNombre() + "' eliminado.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = consola.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }

    private static void verInventario() {
        System.out.println("\n=== INVENTARIO ===");

        if (inventario.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }

        for (int i = 0; i < inventario.size(); i++) {
            System.out.println((i + 1) + ". " + inventario.get(i));
        }
    }

    private static void buscarProducto() {
        System.out.println("\n=== BUSCAR PRODUCTO ===");
        System.out.print("Ingrese nombre o parte del nombre: ");
        String busqueda = consola.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Producto p : inventario) {
            if (p.getNombre().toLowerCase().contains(busqueda)) {
                System.out.println(p);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos.");
        }
    }

    private static void gestionarEmpleados() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Agregar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Eliminar empleado");
            System.out.println("4. Volver");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());

                switch (opcion) {
                    case 1:
                        agregarEmpleado();
                        break;
                    case 2:
                        listarEmpleados();
                        break;
                    case 3:
                        eliminarEmpleado();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private static void agregarEmpleado() {
        System.out.println("\n=== AGREGAR EMPLEADO ===");

        while (true) {

            System.out.print("Nombre de usuario: ");
            String usuario = consola.nextLine().trim().toLowerCase();

            if (usuarioExiste(usuario)) {
                System.out.println("El usuario ya existe.");
                continue;
            }

            System.out.print("Contraseña: ");
            String contrasena = consola.nextLine();

            usuarios.add(new Empleado(usuario, contrasena));
            System.out.println("Empleado agregado exitosamente!");

            System.out.print("¿Desea ingresar otro empleado? (si/no): ");
            String respuesta = consola.nextLine();
            if (!respuesta.equalsIgnoreCase("si")) {
                return;
            }
        }
    }

    private static boolean usuarioExiste(String usuario) {
        for (Usuario usuarioIngresado : usuarios) {
            if (usuarioIngresado.getUsuario().equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    private static void listarEmpleados() {
        System.out.println("\n=== LISTA DE EMPLEADOS ===");

        if (usuarios.size() <= 1) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (int i = 1; i < usuarios.size(); i++) {
            System.out.println(i + ". " + usuarios.get(i).getUsuario());
        }
    }

    private static void eliminarEmpleado() {
        listarEmpleados();

        if (usuarios.size() <= 1)
            return;

        while (true) {
            try {
                System.out.print("Ingrese el número del empleado a eliminar (0 para cancelar): ");
                int indice = Integer.parseInt(consola.nextLine());

                if (indice == 0) return;
                if (indice < 1 || indice >= usuarios.size()) {
                    System.out.println("Número de empleado inválido.");
                    continue;
                }

                Usuario empleado = usuarios.remove(indice);
                System.out.println("Empleado '" + empleado.getUsuario() + "' eliminado.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }

            System.out.print("¿Desea intentar nuevamente? (si/no): ");
            String respuesta = consola.nextLine();
            if (!respuesta.equalsIgnoreCase("si")) {
                return;
            }
        }
    }
}