package com.dwes.restaurante.Repository;

import com.dwes.restaurante.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    boolean existsByMesaIdAndFecha(Long mesaId, LocalDateTime fecha);

    @Query("SELECT r FROM Reserva r WHERE DATE(r.fecha) = DATE(:fecha)")
    List<Reserva> findByFecha(LocalDateTime fecha);
}
