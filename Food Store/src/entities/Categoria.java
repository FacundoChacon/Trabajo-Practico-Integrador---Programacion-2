package entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// Definicion de TODO: (TODO = to do (hacer))

public class Categoria extends Base{
    //TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters
    private String nombre;
    private String descripcion;

    //Relacion 1:N con Producto
    private List<Producto> productos = new ArrayList<>();

    //Constructor
    public Categoria(){
        super();
    }
    public Categoria(Long id, boolean eliminado, LocalDateTime createAT, String nombre, String descripcion){
        super(id, eliminado, createAT);

    }

    //getter and setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
