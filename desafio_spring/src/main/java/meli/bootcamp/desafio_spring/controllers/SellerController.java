package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.CreateSellerDTO;
import meli.bootcamp.desafio_spring.dtos.SellerDTO;
import meli.bootcamp.desafio_spring.services.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Operation(summary = "Get all sellers")
    @GetMapping
    public List<SellerDTO> getAll(){
        return sellerService.getAllSellers();
    }

    @Operation(summary = "Get a specific seller")
    @GetMapping("{sellerId}")
    public SellerDTO getById(@PathVariable Long sellerId){
        return sellerService.getSellerById(sellerId);
    }

    @Operation(summary = "Create a seller")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerDTO create(@RequestBody @Valid CreateSellerDTO createSellerDTO){
        return sellerService.createSeller(createSellerDTO);
    }

    @Operation(summary = "Update an existing seller")
    @PutMapping("{sellerId}")
    public SellerDTO update(@PathVariable Long sellerId, @RequestBody @Valid CreateSellerDTO sellerDTO){
        return sellerService.updateSeller(sellerId, sellerDTO);
    }

    @Operation(summary = "Delete an existing seller")
    @DeleteMapping("{sellerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long sellerId){
        sellerService.deleteSellerById(sellerId);
    }
}
