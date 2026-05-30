package service;

import entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private long contadorId = 1;

    // Listar usuarios activos (eliminado == false)
    public List<Usuario> listarUsuariosActivos() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : listaUsuarios) {
            if (!u.isEliminado()) { // Método heredado de Base
                activos.add(u);
            }
        }
        return activos;
    }

    // Validar si el mail es único en el sistema
    public boolean existeMail(String mail) {
        for (Usuario u : listaUsuarios) {
            if (u.getMail().equalsIgnoreCase(mail)) {
                return true;
            }
        }
        return false;
    }

    // Guardar el usuario asignándole el ID autogestionado
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setId(contadorId++);
        listaUsuarios.add(usuario);
        return usuario;
    }

    // Buscar un usuario activo por ID
    public Usuario buscarPorId(Long id) {
        for (Usuario u : listaUsuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        return null; // Retorna null si no existe o fue dado de baja de forma lógica
    }
}