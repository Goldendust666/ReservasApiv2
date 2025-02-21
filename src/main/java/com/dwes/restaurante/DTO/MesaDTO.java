package com.dwes.restaurante.DTO;

public class MesaDTO {
    private Long id;
    private int numeroMesa;
    private String descripcion;

    public MesaDTO() {}

    public MesaDTO(Long id, int numeroMesa, String descripcion) {
        this.id = id;
        this.numeroMesa = numeroMesa;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

