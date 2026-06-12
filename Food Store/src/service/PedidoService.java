package service;

import entities.Pedido;

import java.util.List;

public interface PedidoService {
    List<Pedido> listar();
    void crear(Pedido pedido);
    void cambiarEstado(Long id, enums.Estado nuevoEstado);
    void eliminar(Long id);
}
