package entities;
import java.time.LocalDateTime;
// Definicion de TODO: (TODO = to do (hacer))

public class Producto extends Base{
    //TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters
    private String nombre;
    private Double precio;
    private String Descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria; //relacion N:1 con Categoria

    //  CONSTRUCTORES
    public Producto() {
        super();
    }
    public Producto(boolean eliminado, String nombne, Double precio, String descripcion, int stock, String imagen, Categoria categoria) {
        super(eliminado);
        setNombre(nombne);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setCategoria(categoria);
    }

    //  GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
