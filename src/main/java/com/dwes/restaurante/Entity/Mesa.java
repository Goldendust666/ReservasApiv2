package com.dwes.restaurante.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UniqueElements
    private int numeroMesa;
    private String descripcion;
    @OneToMany(targetEntity = Reserva.class, mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @UniqueElements
    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(@UniqueElements int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
