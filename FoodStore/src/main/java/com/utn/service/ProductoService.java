package com.utn.service;


import com.utn.entities.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> listar();
    void crear(Producto producto);
    void editar(Long id, Producto producto);
    void eliminar(Long id);
}
