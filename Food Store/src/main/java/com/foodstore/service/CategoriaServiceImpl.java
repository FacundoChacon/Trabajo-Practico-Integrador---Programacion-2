package com.foodstore.service;


import com.foodstore.dao.CategoriaDAO;
import com.foodstore.entities.Categoria;
import com.foodstore.exceptions.NegocioException;

import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public List<Categoria> listar() {
        List<Categoria> activas = categoriaDAO.listar();
        if (activas.isEmpty()) {
            throw new IllegalArgumentException("No hay categorías activas registradas.");
        }
        return activas;
    }

    @Override
    public void crear(Categoria c) {
        if (c.getNombre() == null || c.getNombre().trim().isBlank()) {
            throw new NegocioException("NEGOCIO: El nombre de la categoría es obligatorio.");
        }
        if (categoriaDAO.existeNombre(c.getNombre())) {
            throw new NegocioException("NEGOCIO: El nombre de la categoría ya existe y debe ser único.");
        }
        categoriaDAO.crear(c);
    }

    @Override
    public void editar(Long id, Categoria nuevosDatos) {
        Categoria catExistente = categoriaDAO.buscarPorId(id);
        if (catExistente == null) {
            throw new NegocioException("NEGOCIO: Categoría no encontrada.");
        }
        if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) {
            if (!catExistente.getNombre().equalsIgnoreCase(nuevosDatos.getNombre()) && categoriaDAO.existeNombre(nuevosDatos.getNombre())) {
                throw new NegocioException("NEGOCIO: Ya existe otra categoría activa con ese nombre.");
            }
            catExistente.setNombre(nuevosDatos.getNombre());
        }
        if (nuevosDatos.getDescripcion() != null) {
            catExistente.setDescripcion(nuevosDatos.getDescripcion());
        }
        categoriaDAO.actualizar(catExistente);
    }

    @Override
    public void eliminar(Long id) {
        Categoria cat = categoriaDAO.buscarPorId(id);
        if (cat == null) {
            throw new NegocioException("NEGOCIO: Categoría no encontrada.");
        }
        cat.setEliminado(true);
        categoriaDAO.actualizar(cat);
    }
}