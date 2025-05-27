package Inventario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaSupermercado {
    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    private final ArrayList<Producto> inventario = new ArrayList<>();
    private Usuario usuarioActual;

    static Scanner consola = new Scanner(System.in);

    public void iniciar() {
        inicializarSistema();
        mostrarMenuPrincipal();
    }

    private String leerEntradaNoVacia(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = consola.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo no puede estar vacío.");
        }
    }

    private int leerEnteroNoVacio(String mensaje, String mensajeError, int valorMinimo) {
        while (true) {
            String entrada = leerEntradaNoVacia(mensaje);
            try {
                int numero = Integer.parseInt(entrada);
                if (numero < valorMinimo) {
                    System.out.println(mensajeError);
                } else {
                    return numero;
                }
            } catch (NumberFormatException excepcion) {
                System.out.println("Error: Ingrese un número entero válido.");
            }
        }
    }

    private double leerDoubleNoVacio(String mensaje, String mensajeError, double valorMinimo) {
        while (true) {
            String entrada = leerEntradaNoVacia(mensaje);
            try {

                double numero = Double.parseDouble(entrada.replace(",", "."));
                if (numero < valorMinimo) {
                    System.out.println(mensajeError);
                } else {
                    return numero;
                }
            } catch (NumberFormatException excepcion) {
                System.out.println("Error: Ingrese un valor numérico válido (ej. 10.50).");
            }
        }
    }

    private Usuario validarDatos(String usuario, String contraseña) {
        for (Usuario usuarioIngreasdo : usuarios) {
            if (usuarioIngreasdo.getUsuario().equals(usuario) && usuarioIngreasdo.getContrasena().equals(contraseña)) {
                return usuarioIngreasdo;
            }
        }
        return null;
    }

    private void inicializarSistema() {
        usuarios.add(new Administrador("admin", "admin123"));
    }

    private void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== SISTEMA DE SUPERMERCADO ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine().trim());
                switch (opcion) {
                    case 1 -> iniciarSesion();
                    case 2 -> {
                        System.out.println("Saliendo del sistema...");
                        return;
                    }
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException excepcion) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }

    private void iniciarSesion() {
        System.out.println("\n=== INICIO DE SESIÓN ===");

        while (true) {

            System.out.print("Usuario: ");
            String usuario = consola.nextLine().trim().toLowerCase();

            System.out.print("Contraseña: ");
            String contrasena = consola.nextLine().trim();

            usuarioActual = validarDatos(usuario, contrasena);

            if (usuarioActual != null) {
                usuarioActual.setSistema(this);
                System.out.println("\nBienvenido, " + usuarioActual.getUsuario() + "!");
                usuarioActual.mostrarMenu();
                return;
            } else {
                System.out.println("Datos incorrectas. Intente nuevamente o presione 0 para volver.");
                System.out.print("¿Desea intentar nuevamente? (si/0): ");
                String respuesta = consola.nextLine().trim();
                if (respuesta.equals("0")) return;
            }
        }
    }

    //Menu de administradores
    public void mostrarMenuAdministrador() {
        while (true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar empleados");
            System.out.println("3. Gestionar administradores");
            System.out.println("4. Cerrar sesión");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());
                switch (opcion) {
                    case 1 -> gestionarProductos();
                    case 2 -> gestionarEmpleados();
                    case 3 -> gestionarAdministradores();
                    case 4 -> {
                        usuarioActual = null;
                        return;
                    }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException excepcionNumerica) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    // Menú para empleados
    public void mostrarMenuEmpleado() {
        while (true) {
            System.out.println("\n=== MENÚ EMPLEADO ===");
            System.out.println("1. Ver inventario");
            System.out.println("2. Buscar producto");
            System.out.println("3. Cerrar sesión");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());
                switch (opcion) {
                    case 1 -> verInventario();
                    case 2 -> buscarProducto();
                    case 3 -> {
                        usuarioActual = null;
                        return;
                    }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException excepciionNumerica) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private void gestionarProductos() {
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
                    case 1 -> agregarProducto();
                    case 2 -> editarProducto();
                    case 3 -> eliminarProducto();
                    case 4 -> verInventario();
                    case 5 -> { return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException excepcionNumerica) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private Producto buscaEnAgregarProducto(String nombre) {
        String nombreBuscado = nombre.trim().toLowerCase();
        for (Producto producto : inventario) {
            if (producto.getNombre().toLowerCase().equals(nombreBuscado)) {
                return producto;
            }
        }
        return null;
    }

    private void agregarProducto() {
        System.out.println("\n=== AGREGAR PRODUCTO ===");

        while (true) {
            String nombre = leerEntradaNoVacia("Nombre: ");

            Producto productoExistente = buscaEnAgregarProducto(nombre);
            if (productoExistente != null) {
                System.out.println("Ya existe un producto con el nombre '" + nombre + "':");
                System.out.println(productoExistente.toString());
                System.out.print("¿Desea editar este producto en su lugar? (si/no): ");
                String respuestaEdicion = consola.nextLine().trim().toLowerCase();

                if (respuestaEdicion.equals("si")) {
                    System.out.println("\n--- Editando producto existente ---");
                    productoExistente.editar(consola);
                    System.out.println("Producto '" + nombre + "' actualizado exitosamente.");
                    System.out.print("¿Desea agregar otro producto? (si/no): ");
                    String respuestaSeguirAgregando = consola.nextLine().trim().toLowerCase();
                    if (!respuestaSeguirAgregando.equals("si")) {
                        return;
                    } else {
                        continue;
                    }
                } else {
                    System.out.println("Operación de agregar cancelada para este nombre. Intente con un nombre diferente si desea agregar uno nuevo.");
                    System.out.print("¿Desea agregar otro producto con un nombre diferente? (si/no): ");
                    String respuestaOtroProducto = consola.nextLine().trim().toLowerCase();
                    if (!respuestaOtroProducto.equals("si")) {
                        return;
                    } else {
                        continue;
                    }
                }
            }
            double precio = leerDoubleNoVacio("Precio: ", "El precio debe ser mayor que 0.", 0.01);

            int stock = leerEnteroNoVacio("Stock: ", "El stock debe ser mayor que 0.", 1);

            int tipo = leerEnteroNoVacio("Tipo (1. Perecedero, 2. No perecedero): ", "Seleccione una opción válida (1 o 2).", 1);
            while (tipo != 1 && tipo != 2) {
                tipo = leerEnteroNoVacio("Seleccione una opción válida (1. Perecedero, 2. No perecedero): ", "Seleccione una opción válida (1 o 2).", 1);
            }

            Producto producto;

            if (tipo == 1) {
                LocalDate fechaVencimiento;
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                while (true) {
                    System.out.print("Fecha vencimiento (dd/MM/yyyy): ");
                    String entradaFecha = consola.nextLine().trim();
                    if (entradaFecha.isEmpty()) {
                        System.out.println("La fecha de vencimiento no puede estar vacía.");
                        continue;
                    }
                    try {
                        fechaVencimiento = LocalDate.parse(entradaFecha, formato);
                        if (fechaVencimiento.isBefore(LocalDate.now())) {
                            System.out.println("La fecha no puede ser anterior al día de hoy.");
                        } else {
                            break;
                        }
                    } catch (DateTimeParseException excepcion) {
                        System.out.println("Formato de fecha inválido. Use dd/MM/yyyy.");
                    }
                }
                producto = new Perecedero(nombre, precio, stock, fechaVencimiento);
            } else {
                producto = new Producto(nombre, precio, stock);
            }

            inventario.add(producto);
            System.out.println("¡Producto agregado exitosamente!");

            System.out.print("¿Desea agregar otro producto? (si/no): ");
            String respuesta = consola.nextLine().trim().toLowerCase();
            if (!respuesta.equals("si")) {
                return;
            }
        }
    }

    private void editarProducto() {
        System.out.println("\n=== EDITAR PRODUCTO ===");
        verInventario();

        if (inventario.isEmpty()) return;

        int indice = leerEnteroNoVacio("Ingrese el número del producto a editar (0 para cancelar): ", "Número inválido. Ingrese un número positivo.", 0) - 1;

        if (indice == -1) return;

        if (indice < 0 || indice >= inventario.size()) {
            System.out.println("Número de producto fuera de rango.");
            return;
        }
        inventario.get(indice).editar(consola);
        System.out.println("Producto actualizado correctamente.");
    }

    private void eliminarProducto() {
        System.out.println("\n=== ELIMINAR PRODUCTO ===");
        verInventario();

        if (inventario.isEmpty()) return;

        while (true) {
            int indice = leerEnteroNoVacio("Ingrese el número del producto a eliminar (0 para cancelar): ", "Número inválido. Ingrese un número positivo.", 0) - 1;

            if (indice == -1) return;

            if (indice >= 0 && indice < inventario.size()) {
                Producto eliminado = inventario.remove(indice);
                System.out.println("Producto '" + eliminado.getNombre() + "' eliminado.");
                return;
            } else {
                System.out.println("Número inválido o producto no encontrado.");
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = consola.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }

    private void verInventario() {
        System.out.println("\n=== INVENTARIO ===");

        if (inventario.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }

        for (int i = 0; i < inventario.size(); i++) {
            System.out.println((i + 1) + ". " + inventario.get(i));
        }
    }

    private void buscarProducto() {
        System.out.println("\n=== BUSCAR PRODUCTO ===");
        System.out.print("Ingrese nombre o parte del nombre: ");
        String busqueda = consola.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Producto producto : inventario) {
            if (producto.getNombre().toLowerCase().contains(busqueda)) {
                System.out.println(producto);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos.");
        }
    }

    private void gestionarEmpleados() {
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
                    case 1 -> agregarEmpleado();
                    case 2 -> listarEmpleados();
                    case 3 -> eliminarEmpleado();
                    case 4 -> {
                        return;
                    }
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private void agregarEmpleado() {
        System.out.println("\n=== AGREGAR EMPLEADO ===");

        while (true) {
            String usuario;
            while (true) {
                usuario = leerEntradaNoVacia("Nombre de usuario: ");

                if (usuarioExiste(usuario)) {
                    System.out.println("El usuario ya existe.");
                    continue;
                }
                break;
            }

            String contraseña = leerEntradaNoVacia("Contraseña: ");

            usuarios.add(new Empleado(usuario, contraseña));
            System.out.println("Empleado agregado exitosamente!");

            System.out.print("¿Desea ingresar otro empleado? (si/no): ");
            String respuesta = consola.nextLine();
            if (!respuesta.equalsIgnoreCase("si")) {
                return;
            }
        }
    }

    private boolean usuarioExiste(String usuario) {
        for (Usuario usuarioIngresado : usuarios) {
            if (usuarioIngresado.getUsuario().equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    private void listarEmpleados() {
        System.out.println("\n=== LISTA DE EMPLEADOS ===");

        int contador = 1;
        boolean hayEmpleados = false;

        for (Usuario u : usuarios) {
            if (u.getClass().getSimpleName().equals("Empleado")) {
                System.out.println(contador++ + ". " + u.getUsuario());
                hayEmpleados = true;
            }
        }

        if (!hayEmpleados) {
            System.out.println("No hay empleados registrados.");
        }
    }


    private void eliminarEmpleado() {
        listarEmpleados();

        if (usuarios.stream().noneMatch(u -> u.getClass().getSimpleName().equals("Empleado"))) {
            System.out.println("No hay empleados para eliminar.");
            return;
        }

        while (true) {
            int indice = leerEnteroNoVacio("Ingrese el número del empleado a eliminar (0 para cancelar): ", "Número de empleado inválido.", 0);

            if (indice == 0) return;

            ArrayList<Usuario> soloEmpleados = new ArrayList<>();
            for (Usuario u : usuarios) {
                if (u.getClass().getSimpleName().equals("Empleado")) {
                    soloEmpleados.add(u);
                }
            }

            if (indice > 0 && indice <= soloEmpleados.size()) {
                Usuario empleadoAEliminar = soloEmpleados.get(indice - 1);
                usuarios.remove(empleadoAEliminar);
                System.out.println("Empleado '" + empleadoAEliminar.getUsuario() + "' eliminado.");
                return;
            } else {
                System.out.println("Número de empleado inválido.");
            }

            System.out.print("¿Desea intentar nuevamente? (si/no): ");
            String respuesta = consola.nextLine();
            if (!respuesta.equalsIgnoreCase("si")) {
                return;
            }
        }
    }

    private void gestionarAdministradores() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE ADMINISTRADORES ===");
            System.out.println("1. Agregar administrador");
            System.out.println("2. Listar administradores");
            System.out.println("3. Eliminar administrador");
            System.out.println("4. Volver");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(consola.nextLine());

                switch (opcion) {
                    case 1 -> agregarAdministrador();
                    case 2 -> listarAdministradores();
                    case 3 -> eliminarAdministrador();
                    case 4 -> {
                        return;
                    }
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private void agregarAdministrador() {
        System.out.println("\n=== AGREGAR ADMINISTRADOR ===");

        while (true) {
            String usuario;
            while (true) {
                usuario = leerEntradaNoVacia("Nombre de usuario: ").toLowerCase();

                if (usuarioExiste(usuario)) {
                    System.out.println("Ese nombre de usuario ya existe.");
                    continue;
                }
                break;
            }

            String contraseña = leerEntradaNoVacia("Contraseña: ");

            usuarios.add(new Administrador(usuario, contraseña));
            System.out.println("Administrador agregado exitosamente.");

            System.out.print("¿Desea agregar otro? (si/no): ");
            if (!consola.nextLine().equalsIgnoreCase("si")) {
                return;
            }
        }
    }

    private void listarAdministradores() {
        System.out.println("\n=== LISTA DE ADMINISTRADORES ===");
        int contador = 1;
        boolean hayAdministradores = false;

        for (Usuario usuario : usuarios) {
            if (usuario.getClass().getSimpleName().equals("Administrador") &&
                    !usuario.getUsuario().equals("admin")) {
                System.out.println(contador++ + ". " + usuario.getUsuario());
                hayAdministradores = true;
            }
        }

        if (!hayAdministradores) {
            System.out.println("No hay otros administradores registrados.");
        }
    }

    private void eliminarAdministrador() {
        System.out.println("\n=== ELIMINAR ADMINISTRADOR ===");

        ArrayList<Usuario> adminsEliminables = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getClass().getSimpleName().equals("Administrador") && !usuario.getUsuario().equals("admin")) {
                adminsEliminables.add(usuario);
            }
        }

        if (adminsEliminables.isEmpty()) {
            System.out.println("No hay administradores eliminables.");
            return;
        }

        for (int i = 0; i < adminsEliminables.size(); i++) {
            System.out.println((i + 1) + ". " + adminsEliminables.get(i).getUsuario());
        }

        while (true) {
            int seleccion = leerEnteroNoVacio("Seleccione el número del administrador a eliminar (0 para cancelar): ", "Selección inválida.", 0);

            if (seleccion == 0) return;
            if (seleccion < 1 || seleccion > adminsEliminables.size()) {
                System.out.println("Selección inválida.");
                continue;
            }

            Usuario adminAEliminar = adminsEliminables.get(seleccion - 1);
            usuarios.remove(adminAEliminar);
            System.out.println("Administrador eliminado correctamente.");
            return;
        }
    }
}