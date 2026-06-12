package service;

import dao.UsuarioDAO;
import entities.Usuario;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public List<Usuario> listar() {
        List<Usuario> listaCompleta = usuarioDAO.listar();

        if (listaCompleta == null || listaCompleta.isEmpty()) {
            throw new IllegalArgumentException("No hay usuarios registrados en el sistema.");
        }
        List<Usuario> activos = listaCompleta.stream()
                .filter(u -> !u.isEliminado())
                .toList();

        if (activos.isEmpty()) {
            throw new IllegalArgumentException("No hay usuarios activos para mostrar.");
        }

        return activos;
    }

    @Override
    public void crear(Usuario u, String mail) {
        if (u.getNombre() == null || u.getNombre().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El nombre del usuario es obligatorio.");
        }
        if (u.getApellido() == null || u.getApellido().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El apellido del usuario es obligatorio.");
        }
        if (u.getMail() == null || u.getMail().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El mail del usuario es obligatorio.");
        } else {
            if (validarMailUnico(mail)) {
                throw new RuntimeException("NEGOCIO: El mail del usuario ya existe y debe ser único.");
            }
        }
        if (u.getCelular() == null || u.getCelular().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: El celular del usuario es obligatorio.");
        }
        if (u.getContraseña() == null || u.getContraseña().trim().isBlank()) {
            throw new IllegalArgumentException("NEGOCIO: La contraseña es obligatoria.");
        }

        usuarioDAO.crear(u);
    }

    @Override
    public void editar(int id, Usuario nuevosDatos) {
        Usuario usuarioExistente = usuarioDAO.buscarPorId(id);

        if (usuarioExistente == null || usuarioExistente.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: Usuario no encontrado o ya fue eliminado.");
        }
        if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) {
            usuarioExistente.setNombre(nuevosDatos.getNombre());
        }
        if (nuevosDatos.getApellido() != null && !nuevosDatos.getApellido().isBlank()) {
            usuarioExistente.setApellido(nuevosDatos.getApellido());
        }
        usuarioDAO.actualizar(usuarioExistente);
    }

    @Override
    public void eliminar(int id) {
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null || usuario.isEliminado()) {
            throw new IllegalArgumentException("NEGOCIO: No se encontró un usuario activo con el ID: " + id);
        }
        usuario.setEliminado(true);
        usuarioDAO.actualizar(usuario);
    }

    private boolean validarMailUnico(String mail) {
        for (Usuario existente : usuarioDAO.listar()) {
            if (existente.getMail().equalsIgnoreCase(mail) && !existente.isEliminado()) {
                return true;
            }
        }
        return false;
    }
}