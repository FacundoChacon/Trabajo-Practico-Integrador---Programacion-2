package entities;

import dao.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Estado;
import enums.FormaPago;
import dao.Calculable;

// Definicion de TODO: (TODO = to do (hacer))

public class Pedido extends Base implements Calculable {
    //TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario; // Relación con Usuario según UML
    private List<DetallePedido> detallesPedido; // Relación 1..m con DetallePedido

    // CONSTRUCTORES
    public Pedido() {
        super();
        this.detallesPedido = new ArrayList<>();
        this.total = 0.0;
    }

    public Pedido(Usuario usuario, FormaPago formaPago) {
        super(false); // Gestiona ID y createdAt en Base
        if (usuario == null) {
            throw new IllegalArgumentException("No se permite crear un Pedido sin usuario."); //
        }
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE; // Estado inicial por defecto
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detallesPedido = new ArrayList<>();
        this.total = 0.0;
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        // La validación de cantidad > 0 ya se realiza en el constructor de DetallePedido
        DetallePedido nuevoDetalle = new DetallePedido(cantidad, producto);
        this.detallesPedido.add(nuevoDetalle);
        calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        return detallesPedido.stream()
                .filter(d -> d.getProducto().equals(producto))
                .findFirst()
                .orElse(null);
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);
        if (detalle != null) {
            detallesPedido.remove(detalle);
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        //TODO: Modificar el metodo segun lo que pide la rubrica
        this.total = detallesPedido.stream()
                .filter(d -> !d.isEliminado())
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
        }
    }
