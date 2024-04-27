package joao.dev.desafiobackendfcamara.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Realiza uma entrada de um veículo no estacionamento selecionado")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o estabelecimento com a lista de veículos" +
                    " estacionados atualizada"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/entry")
    public ResponseEntity<?> vehicleEntryControl(@RequestParam String establishmentDocument, @RequestParam String vehiclePlate) {
        return new ResponseEntity<>(vehicleControlService.vehicleEntryControl(establishmentDocument, vehiclePlate), HttpStatus.OK);
    }

    @Operation(description = "Realiza uma saída de um veículo no estacionamento selecionado")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o estabelecimento com a lista de veículos" +
                    " estacionados atualizada"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/exit")
    public ResponseEntity<?> vehicleExitControl(@RequestParam String establishmentDocument, @RequestParam String vehiclePlate) {
        return new ResponseEntity<>(vehicleControlService.vehicleExitControl(establishmentDocument, vehiclePlate), HttpStatus.OK);
    }

    @Operation(description = "Sumário da quantidade total de entradas e saídas no estacionamento selecionado")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma String com o sumário"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @GetMapping("/summary")
    public ResponseEntity<?> summary(@RequestParam String establishmentDocument) {
        return new ResponseEntity<>(vehicleControlService.summary(establishmentDocument), HttpStatus.OK);
    }

    @Operation(description = "Sumário da quantidade de entradas e saídas no período de 1 hora" +
            " no estacionamento selecionado")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma String com o sumário"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @GetMapping("/summary/perHour")
    public ResponseEntity<?> summaryPerHour(@RequestParam String establishmentDocument) {
        return new ResponseEntity<>(vehicleControlService.summaryPerHour(establishmentDocument), HttpStatus.OK);
    }

    @Operation(description = "Realiza uma entrada do veículo de um cliente" +
            " no estacionamento selecionado")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o estabelecimento com a lista de veículos" +
                    " estacionados atualizada"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/entry/customer")
    public ResponseEntity<?> vehicleEntryControlForCustomer(@RequestParam String establishmentDocument,
                                                            @RequestParam String customerDocument) {
        return new ResponseEntity<>(vehicleControlService.vehicleEntryControlForCustomer(
                establishmentDocument, customerDocument), HttpStatus.OK);
    }

    @Operation(description = "Realiza uma saída do veículo de um cliente" +
            " no estacionamento selecionado")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o estabelecimento com a lista de veículos" +
                    " estacionados atualizada"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/exit/customer")
    public ResponseEntity<?> vehicleExitControlForCustomer(@RequestParam String establishmentDocument,
                                                           @RequestParam String customerDocument) {
        return new ResponseEntity<>(vehicleControlService.vehicleExitControlForCustomer(
                establishmentDocument, customerDocument), HttpStatus.OK);
    }
}
