package entities;
import java.time.LocalDateTime;
// Definicion de TODO: (TODO = to do (hacer))

public class Producto extends Base{
    //TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria; //relacion N:1 con Categoria

    //  CONSTRUCTORES
    public Producto() {
        super();
    }
    public Producto(boolean eliminado, String nombre, Double precio, String descripcion, int stock, String imagen,boolean disponible, Categoria categoria) {
        super(eliminado);
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
        setCategoria(categoria);
    }

    //  GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {return precio;}
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        descripcion = descripcion;
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
    public void setDisponible(boolean disponible) {this.disponible = disponible;}

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
