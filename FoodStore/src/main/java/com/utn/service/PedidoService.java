package com.utn.service;



import com.utn.entities.Pedido;
import com.utn.enums.Estado;

import java.util.List;

public interface PedidoService {
    List<Pedido> listar();
    void crear(Pedido pedido);
    void cambiarEstado(Long id, Estado nuevoEstado);
    void eliminar(Long id);
}
