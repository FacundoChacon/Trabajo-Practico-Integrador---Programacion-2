package com.foodstore;

import com.foodstore.entities.*;
import com.foodstore.enums.Estado;
import com.foodstore.enums.FormaPago;
import com.foodstore.enums.Rol;
import com.foodstore.exceptions.NegocioException;
import com.foodstore.exceptions.PersistenciaException;
import com.foodstore.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaService categoriaService = new CategoriaServiceImpl();
    private static final ProductoService productoService = new ProductoServiceImpl();
    private static final UsuarioService usuarioService = new UsuarioServiceImpl();
    private static final PedidoService pedidoService = new PedidoServiceImpl();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=======================================");
            System.out.println("      FOOD STORE - MENÚ PRINCIPAL      ");
            System.out.println("=======================================");
            System.out.println("1. Gestión de Usuarios");
            System.out.println("2. Gestión de Categorías");
            System.out.println("3. Gestión de Productos");
            System.out.println("4. Gestión de Pedidos");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> menuUsuarios();
                    case 2 -> menuCategorias();
                    case 3 -> menuProductos();
                    case 4 -> menuPedidos();
                    case 0 -> System.out.println("¡Gracias por utilizar Food Store!");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido de opción.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private static void menuUsuarios() {
        System.out.println("\n--- GESTIÓN DE USUARIOS ---");
        System.out.println("1. Listar Usuarios");
        System.out.println("2. Crear Usuario");
        System.out.println("3. Editar Usuario");
        System.out.println("4. Eliminar Usuario");
        System.out.print("Seleccione una opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1 -> {
                    System.out.println("\n--- Listado de Usuarios Activos ---");
                    usuarioService.listar().forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("Nombre: "); String nom = scanner.nextLine();
                    System.out.print("Apellido: "); String ape = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Celular: "); String cel = scanner.nextLine();
                    System.out.print("Contraseña: "); String pass = scanner.nextLine();
                    System.out.print("Rol (1- ADMIN, 2- USUARIO): ");
                    int r = Integer.parseInt(scanner.nextLine());
                    Rol rol = (r == 1) ? Rol.ADMIN : Rol.USUARIO;

                    Usuario nuevo = new Usuario(false, nom, ape, email, cel, pass, rol);
                    usuarioService.crear(nuevo, email);
                }
                case 3 -> {
                    System.out.print("ID del usuario a editar: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nuevo Nombre (vacío para omitir): "); String nom = scanner.nextLine();
                    System.out.print("Nuevo Apellido (vacío para omitir): "); String ape = scanner.nextLine();

                    Usuario datos = new Usuario();
                    datos.setNombre(nom);
                    datos.setApellido(ape);
                    usuarioService.editar(id, datos);
                }
                case 4 -> {
                    System.out.print("ID del usuario a eliminar (Baja Lógica): ");
                    int id = Integer.parseInt(scanner.nextLine());
                    usuarioService.eliminar(id);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Debe ingresar un número entero válido para IDs u opciones.");
        } catch (NegocioException e) {
            System.err.println("\n[VALIDACIÓN DE NEGOCIO] " + e.getMessage());
        } catch (PersistenciaException e) {
            gestionarErrorSQL(e);
        } catch (Exception e) {
            System.err.println("[ERROR GENERAL INESPERADO] " + e.getMessage());
        }
    }

    // =========================================================================
    // 2. MENÚ - GESTIÓN DE CATEGORÍAS
    // =========================================================================
    private static void menuCategorias() {
        System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
        System.out.println("1. Listar Categorías");
        System.out.println("2. Crear Categoría");
        System.out.println("3. Editar Categoría");
        System.out.println("4. Eliminar Categoría");
        System.out.print("Seleccione una opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1 -> {
                    System.out.println("\n--- Listado de Categorías Activas ---");
                    categoriaService.listar().forEach(c ->
                            System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Descripción: " + c.getDescripcion())
                    );
                }
                case 2 -> {
                    System.out.print("Nombre de la Categoría: "); String nom = scanner.nextLine();
                    System.out.print("Descripción: "); String desc = scanner.nextLine();

                    Categoria nueva = new Categoria(false, nom, desc);
                    categoriaService.crear(nueva);
                }
                case 3 -> {
                    System.out.print("ID de la categoría a editar: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo Nombre: "); String nom = scanner.nextLine();
                    System.out.print("Nueva Descripción: "); String desc = scanner.nextLine();

                    Categoria datos = new Categoria();
                    datos.setNombre(nom);
                    datos.setDescripcion(desc);
                    categoriaService.editar(id, datos);
                }
                case 4 -> {
                    System.out.print("ID de la categoría a eliminar: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    categoriaService.eliminar(id);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Entrada numérica inválida.");
        } catch (NegocioException e) {
            System.err.println("\n[VALIDACIÓN DE NEGOCIO] " + e.getMessage());
        } catch (PersistenciaException e) {
            gestionarErrorSQL(e);
        } catch (Exception e) {
            System.err.println("[ERROR GENERAL INESPERADO] " + e.getMessage());
        }
    }

    // =========================================================================
    // 3. MENÚ - GESTIÓN DE PRODUCTOS
    // =========================================================================
    private static void menuProductos() {
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Listar Productos");
        System.out.println("2. Crear Producto");
        System.out.println("3. Editar Producto");
        System.out.println("4. Eliminar Producto");
        System.out.print("Seleccione una opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1 -> {
                    System.out.println("\n--- Listado de Productos Activos ---");
                    productoService.listar().forEach(p ->
                            System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock() + " | Categoría: " + p.getCategoria().getNombre())
                    );
                }
                case 2 -> {
                    System.out.print("Nombre del Producto: "); String nom = scanner.nextLine();
                    System.out.print("Precio: "); Double precio = Double.parseDouble(scanner.nextLine());
                    System.out.print("Descripción: "); String desc = scanner.nextLine();
                    System.out.print("Stock Inicial: "); int stock = Integer.parseInt(scanner.nextLine());
                    System.out.print("URL Imagen: "); String img = scanner.nextLine();
                    System.out.print("ID de la Categoría Asociada: "); Long catId = Long.parseLong(scanner.nextLine());

                    Categoria cat = new Categoria();
                    cat.setId(catId);

                    Producto nuevo = new Producto(false, nom, precio, desc, stock, img, true, cat);
                    productoService.crear(nuevo);
                }
                case 3 -> {
                    System.out.print("ID del producto a editar: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo Nombre: "); String nom = scanner.nextLine();
                    System.out.print("Nuevo Precio: "); Double precio = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nuevo Stock: "); int stock = Integer.parseInt(scanner.nextLine());

                    Producto datos = new Producto();
                    datos.setNombre(nom);
                    datos.setPrecio(precio);
                    datos.setStock(stock);
                    productoService.editar(id, datos);
                }
                case 4 -> {
                    System.out.print("ID del producto a eliminar: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    productoService.eliminar(id);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] El precio o ID ingresado no coincide con el tipo de dato esperado.");
        } catch (NegocioException e) {
            System.err.println("\n[VALIDACIÓN DE NEGOCIO] " + e.getMessage());
        } catch (PersistenciaException e) {
            gestionarErrorSQL(e);
        } catch (Exception e) {
            System.err.println("[ERROR GENERAL INESPERADO] " + e.getMessage());
        }
    }

    // =========================================================================
    // 4. MENÚ - GESTIÓN DE PEDIDOS
    // =========================================================================
    private static void menuPedidos() {
        System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
        System.out.println("1. Listar Pedidos");
        System.out.println("2. Crear Pedido Completo (HU-PED-02)");
        System.out.println("3. Actualizar Estado o Forma de Pago");
        System.out.println("4. Eliminar Pedido");
        System.out.print("Seleccione una opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1 -> {
                    System.out.println("\n--- Listado Histórico de Pedidos ---");
                    pedidoService.listar().forEach(p ->
                            System.out.println("ID Pedido: " + p.getId() + " | Cliente: " + p.getUsuario().getNombre() + " | Estado: " + p.getEstado() + " | TOTAL: $" + p.getTotal())
                    );
                }
                case 2 -> {
                    System.out.print("Ingrese ID del Usuario/Cliente: ");
                    long userId = Long.parseLong(scanner.nextLine());
                    Usuario cliente = new Usuario();
                    cliente.setId(userId);

                    Pedido nuevoPedido = new Pedido();
                    nuevoPedido.setUsuario(cliente);

                    System.out.print("Seleccione Forma de Pago (1- TARJETA, 2- TRANSFERENCIA, 3- EFECTIVO): ");
                    int fp = Integer.parseInt(scanner.nextLine());
                    nuevoPedido.setFormaPago((fp == 1) ? FormaPago.TARJETA : (fp == 2) ? FormaPago.TRANSFERENCIA : FormaPago.EFECTIVO);

                    List<DetallePedido> carroDeCompras = new ArrayList<>();
                    String agregarMas;

                    do {
                        System.out.print("ID del Producto a comprar: ");
                        Long prodId = Long.parseLong(scanner.nextLine());
                        System.out.print("Cantidad: ");
                        int cant = Integer.parseInt(scanner.nextLine());

                        Producto p = new Producto();
                        p.setId(prodId);

                        DetallePedido linea = new DetallePedido(cant, p);
                        carroDeCompras.add(linea);

                        System.out.print("¿Desea agregar otro producto al pedido? (S/N): ");
                        agregarMas = scanner.nextLine();
                    } while (agregarMas.equalsIgnoreCase("S"));

                    nuevoPedido.setDetallesPedido(carroDeCompras);
                    pedidoService.crear(nuevoPedido);
                }
                case 3 -> {
                    System.out.print("ID del Pedido a modificar: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println("Seleccione Nuevo Estado (1- PENDIENTE, 2- CONFIRMADO, 3- TERMINADO, 4- CANCELADO):");
                    int estOp = Integer.parseInt(scanner.nextLine());
                    Estado nuevoEst = switch (estOp) {
                        case 2 -> Estado.CONFIRMADO;
                        case 3 -> Estado.TERMINADO;
                        case 4 -> Estado.CANCELADO;
                        default -> Estado.PENDIENTE;
                    };
                    pedidoService.cambiarEstado(id, nuevoEst);
                }
                case 4 -> {
                    System.out.print("ID del Pedido a eliminar (Baja lógica): ");
                    Long id = Long.parseLong(scanner.nextLine());
                    pedidoService.eliminar(id);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Ingrese números correctos.");
        } catch (NegocioException e) {
            System.err.println("\n[VALIDACIÓN DE NEGOCIO] " + e.getMessage());
        } catch (PersistenciaException e) {
            gestionarErrorSQL(e);
        } catch (Exception e) {
            System.err.println("[ERROR GENERAL INESPERADO] " + e.getMessage());
        }
    }

    private static void gestionarErrorSQL(PersistenciaException ex) {
        System.err.println("\n-------------------------------------------------------");
        System.err.println("          ERROR EN LA BASE DE DATOS         ");
        System.err.println("-------------------------------------------------------");
        System.err.println("Mensaje: " + ex.getMessage());

        int code = ex.getErrorCode();
        String state = ex.getSQLState();

        switch (code) {
            case 1062 -> System.err.println("Motivo: Clave duplicada. Estás intentando guardar un registro con un e-mail o nombre único que ya existe.");
            case 1451, 1452 -> System.err.println("Motivo: Conflicto de Integridad (FK). No podés borrar o asociar este elemento porque tiene dependencias en otra tabla.");
            case 1045 -> System.err.println("Motivo: Credenciales incorrectas. Revisá los accesos a la DB en tu persistence.xml.");
            case 1146 -> System.err.println("Motivo: Tabla inexistente. Asegurate de correr el script SQL de creación de tablas.");
            default -> {
                System.err.println("Código Nativo Base de Datos: " + code);
                System.err.println("Estado SQL: " + state);
                System.err.println("Causa técnica: " + (ex.getCause() != null ? ex.getCause().getMessage() : "Desconocida"));
            }
        }
        System.err.println("-------------------------------------------------------\n");
    }
}