package com.dwes.restaurante.Service;

import com.dwes.restaurante.DTO.MesaDTO;
import com.dwes.restaurante.DTO.ReservaDTO;
import com.dwes.restaurante.Entity.Mesa;
import com.dwes.restaurante.Entity.Reserva;
import com.dwes.restaurante.Repository.MesaRepository;
import com.dwes.restaurante.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private final MesaRepository mesaRepository;

    public ReservaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    /** Verifica si una mesa está disponible en una fecha y hora específicas */
    public boolean verificarDisponibilidad(Long mesaId, LocalDateTime fecha) {
        return !reservaRepository.existsByMesaIdAndFecha(mesaId, fecha);
    }

    public List<ReservaDTO> getReservasPorDia(LocalDate fecha) {
        // Convertimos LocalDate a LocalDateTime al inicio del día
        LocalDateTime inicioDia = fecha.atStartOfDay();

        // Obtenemos las reservas de la base de datos
        List<Reserva> reservas = reservaRepository.findByFecha(inicioDia);

        // Convertimos las reservas a DTOs
        return reservas.stream()
                .map(r -> new ReservaDTO(
                        r.getCliente().getNombre(),
                        r.getCliente().getEmail(),
                        r.getFecha(),
                        r.getMesa().getNumeroMesa(),
                        r.getNumeroPersonas()
                ))
                .collect(Collectors.toList());
    }
    public List<MesaDTO> obtenerMesasDisponibles(String fecha, String hora, int comensales) {
        LocalDateTime fechaHoraInicio = LocalDateTime.parse(fecha + "T" + hora);
        LocalDateTime fechaHoraFin = fechaHoraInicio.plusHours(1); // Suponiendo duración de 1h por reserva

        List<Mesa> todasLasMesas = mesaRepository.findAll();

        // Buscar reservas en ese rango de tiempo
        List<Mesa> mesasOcupadas = reservaRepository.findByFechaBetween(fechaHoraInicio, fechaHoraFin)
                .stream()
                .map(Reserva::getMesa)
                .collect(Collectors.toList());

        // Filtrar mesas disponibles con capacidad suficiente
        return todasLasMesas.stream()
                .filter(mesa -> !mesasOcupadas.contains(mesa))
                .map(mesa -> new MesaDTO(mesa.getId(), mesa.getNumeroMesa(), mesa.getDescripcion()))
                .collect(Collectors.toList());
    }
}
