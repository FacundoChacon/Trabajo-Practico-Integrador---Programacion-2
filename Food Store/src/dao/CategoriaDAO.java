package dao;

import entities.Categoria;
import java.util.List;

public class CategoriaDAO implements DAO <Categoria> {
    @Override
    public List<Categoria> listar() {
        return List.of();
    }

    @Override
    public void crear(Categoria categoria) {

    }

    @Override
    public void editar(long id) {

    }
    @Override
    public void eliminar(long id) {
    }

    @Override
    public Categoria buscarPorId(long id) {
        return null;
    }
    @Override
    public void actualizar(Categoria categoria) {
    }
}