package dao;

import entities.Usuario;

import java.util.List;

public class UsuarioDAO implements DAO <Usuario>{
    @Override
    public List<Usuario> listar() {
        return List.of();
    }

    @Override
    public void crear(Usuario entidad) {

    }

    @Override
    public void editar(int id) {

    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public Usuario buscarPorId(int id) {
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {

    }


}
