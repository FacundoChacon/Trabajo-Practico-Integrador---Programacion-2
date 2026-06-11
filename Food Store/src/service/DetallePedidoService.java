package service;

import entities.Pedido;
import entities.Producto;
import entities.DetallePedido;
import java.util.List;

public interface DetallePedidoService {
    List<DetallePedido> listarPorPedido(Pedido pedido);
    void agregarDetalle(Pedido pedido, Producto producto, int cantidad);
    void eliminarDetalle(Pedido pedido, Producto producto);
}