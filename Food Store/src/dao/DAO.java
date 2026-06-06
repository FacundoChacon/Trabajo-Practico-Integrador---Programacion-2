package dao;

import entities.Usuario;

import java.util.List;

public interface DAO <T> {
    List<T> listar();
    void crear(T entidad);
    void editar(long id);
    void eliminar(long id);
    T buscarPorId(long id);
    void actualizar(T entidad);
}
