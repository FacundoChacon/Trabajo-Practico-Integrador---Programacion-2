package com.utn.dao;



import com.utn.config.ConexionDB;
import com.utn.entities.Usuario;
import com.utn.enums.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setMail(rs.getString("mail"));
                u.setCelular(rs.getString("celular"));
                u.setContraseña(rs.getString("contrasenia"));
                u.setRol(Rol.valueOf(rs.getString("rol")));
                u.setEliminado(rs.getBoolean("eliminado"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    public boolean existeMail(String mail) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE mail = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void crear(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, apellido, mail, celular, contrasenia, rol, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getMail());
            ps.setString(4, u.getCelular());
            ps.setString(5, u.getContraseña());
            ps.setString(6, u.getRol().name());
            ps.setBoolean(7, u.isEliminado());
            ps.setTimestamp(8, Timestamp.valueOf(u.getCreatedAt()));
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    u.setId(generatedKeys.getLong(1)); // Asignación del ID real generado por BD
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, apellido=?, mail=?, celular=?, contrasenia=?, rol=?, eliminado=? WHERE id=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getMail());
            ps.setString(4, u.getCelular());
            ps.setString(5, u.getContraseña());
            ps.setString(6, u.getRol().name());
            ps.setBoolean(7, u.isEliminado());
            ps.setLong(8, u.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override public void editar(long id) {}
    @Override public void eliminar(long id) {}

    @Override
    public Usuario buscarPorId(long id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setMail(rs.getString("mail"));
                    u.setCelular(rs.getString("celular"));
                    u.setContraseña(rs.getString("contrasenia"));
                    u.setRol(Rol.valueOf(rs.getString("rol")));
                    u.setEliminado(rs.getBoolean("eliminado"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}