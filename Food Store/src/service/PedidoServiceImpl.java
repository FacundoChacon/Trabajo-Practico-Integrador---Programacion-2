package service;

import dao.PedidoDAO;
import dao.ProductoDAO;
import entities.Pedido;
import entities.DetallePedido;
import entities.Producto;
import enums.Estado;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    public List<Pedido> listar() {
        List<Pedido> lista = pedidoDAO.listar();
        if (lista.isEmpty()) {
            throw new IllegalArgumentException("No se encuentran registros de pedidos activos.");
        }
        return lista;
    }

    @Override
    public void crear(Pedido p) {
        if (p.getUsuario() == null) {
            throw new IllegalArgumentException("NEGOCIO: No se puede generar un pedido sin un usuario/cliente asociado.");
        }
        if (p.getDetallesPedido() == null || p.getDetallesPedido().isEmpty()) {
            throw new IllegalArgumentException("NEGOCIO: El pedido debe contener al menos un producto en sus líneas de detalle.");
        }

        for (DetallePedido detalle : p.getDetallesPedido()) {
            Producto prodStock = productoDAO.buscarPorId(detalle.getProducto().getId());
            if (prodStock == null || !prodStock.isDisponible()) {
                throw new RuntimeException("NEGOCIO: El producto '" + detalle.getProducto().getNombre() + "' ya no está a la venta.");
            }
            if (prodStock.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("NEGOCIO: Stock insuficiente para " + prodStock.getNombre() + ". Disponibles: " + prodStock.getStock());
            }
            prodStock.setStock(prodStock.getStock() - detalle.getCantidad());
            productoDAO.actualizar(prodStock);
        }
        p.calcularTotal();
        pedidoDAO.crear(p);
    }

    @Override
    public void cambiarEstado(Long id, Estado nuevoEstado) {
        Pedido p = pedidoDAO.buscarPorId(id);
        if (p == null) {
            throw new IllegalArgumentException("NEGOCIO: Pedido no encontrado.");
        }
        p.setEstado(nuevoEstado);
        pedidoDAO.actualizar(p);
    }

    @Override
    public void eliminar(Long id) {
        Pedido p = pedidoDAO.buscarPorId(id);
        if (p == null) {
            throw new IllegalArgumentException("NEGOCIO: Registro de pedido no localizado.");
        }
        p.setEliminado(true);
        pedidoDAO.actualizar(p);
    }
}