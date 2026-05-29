package entities;

import java.time.LocalDateTime;

// Definicion de TODO: (TODO = to do (hacer))

public abstract class Base {
    protected Long id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    // CONSTRUCTORES
    public Base() {
    }
    public Base(Long id, boolean eliminado, LocalDateTime createdAt) {
        setId(id);
        setEliminado(eliminado);
        setCreatedAt(createdAt);
    }

    // METODOS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
