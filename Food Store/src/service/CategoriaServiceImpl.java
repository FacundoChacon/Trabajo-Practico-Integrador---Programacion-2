package service;

import dao.CategoriaDAO;
import entities.Categoria;

import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public List<Categoria> listar() {
        List<Categoria> listaCompleta = categoriaDAO.listar();

        if (listaCompleta == null || listaCompleta.isEmpty()) {
            throw new IllegalArgumentException("No hay categorías registradas en el sistema.");
        }

        List<Categoria> activas = listaCompleta.stream()
                .filter(c -> !c.isEliminado())
                .toList();

        if (activas.isEmpty()) {
            throw new IllegalArgumentException("No hay categorías activas para mostrar.");
        }

        return activas;
    }

    @Override
    public void crear(Categoria c) {
        if (c.getNombre() == null || c.getNombre().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El nombre de la categoría es obligatorio.");
        }

        if (validarNombreUnico(c.getNombre())) {
            throw new RuntimeException("NEGOCIO: El nombre de la categoría ya existe.");
        }

        categoriaDAO.crear(c);
        System.out.println("Categoría creada exitosamente con ID: " + c.getId());
    }

    @Override
    public void editar(Long id, Categoria nuevosDatos) {
        Categoria categoriaExistente = categoriaDAO.buscarPorId(id);

        if (categoriaExistente == null || categoriaExistente.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: Categoría no encontrada o ya eliminada.");
        }

        if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) {
            // Validamos nombre pertenezca a otra categoría
            if (!categoriaExistente.getNombre().equalsIgnoreCase(nuevosDatos.getNombre()) && validarNombreUnico(nuevosDatos.getNombre())) {
                throw new RuntimeException("NEGOCIO: Ya existe otra categoría con ese nombre.");
            }
            categoriaExistente.setNombre(nuevosDatos.getNombre());
        }
        if (nuevosDatos.getDescripcion() != null && !nuevosDatos.getDescripcion().isBlank()) {
            categoriaExistente.setDescripcion(nuevosDatos.getDescripcion());
        }

        categoriaDAO.actualizar(categoriaExistente);
        System.out.println("Categoría actualizada correctamente.");
    }

    @Override
    public void eliminar(Long id) {
        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null || categoria.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: No se encontró una categoría activa con el ID: " + id);
        }
        categoria.setEliminado(true);

        categoriaDAO.actualizar(categoria);
        System.out.println("Categoría eliminada exitosamente.");
    }


    // --- MÉTODOS DE VALIDACIÓN ---

    public boolean validarActivo(Categoria c) {
        return !c.isEliminado();
    }

    private boolean validarNombreUnico(String nombre) {
        for (Categoria existente : categoriaDAO.listar()) {
            if (existente.getNombre().equalsIgnoreCase(nombre) && !existente.isEliminado()) {
                return true;
            }
        }
        return false;
    }
}