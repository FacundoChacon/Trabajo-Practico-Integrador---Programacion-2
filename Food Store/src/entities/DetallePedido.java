package entities;

// Definicion de TODO: (TODO = to do (hacer))

public class DetallePedido extends Base{
    //TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters

    private int cantidad;
    private Double subtotal;
    private Producto producto;

    // Getters y Setters

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        this.cantidad = cantidad;
        this.subtotal = calcularMontoSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    // Constructores

    public DetallePedido(int cantidad, Producto producto) {
        super();
    }

    public DetallePedido(boolean eliminado, int cantidad, Producto producto) {
        super(eliminado);

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularMontoSubtotal();
    }

    private Double calcularMontoSubtotal() {
        if (this.producto != null) {
            return this.cantidad * this.producto.getPrecio();
        }
        return 0.0;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.subtotal = calcularMontoSubtotal(); // Recalcula si cambia el producto
    }

    @Override
    public String toString() {
        return String.format(
                "Detalle [ID: %d | Producto: %-15s | Cant: %d | Subtotal: $%.2f | Eliminado: %b]",
                this.id,
                (producto != null ? producto.getNombre() : "Sin Producto"),
                this.cantidad,
                this.subtotal,
                this.eliminado
        );
    }
}