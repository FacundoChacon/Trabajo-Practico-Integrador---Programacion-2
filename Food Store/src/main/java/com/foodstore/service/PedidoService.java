package com.foodstore.service;


import com.foodstore.entities.Pedido;
import com.foodstore.enums.Estado;

import java.util.List;

public interface PedidoService {
    List<Pedido> listar();
    void crear(Pedido pedido);
    void cambiarEstado(Long id, Estado nuevoEstado);
    void eliminar(Long id);
}
