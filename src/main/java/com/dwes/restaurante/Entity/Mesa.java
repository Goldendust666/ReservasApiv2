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
}
