package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.CreatePromotionDTO;
import meli.bootcamp.desafio_spring.dtos.PromotionDTO;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Promotion;
import meli.bootcamp.desafio_spring.repositories.PromotionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public PromotionDTO createPromotion(CreatePromotionDTO createPromotion, Post post)
            throws InvalidParameterException {
        Optional<InvalidParameterException> ex = validatePromotion(createPromotion);
        if (ex.isPresent()) {
            throw ex.get();
        }
        Promotion promo = new Promotion(createPromotion.getDiscount(), createPromotion.getExpiresAt(), post);
        return PromotionDTO.toDTO(promo);
    }

    private Optional<InvalidParameterException> validatePromotion(CreatePromotionDTO createPromotion) {
        BigDecimal discount = createPromotion.getDiscount();
        LocalDateTime expiresAt = createPromotion.getExpiresAt();
        InvalidParameterException ex = null;
        if (this.checkIfDiscountInRange(discount)) {
            ex = new InvalidParameterException("Discount value " +
                    discount.doubleValue() +
                    " is outside the valid range (0 > discount <= 1).");
        }
        if(expiresAt.isBefore(LocalDateTime.now())){
            ex = new InvalidParameterException("Expiration date for a promotion cannot be earlier than now.");
        }
        return Optional.ofNullable(ex);
    }

    private boolean checkIfDiscountInRange(BigDecimal discount) {
        return  discount.compareTo(new BigDecimal("0.0")) <= 0 ||
               discount.compareTo(new BigDecimal("1.0")) >= 1;
    }
}
