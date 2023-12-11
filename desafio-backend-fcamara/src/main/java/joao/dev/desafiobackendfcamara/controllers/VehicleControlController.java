package joao.dev.desafiobackendfcamara.controllers;

import joao.dev.desafiobackendfcamara.services.VehicleControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle/control")
public class VehicleControlController {

    @Autowired
    private VehicleControlService vehicleControlService;

    @PostMapping("/entry")
    public ResponseEntity<?> vehicleEntryControl(@RequestParam String establishmentDocument, @RequestParam String vehiclePlate) {
        try {
            return new ResponseEntity<>(vehicleControlService.vehicleEntryControl(establishmentDocument, vehiclePlate), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @PostMapping("/exit")
    public ResponseEntity<?> vehicleExitControl(@RequestParam String establishmentDocument, @RequestParam String vehiclePlate) {
        try {
            return new ResponseEntity<>(vehicleControlService.vehicleExitControl(establishmentDocument, vehiclePlate), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<?> summary(@RequestParam String establishmentDocument) {
        try {
            return new ResponseEntity<>(vehicleControlService.summary(establishmentDocument), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/summary/perHour")
    public ResponseEntity<?> summaryPerHour(@RequestParam String establishmentDocument) {
        try {
            return new ResponseEntity<>(vehicleControlService.summaryPerHour(establishmentDocument), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }
}
