package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpsertProductDTO {
    
    @NotNull @NotEmpty
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;
    private Long categoryId;
    

    public UpsertProductDTO() {}

    public UpsertProductDTO(
            String productName, 
            String type, 
            String brand, 
            String color,
            String notes, 
            Long categoryId) {
        this.productName = productName;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getType() {
        return this.type;
    }

    public String getBrand() {
        return this.brand;
    }
    
    public String getColor() {
        return this.color;
    }

    public String getNotes() {
        return this.notes;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }
}
