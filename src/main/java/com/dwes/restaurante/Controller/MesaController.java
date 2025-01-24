package com.dwes.restaurante.Controller;

import com.dwes.restaurante.Entity.Mesa;
import com.dwes.restaurante.Repository.MesaRepository;
import com.dwes.restaurante.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MesaController {
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private MesaService mesaService;
       /**Listar todas las mesas*/
    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> GetAllMesas(){
        List<Mesa> mesas = mesaRepository.findAll();
        return ResponseEntity.ok(mesas);
    }
    /**insertar*/
    @PostMapping("/mesas")
    public ResponseEntity<Mesa> NewMesa(@RequestBody Mesa mesa) {
        Mesa saveMesa = mesaRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMesa);
    }
    /**Obtiene una mesa*/
    @GetMapping("/mesas/{id}")
    public ResponseEntity<Mesa> getMesa(@PathVariable Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la mesa con el id: " + id));
        return ResponseEntity.ok(mesa);
    }
    /**Modificar*/
    @PutMapping("/mesas/{id}")
    public ResponseEntity<Mesa> modifyMesa(@PathVariable Long id, @RequestBody Mesa mesa) {
        Mesa existingMesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la mesa con el id: " + id +"por lo que no se pudo modifficar."));

        existingMesa.setNumeroMesa(mesa.getNumeroMesa());
        existingMesa.setDescripcion(mesa.getDescripcion());


        Mesa updatedMesa = mesaRepository.save(existingMesa);

        return ResponseEntity.ok(updatedMesa);
    }
    /**borrar*/
    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        mesaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
