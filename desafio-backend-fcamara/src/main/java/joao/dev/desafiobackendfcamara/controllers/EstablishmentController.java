package joao.dev.desafiobackendfcamara.controllers;

import jakarta.validation.Valid;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
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
        Establishment newEstablishment = establishmentService.createEstablishment(data);
        return new ResponseEntity<>(newEstablishment, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listEstablishments() {
        return new ResponseEntity<>(establishmentService.list(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEstablishment(@RequestBody @Valid EstablishmentDTO data) {
        Establishment updatedEstablishment = establishmentService.updateEstablishment(data);
        return new ResponseEntity<>(updatedEstablishment, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEstablishment(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(establishmentService.deleteEstablishment(id), HttpStatus.OK);
    }
}
