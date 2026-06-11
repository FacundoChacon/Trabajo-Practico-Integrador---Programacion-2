package service;

import entities.Pedido;
import entities.Producto;
import entities.DetallePedido;
import java.util.List;

public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Override
    public List<DetallePedido> listarPorPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        if (pedido.isEliminado()) {
            throw new IllegalArgumentException("El pedido se encuentra eliminado lógicamente.");
        }

        List<DetallePedido> listaDetalles = pedido.getDetallesPedido();

        if (listaDetalles == null || listaDetalles.isEmpty()) {
            throw new IllegalArgumentException("No hay detalles registrados para este pedido.");
        }

        List<DetallePedido> activos = listaDetalles.stream()
                .filter(d -> !d.isEliminado())
                .toList();

        if (activos.isEmpty()) {
            throw new IllegalArgumentException("No hay detalles activos para mostrar.");
        }

        return activos;
    }

    @Override
    public void agregarDetalle(Pedido pedido, Producto producto, int cantidad) {
        // --- VALIDACIONES DE CAPA DE SERVICIO ---
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido de destino es obligatorio.");
        }
        if (producto == null) {
            throw new IllegalArgumentException("El producto a agregar es obligatorio.");
        }
        if (pedido.isEliminado()) {
            throw new IllegalArgumentException("No se pueden añadir elementos a un pedido eliminado.");
        }

        if (!producto.isDisponible()) {
            throw new RuntimeException("El producto '" + producto.getNombre() + "' no está disponible.");
        }
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para '" + producto.getNombre() +
                    "'. Stock actual: " + producto.getStock() + ", Solicitado: " + cantidad);
        }

        try {
            pedido.addDetallePedido(cantidad, producto);

            producto.setStock(producto.getStock() - cantidad);

            System.out.println("Detalle agregado con éxito: " + producto.getNombre() + " x" + cantidad);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("NEGOCIO: Error al crear el detalle. " + e.getMessage());
        }
    }

    @Override
    public void eliminarDetalle(Pedido pedido, Producto producto) {
        if (pedido == null || producto == null) {
            throw new IllegalArgumentException("NEGOCIO: El pedido y el producto son obligatorios.");
        }

        DetallePedido detalleExistente = pedido.findeDetallePedidoByProducto(producto);

        if (detalleExistente == null || detalleExistente.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: El producto no se encuentra en el detalle de este pedido.");
        }

        // Guardamos la cantidad antes de borrar para poder restaurar el stock del producto
        int cantidadARestituir = detalleExistente.getCantidad();

        pedido.deleteDetallePedidoByProducto(producto);

        producto.setStock(producto.getStock() + cantidadARestituir);

        System.out.println("Detalle eliminado exitosamente. Se devolvieron " + cantidadARestituir + " unidades al stock.");
    }
}