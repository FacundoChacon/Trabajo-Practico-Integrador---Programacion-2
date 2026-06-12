package service;

import dao.ProductoDAO;
import entities.Producto;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    public List<Producto> listar() {
        List<Producto> activos = productoDAO.listar();
        if (activos.isEmpty()) {
            throw new IllegalArgumentException("No existen productos activos en el sistema.");
        }
        return activos;
    }

    @Override
    public void crear(Producto p) {
        if (p.getNombre() == null || p.getNombre().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El nombre del producto es mandatorio.");
        }
        if (p.getPrecio() == null || p.getPrecio() <= 0) {
            throw new IllegalArgumentException("NEGOCIO: El precio debe ser un valor positivo mayor a cero.");
        }
        if (p.getStock() < 0) {
            throw new IllegalArgumentException("NEGOCIO: El stock disponible inicial no puede ser negativo.");
        }
        if (p.getCategoria() == null) {
            throw new IllegalArgumentException("NEGOCIO: Todo producto debe estar estrictamente vinculado a una Categoría.");
        }
        productoDAO.crear(p);
    }

    @Override
    public void editar(Long id, Producto nuevosDatos) {
        Producto prodExistente = productoDAO.buscarPorId(id);
        if (prodExistente == null) {
            throw new IllegalArgumentException("NEGOCIO: El producto especificado no fue localizado.");
        }
        if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) prodExistente.setNombre(nuevosDatos.getNombre());
        if (nuevosDatos.getPrecio() != null && nuevosDatos.getPrecio() > 0) prodExistente.setPrecio(nuevosDatos.getPrecio());
        if (nuevosDatos.getStock() >= 0) prodExistente.setStock(nuevosDatos.getStock());
        if (nuevosDatos.getDescripcion() != null) prodExistente.setDescripcion(nuevosDatos.getDescripcion());

        productoDAO.actualizar(prodExistente);
    }

    @Override
    public void eliminar(Long id) {
        Producto prod = productoDAO.buscarPorId(id);
        if (prod == null) {
            throw new IllegalArgumentException("NEGOCIO: Producto inexistente.");
        }
        prod.setEliminado(true);
        productoDAO.actualizar(prod);
    }
}