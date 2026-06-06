package dao;

import entities.Producto;
import java.util.List;

public class ProductoDAO implements DAO<Producto> {

    @Override
    public List<Producto> listar() {
        return List.of();
    }

    @Override
    public void crear(Producto entidad) {

    }

    @Override
    public void editar(Long id) {

    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public Producto buscarPorId(Long id) {
        return null;
    }

    @Override
    public void actualizar(Producto entidad) {

    }
}