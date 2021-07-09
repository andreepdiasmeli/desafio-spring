package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.CreateSellerDTO;
import meli.bootcamp.desafio_spring.dtos.SellerDTO;
import meli.bootcamp.desafio_spring.services.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public List<SellerDTO> getAll(){
        return sellerService.getAllSellers();
    }

    @GetMapping("{sellerId}")
    public SellerDTO getById(@PathVariable Long sellerId){
        return sellerService.getSellerById(sellerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerDTO create(@RequestBody CreateSellerDTO createSellerDTO){
        return sellerService.createSeller(createSellerDTO);
    }

    @PutMapping("{sellerId}")
    public SellerDTO update(@PathVariable Long sellerId, @RequestBody SellerDTO sellerDTO){
        return sellerService.updateSeller(sellerId, sellerDTO);
    }

    @DeleteMapping("{sellerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long sellerId){
        sellerService.deleteSellerById(sellerId);
    }
}
