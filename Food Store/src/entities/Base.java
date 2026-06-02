package entities;

import java.time.LocalDateTime;

// Definicion de TODO: (TODO = to do (hacer))

public abstract class Base {
    protected Long id;
    protected static Long contador;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    // CONSTRUCTORES
    public Base() {
    }
    public Base(boolean eliminado) {
        contador ++;
        this.id = contador;
        setEliminado(eliminado);
        this.createdAt = LocalDateTime.now();
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
