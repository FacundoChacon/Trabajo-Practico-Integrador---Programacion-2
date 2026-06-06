package service;

import dao.ProductoDAO;
import entities.Producto;

import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    public List<Producto> listar() {
        List<Producto> listaCompleta = productoDAO.listar();

        if (listaCompleta == null || listaCompleta.isEmpty()) {
            throw new IllegalArgumentException("No hay productos registrados en el sistema.");
        }
        List<Producto> activos = listaCompleta.stream()
                .filter(p -> !p.isEliminado())
                .toList();

        if (activos.isEmpty()) {
            throw new IllegalArgumentException("No hay productos activos para mostrar.");
        }

        return activos;
    }

    @Override
    public void crear(Producto p) {
        // Validaciones
        if (p.getNombre() == null || p.getNombre().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El nombre del producto es obligatorio.");
        }
        if (p.getPrecio() == null || p.getPrecio() <= 0) {
            throw new IllegalArgumentException("NEGOCIO: El precio debe ser mayor a 0.");
        }
        if (p.getStock() < 0) {
            throw new IllegalArgumentException("NEGOCIO: El stock no puede ser negativo.");
        }
        if (p.getCategoria() == null) {
            throw new IllegalArgumentException("NEGOCIO: El producto debe tener una categoría asignada.");
        }

        // Si pasa todas las validaciones, lo mandamos al DAO
        productoDAO.crear(p);
        System.out.println("Producto creado exitosamente con ID: " + p.getId());
    }

    @Override
    public void editar(Long id, Producto nuevosDatos) {
        Producto productoExistente = productoDAO.buscarPorId(id);

        if (productoExistente == null || productoExistente.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: Producto no encontrado o ya eliminado.");
        }

        // Actualizamos solo los campos que vengan con datos válidos
        if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) {
            productoExistente.setNombre(nuevosDatos.getNombre());
        }
        if (nuevosDatos.getPrecio() != null && nuevosDatos.getPrecio() > 0) {
            productoExistente.setPrecio(nuevosDatos.getPrecio());
        }
        if (nuevosDatos.getDescripcion() != null && !nuevosDatos.getDescripcion().isBlank()) {
            productoExistente.setDescripcion(nuevosDatos.getDescripcion());
        }
        if (nuevosDatos.getStock() >= 0) { // Permitimos stock 0, pero no negativo
            productoExistente.setStock(nuevosDatos.getStock());
        }
        if (nuevosDatos.getImagen() != null && !nuevosDatos.getImagen().isBlank()) {
            productoExistente.setImagen(nuevosDatos.getImagen());
        }
        if (nuevosDatos.getCategoria() != null) {
            productoExistente.setCategoria(nuevosDatos.getCategoria());
        }

        // Actualizamos el estado de disponibilidad
        productoExistente.setDisponible(nuevosDatos.isDisponible());

        productoDAO.actualizar(productoExistente);
        System.out.println("Producto actualizado correctamente.");
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoDAO.buscarPorId(id);

        if (producto == null || producto.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: No se encontró un producto activo con el ID: " + id);
        }

        // Aplicamos el borrado lógico
        producto.setEliminado(true);

        productoDAO.actualizar(producto);
        System.out.println("Producto eliminado exitosamente.");
    }
}