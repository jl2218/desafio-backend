package joao.dev.desafiobackendfcamara.controllers;

import jakarta.validation.Valid;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.services.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/establishment")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @PostMapping("/register")
    public ResponseEntity<?> createEstablishment(@RequestBody @Valid EstablishmentDTO data) {
        try {
            return new ResponseEntity<>(establishmentService.createEstablishment(data), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listEstablishments() {
        return new ResponseEntity<>(establishmentService.list(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEstablishment(@RequestBody @Valid EstablishmentDTO data) {
        try {
            return new ResponseEntity<>(establishmentService.updateEstablishment(data), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEstablishment(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(establishmentService.deleteEstablishment(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }
}
