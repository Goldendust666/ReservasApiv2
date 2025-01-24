package com.dwes.restaurante.Controller;

import com.dwes.restaurante.Entity.Cliente;
import com.dwes.restaurante.Repository.ClienteRepository;
import com.dwes.restaurante.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {
@Autowired
private ClienteService clienteService;
@Autowired
private ClienteRepository clienteRepository;
    /**Listar todos los clientes*/
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> GetAllClientes(){
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }
    /**insertar*/
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> NewEmpleado(@RequestBody Cliente cliente) {
        Cliente saveClient = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
    }
    /**Obtiene un cliente*/
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el cliente con el id: " + id));
        return ResponseEntity.ok(cliente);
    }
    /**Modificar*/
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el cliente con el id: " + id +"por lo que no se pudo modifficar."));

        existingCliente.setNombre((cliente.getNombre()));
        existingCliente.setTelefono((cliente.getTelefono()));
        existingCliente.setEmail(cliente.getEmail());

        Cliente updatedCliente = clienteRepository.save(existingCliente);

        return ResponseEntity.ok(updatedCliente);
    }
    /**borrar*/
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
