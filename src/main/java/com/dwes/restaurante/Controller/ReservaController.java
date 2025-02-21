package com.dwes.restaurante.Controller;

import com.dwes.restaurante.DTO.ReservaDTO;
import com.dwes.restaurante.Entity.Mesa;
import com.dwes.restaurante.Entity.Reserva;
import com.dwes.restaurante.Repository.ReservaRepository;
import com.dwes.restaurante.Service.MesaService;
import com.dwes.restaurante.Service.ReservaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequestMapping("/reservas")
@RestController
@Tag(name = "Reserva", description = "Gestión de reservas") // Añade un nombre y descripción al controlador
public class ReservaController {
     @Autowired
     private ReservaService reservaService;
     @Autowired
     private ReservaRepository reservaRepository;


    /** Listar todas las reservas */
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        return ResponseEntity.ok(reservaRepository.findAll());
    }

    /** Crear una nueva reserva verificando disponibilidad */
    @PostMapping
    public ResponseEntity<?> createReserva(@RequestBody Reserva reserva) {
        // Verificar si la mesa ya está reservada en ese horario
        boolean disponible = reservaService.verificarDisponibilidad(reserva.getMesa().getId(), reserva.getFecha());

        if (!disponible) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La mesa ya está reservada para la fecha y hora seleccionadas.");
        }

        // Guardar la reserva si está disponible
        Reserva nuevaReserva = reservaRepository.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    /** Obtener reservas de un día específico en formato DTO */
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ReservaDTO>> getReservasPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<ReservaDTO> reservas = reservaService.getReservasPorDia(fecha);
        return ResponseEntity.ok(reservas);
    }

    /**borrar*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        reservaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/disponibles")
    public ResponseEntity<List<Mesa>> obtenerMesasDisponibles(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("hora") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora) {

        // Unimos fecha y hora en LocalDateTime
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

        System.out.println("Consultando mesas disponibles para: " + fechaHora); // 🛠 Debug

        // Llamamos al servicio con la fecha y hora combinadas
        List<Mesa> mesasDisponibles = reservaService.obtenerMesasDisponibles(fecha,hora);

        return ResponseEntity.ok(mesasDisponibles);
    }


}
