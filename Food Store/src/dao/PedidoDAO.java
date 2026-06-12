package dao;

import entities.Pedido;
import entities.DetallePedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements DAO<Pedido> {

    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre, u.apellido FROM pedidos p INNER JOIN usuarios u ON p.usuario_id = u.id WHERE p.eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido ped = new Pedido();
                ped.setId(rs.getLong("id"));
                ped.setFecha(rs.getDate("fecha").toLocalDate());
                ped.setEstado(Estado.valueOf(rs.getString("estado")));
                ped.setTotal(rs.getDouble("total"));
                ped.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));

                Usuario u = new Usuario();
                u.setId(rs.getLong("usuario_id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                ped.setUsuario(u);

                pedidos.add(ped);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public void crear(Pedido p) {
        String insertPedido = "INSERT INTO pedidos (fecha, estado, total, forma_pago, usuario_id, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertDetalle = "INSERT INTO detalles_pedido (cantidad, subtotal, producto_id, pedido_id, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); // Apertura de bloque transaccional seguro

            // 1. Guardar la Cabecera del Pedido
            try (PreparedStatement psPed = conn.prepareStatement(insertPedido, Statement.RETURN_GENERATED_KEYS)) {
                psPed.setDate(1, Date.valueOf(p.getFecha()));
                psPed.setString(2, p.getEstado().name());
                psPed.setDouble(3, p.getTotal());
                psPed.setString(4, p.getFormaPago().name());
                psPed.setLong(5, p.getUsuario().getId());
                psPed.setBoolean(6, p.isEliminado());
                psPed.setTimestamp(7, Timestamp.valueOf(p.getCreatedAt()));
                psPed.executeUpdate();

                try (ResultSet gk = psPed.getGeneratedKeys()) {
                    if (gk.next()) p.setId(gk.getLong(1));
                }
            }

            // 2. Guardar cada fila de Detalle asociándolo al ID generado
            try (PreparedStatement psDet = conn.prepareStatement(insertDetalle)) {
                for (DetallePedido det : p.getDetallesPedido()) {
                    psDet.setInt(1, det.getCantidad());
                    psDet.setDouble(2, det.getSubtotal());
                    psDet.setLong(3, det.getProducto().getId());
                    psDet.setLong(4, p.getId());
                    psDet.setBoolean(5, det.isEliminado());
                    psDet.setTimestamp(6, Timestamp.valueOf(det.getCreatedAt()));
                    psDet.addBatch();
                }
                psDet.executeBatch();
            }

            conn.commit(); // Éxito completo en la operación
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            throw new RuntimeException("Error crítico en la persistencia del pedido. Transacción revertida.");
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    @Override
    public void actualizar(Pedido p) {
        String sql = "UPDATE pedidos SET estado = ?, forma_pago = ?, eliminado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getEstado().name());
            ps.setString(2, p.getFormaPago().name());
            ps.setBoolean(3, p.isEliminado());
            ps.setLong(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override public void editar(long id) {}
    @Override public void eliminar(long id) {}

    @Override
    public Pedido buscarPorId(long id) {
        String sql = "SELECT * FROM pedidos WHERE id = ? AND eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido p = new Pedido();
                    p.setId(rs.getLong("id"));
                    p.setFecha(rs.getDate("fecha").toLocalDate());
                    p.setEstado(Estado.valueOf(rs.getString("estado")));
                    p.setTotal(rs.getDouble("total"));
                    p.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}