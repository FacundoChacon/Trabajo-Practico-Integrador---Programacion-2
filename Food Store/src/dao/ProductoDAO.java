package dao;

import entities.Categoria;
import entities.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements DAO<Producto> {

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as cat_nombre, c.descripcion as cat_desc " +
                "FROM productos p INNER JOIN categorias c ON p.categoria_id = c.id WHERE p.eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setImagen(rs.getString("imagen"));
                p.setDisponible(rs.getBoolean("disponible"));
                p.setEliminado(rs.getBoolean("eliminado"));

                Categoria cat = new Categoria();
                cat.setId(rs.getLong("categoria_id"));
                cat.setNombre(rs.getString("cat_nombre"));
                cat.setDescripcion(rs.getString("cat_desc"));
                p.setCategoria(cat);

                productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public void crear(Producto p) {
        String sql = "INSERT INTO productos (nombre, precio, descripcion, stock, imagen, disponible, categoria_id, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.isDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setBoolean(8, p.isEliminado());
            ps.setTimestamp(9, Timestamp.valueOf(p.getCreatedAt()));
            ps.executeUpdate();
            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (gk.next()) p.setId(gk.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Producto p) {
        String sql = "UPDATE productos SET nombre=?, precio=?, descripcion=?, stock=?, imagen=?, disponible=?, categoria_id=?, eliminado=? WHERE id=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.isDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setBoolean(8, p.isEliminado());
            ps.setLong(9, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override public void editar(long id) {}
    @Override public void eliminar(long id) {}

    @Override
    public Producto buscarPorId(long id) {
        String sql = "SELECT p.*, c.nombre as cat_nombre, c.descripcion as cat_desc " +
                "FROM productos p INNER JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ? AND p.eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getLong("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setStock(rs.getInt("stock"));
                    p.setImagen(rs.getString("imagen"));
                    p.setDisponible(rs.getBoolean("disponible"));

                    Categoria cat = new Categoria();
                    cat.setId(rs.getLong("categoria_id"));
                    cat.setNombre(rs.getString("cat_nombre"));
                    cat.setDescripcion(rs.getString("cat_desc"));
                    p.setCategoria(cat);
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}