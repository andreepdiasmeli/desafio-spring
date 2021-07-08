package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Product;

public class ProductDTO {

    private Long productId;
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;
    private CategoryDTO category;


    public ProductDTO() {}

    public ProductDTO(
            Long productId,
            String productName,
            String type,
            String brand,
            String color,
            String notes,
            CategoryDTO category) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
        this.category = category;
    }

    public Long getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public CategoryDTO getCategory() {
        return this.category;
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

    public static ProductDTO toDTO(Product product) {
        CategoryDTO productCategoryDTO = CategoryDTO.toDTO(product.getCategory());

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getType(),
                product.getBrand(),
                product.getColor(),
                product.getNotes(),
                productCategoryDTO
        );
    }
}
