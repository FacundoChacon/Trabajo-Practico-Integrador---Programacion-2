package entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// Definicion de TODO: (TODO = to do (hacer))

public class Categoria extends Base{
    //TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>();//Relacion 1:N con Producto

    //Constructor
    public Categoria(){
        super();
    }
    public Categoria(boolean eliminado, String nombre, String descripcion){
        super(eliminado);
        setNombre(nombre);
        setDescripcion(descripcion);
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
