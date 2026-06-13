package com.utn.service;

import com.utn.entities.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> listar();
    void crear(Categoria categoria);
    void editar(Long id, Categoria categoria);
    void eliminar(Long id);
}

