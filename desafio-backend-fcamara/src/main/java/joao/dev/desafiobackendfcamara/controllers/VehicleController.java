package joao.dev.desafiobackendfcamara.controllers;

import jakarta.validation.Valid;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<?> createVehicle(@RequestBody @Valid VehicleDTO data) {
        try {
            return new ResponseEntity<>(vehicleService.createVehicle(data), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listVehicles() {
        return new ResponseEntity<>(vehicleService.list(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody @Valid VehicleDTO data) {
        try {
            return new ResponseEntity<>(vehicleService.updateVehicle(data), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicle(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(vehicleService.deleteVehicle(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }
}
