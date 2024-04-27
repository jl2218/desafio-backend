package joao.dev.desafiobackendfcamara.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Cria um estabelecimento")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o estabelecimento criado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/register")
    public ResponseEntity<?> createEstablishment(@RequestBody @Valid EstablishmentDTO data) {
        Establishment newEstablishment = establishmentService.createEstablishment(data);
        return new ResponseEntity<>(newEstablishment, HttpStatus.CREATED);
    }

    @Operation(description = "Lista todos os estabelecimentos")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os estabelecimentos"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @GetMapping("/list")
    public ResponseEntity<?> listEstablishments() {
        return new ResponseEntity<>(establishmentService.list(), HttpStatus.OK);
    }

    @Operation(description = "Atualiza um estabelecimento")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o estabelecimento atualizado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateEstablishment(@RequestBody @Valid EstablishmentDTO data) {
        Establishment updatedEstablishment = establishmentService.updateEstablishment(data);
        return new ResponseEntity<>(updatedEstablishment, HttpStatus.OK);
    }

    @Operation(description = "Apaga um estabelecimento")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma String de sucesso"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEstablishment(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(establishmentService.deleteEstablishment(id), HttpStatus.OK);
    }
}
