package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.repositories.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }
}
