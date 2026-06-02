package entities;

// Definicion de TODO: (TODO = to do (hacer))
//TODO: escribir los atributos de la clase, usar super en el constructor para pasarle los parametros a base y NO usar smart setters
import enums.Rol;

import java.time.LocalDateTime;

public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    private Rol rol;


    //  CONSTRUCTORES
    public Usuario() {
        super();
    }

    public Usuario(boolean eliminado, String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        super(eliminado);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    //  GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", celular='" + celular + '\'' +
                ", rol=" + rol +
                ", eliminado=" + isEliminado() +
                ", createdAt=" + getCreatedAt() +
                '}';
    }

}
