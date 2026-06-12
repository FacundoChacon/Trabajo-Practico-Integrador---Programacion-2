import entities.*;
import enums.*;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Inyección de dependencias de la capa de Negocio
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
                System.out.println("Error: Por favor, ingrese un número válido.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    // =========================================================================
    // 1. MENÚ - GESTIÓN DE USUARIOS
    // =========================================================================
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
        } catch (Exception e) {
            System.out.println("Error en Usuarios: " + e.getMessage());
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
                            System.out.println(c.toString())
                    );
                }
                case 2 -> {
                    System.out.print("Nombre de la Categoría: "); String nom = scanner.nextLine();
                    System.out.print("Descripción: "); String desc = scanner.nextLine();

                    Categoria nueva = new Categoria(false, nom, desc);
                    categoriaService.crear(nueva);
                    System.out.println("Categoría registrada con éxito.");
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
        } catch (Exception e) {
            System.out.println("Error en Categorías: " + e.getMessage());
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
                            System.out.println(p.toString())
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
                    System.out.println("Producto creado exitosamente.");
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
        } catch (Exception e) {
            System.out.println("Error en Productos: " + e.getMessage());
        }
    }

    // =========================================================================
    // 4. MENÚ - GESTIÓN DE PEDIDOS (Historias de Usuario del Backlog)
    // =========================================================================
    private static void menuPedidos() {
        System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
        System.out.println("1. Listar Pedidos (HU-PED-01)");
        System.out.println("2. Crear Pedido Completo con Detalles (HU-PED-02)");
        System.out.println("3. Actualizar Estado/Forma de Pago (HU-PED-03)");
        System.out.println("4. Eliminar Pedido (HU-PED-04)");
        System.out.print("Seleccione una opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1 -> {
                    System.out.println("\n--- Listado Histórico de Pedidos ---");
                    pedidoService.listar().forEach(p ->
                            System.out.println(p.toString())
                    );
                }
                case 2 -> {
                    System.out.println("\n--- Alta de Nuevo Pedido Transaccional ---");
                    System.out.print("Ingrese ID del Usuario/Cliente: ");
                    long userId = Long.parseLong(scanner.nextLine());
                    Usuario cliente = new Usuario();
                    cliente.setId(userId);

                    Pedido nuevoPedido = new Pedido();
                    nuevoPedido.setUsuario(cliente);

                    System.out.print("Seleccione Forma de Pago (1- TARJETA, 2- TRANSFERENCIA, 3- EFECTIVO): ");
                    int fp = Integer.parseInt(scanner.nextLine());
                    switch (fp) {
                        case 1 -> nuevoPedido.setFormaPago(FormaPago.TARJETA);
                        case 2 -> nuevoPedido.setFormaPago(FormaPago.TRANSFERENCIA);
                        default -> nuevoPedido.setFormaPago(FormaPago.EFECTIVO);
                    }

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
                    System.out.println("¡Pedido y líneas de detalle guardados con éxito en la Base de Datos!");
                }
                case 3 -> {
                    System.out.print("ID del Pedido a modificar: ");
                    Long id = Long.parseLong(scanner.nextLine());

                    System.out.println("Seleccione Nuevo Estado:");
                    System.out.println("1- PENDIENTE | 2- CONFIRMADO | 3- TERMINADO | 4- CANCELADO");
                    int estOp = Integer.parseInt(scanner.nextLine());
                    Estado nuevoEst = switch (estOp) {
                        case 2 -> Estado.CONFIRMADO;
                        case 3 -> Estado.TERMINADO;
                        case 4 -> Estado.CANCELADO;
                        default -> Estado.PENDIENTE;
                    };

                    pedidoService.cambiarEstado(id, nuevoEst);
                    System.out.println("El estado del pedido fue actualizado con éxito.");
                }
                case 4 -> {
                    System.out.print("ID del Pedido a eliminar (Baja lógica): ");
                    Long id = Long.parseLong(scanner.nextLine());
                    pedidoService.eliminar(id);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en Pedidos: " + e.getMessage());
        }
    }
}