package joao.dev.desafiobackendfcamara.controllers;

import jakarta.validation.Valid;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
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
        Vehicle newVehicle = vehicleService.createVehicle(data);
        return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listVehicles() {
        return new ResponseEntity<>(vehicleService.list(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody @Valid VehicleDTO data) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(data);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicle(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(vehicleService.deleteVehicle(id), HttpStatus.OK);
    }
}
