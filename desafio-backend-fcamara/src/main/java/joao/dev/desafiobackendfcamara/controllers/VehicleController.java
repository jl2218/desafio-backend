package joao.dev.desafiobackendfcamara.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Cria um veículo")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o veículo criado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/register")
    public ResponseEntity<?> createVehicle(@RequestBody @Valid VehicleDTO data) {
        Vehicle newVehicle = vehicleService.createVehicle(data);
        return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
    }

    @Operation(description = "Lista todos os veículos")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os veículos"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @GetMapping("/list")
    public ResponseEntity<?> listVehicles() {
        return new ResponseEntity<>(vehicleService.list(), HttpStatus.OK);
    }

    @Operation(description = "Atualiza um veículo")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o veículo atualizado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody @Valid VehicleDTO data) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(data);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.CREATED);
    }

    @Operation(description = "Apaga um veículo")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma String de sucesso"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicle(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(vehicleService.deleteVehicle(id), HttpStatus.OK);
    }
}
