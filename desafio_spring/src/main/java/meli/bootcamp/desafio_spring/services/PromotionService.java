package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.CreatePromotionDTO;
import meli.bootcamp.desafio_spring.dtos.PromotionDTO;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Promotion;
import meli.bootcamp.desafio_spring.repositories.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public PromotionDTO createPromotion(CreatePromotionDTO createPromotion, Post post) {
        Promotion promotion = new Promotion(createPromotion.getDiscount(), createPromotion.getExpiresAt(), post);
        return PromotionDTO.toDTO(promotion);
    }

    public void updatePromotion(Long oldPromotionId, CreatePromotionDTO updatePost) {
        Promotion promo = this.promotionRepository.getById(oldPromotionId);
        promo.setDiscout(updatePost.getDiscount());
        promo.setExpiresAt(updatePost.getExpiresAt());
    }

    public void deletePromotion(Promotion promotion, Post post) {
        post.setPromotion(null);
        this.promotionRepository.delete(promotion);
    }
}
