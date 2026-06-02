package dao;

import entities.Usuario;

import java.util.List;

public interface DAO <T> {
    List<T> listar();
    void crear(T entidad);
    void editar(int id);
    void eliminar(int id);
    T buscarPorId(int id);
    void actualizar(T entidad);
}
