package Inventario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaSupermercado {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Producto> inventario = new ArrayList<>();
    private Usuario usuarioActual = null;
    private Scanner consola = new Scanner(System.in);

    public SistemaSupermercado() {
        inicializarSistema();
        mostrarMenuPrincipal();
    }

    public void iniciar() {
        inicializarSistema();
        mostrarMenuPrincipal();
    }

    private void inicializarSistema() {
        this.usuarios.add(new Administrador("admin", "admin123"));
    }

    private void mostrarMenuPrincipal() {
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

    private Usuario validarDatos(String usuario, String contraseña) {
        for (Usuario usuarioIngreasdo : usuarios) {
            if (usuarioIngreasdo.getUsuario().equals(usuario) && usuarioIngreasdo.getContrasena().equals(contraseña)) {
                return usuarioIngreasdo;
            }
        }
        return null;
    }

    private String leerEntradaNoVacia(String mensaje) {
        String entrada;
        do {
            System.out.print(mensaje);
            entrada = consola.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (entrada.isEmpty());
        return entrada;
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
                    case 1:
                        gestionarProductos();
                        break;
                    case 2:
                        gestionarEmpleados();
                        break;
                    case 3:
                        gestionarAdministradores();
                        break;
                    case 4:
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

    private void agregarProducto() {
        System.out.println("\n=== AGREGAR PRODUCTO ===");

        while (true) {
            try {
                String nombre = leerEntradaNoVacia("Nombre: ");

                System.out.print("Precio: ");
                double precio = Double.parseDouble(consola.nextLine().replace(",", ".").trim());

                System.out.print("Stock: ");
                int stock = Integer.parseInt(consola.nextLine().trim());

                System.out.print("Tipo (1. Perecedero, 2. No perecedero): ");
                int tipo = Integer.parseInt(consola.nextLine());

                Producto producto;

                if (tipo == 1) {
                    LocalDate fechaVencimiento;
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
                return;
            }
        }
    }


    private void editarProducto() {
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

    private void eliminarProducto() {
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

    private void agregarEmpleado() {
        System.out.println("\n=== AGREGAR EMPLEADO ===");

        while (true) {
            String usuario;
            while (true) {
                System.out.print("Nombre de usuario: ");
                usuario = consola.nextLine().trim().toLowerCase();

                if (usuario.isEmpty()) {
                    System.out.println("El nombre de usuario no puede estar vacío.");
                    continue;
                }

                if (usuarioExiste(usuario)) {
                    System.out.println("El usuario ya existe.");
                    continue;
                }

                break;
            }

            String contraseña;
            while (true) {
                System.out.print("Contraseña: ");
                contraseña = consola.nextLine().trim();

                if (contraseña.isEmpty()) {
                    System.out.println("La contraseña no puede estar vacía.");
                    continue;
                }

                break;
            }

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
                    case 1:
                        agregarAdministrador();
                        break;
                    case 2:
                        listarAdministradores();
                        break;
                    case 3:
                        eliminarAdministrador();
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

    private void agregarAdministrador() {
        System.out.println("\n=== AGREGAR ADMINISTRADOR ===");

        while (true) {
            String usuario;
            while (true) {
                System.out.print("Nombre de usuario: ");
                usuario = consola.nextLine().trim().toLowerCase();

                if (usuario.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío.");
                    continue;
                }

                if (usuarioExiste(usuario)) {
                    System.out.println("Ese nombre de usuario ya existe.");
                    continue;
                }

                break;
            }

            String contraseña;
            while (true) {
                System.out.print("Contraseña: ");
                contraseña = consola.nextLine().trim();
                if (contraseña.isEmpty()) {
                    System.out.println("La contraseña no puede estar vacía.");
                    continue;
                }
                break;
            }

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

        ArrayList<Usuario> admins = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getClass().getSimpleName().equals("Administrador") && !usuario.getUsuario().equals("admin")) {
                admins.add(usuario);
            }
        }

        if (admins.isEmpty()) {
            System.out.println("No hay administradores eliminables.");
            return;
        }

        for (int i = 0; i < admins.size(); i++) {
            System.out.println((i + 1) + ". " + admins.get(i).getUsuario());
        }

        while (true) {
            try {
                System.out.print("Seleccione el número del administrador a eliminar (0 para cancelar): ");
                int seleccion = Integer.parseInt(consola.nextLine());

                if (seleccion == 0) return;
                if (seleccion < 1 || seleccion > admins.size()) {
                    System.out.println("Selección inválida.");
                    continue;
                }

                Usuario adminAEliminar = admins.get(seleccion - 1);
                usuarios.remove(adminAEliminar);
                System.out.println("Administrador eliminado correctamente.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        }
    }
}