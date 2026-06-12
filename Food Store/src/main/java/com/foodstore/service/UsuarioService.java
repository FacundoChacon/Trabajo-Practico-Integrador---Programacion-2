package com.foodstore.service;


import com.foodstore.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();
    void crear(Usuario usuario, String mail);
    void editar(int id, Usuario usuario);
    void eliminar(int id);
}