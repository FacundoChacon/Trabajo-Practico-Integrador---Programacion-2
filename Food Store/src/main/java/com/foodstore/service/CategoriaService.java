package com.foodstore.service;

import com.foodstore.entities.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> listar();
    void crear(Categoria categoria);
    void editar(Long id, Categoria categoria);
    void eliminar(Long id);
}

