package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Product;

public class ProductDTO {
    private Long productId;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;
    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String type, String brand, String color, String notes) {
        this.productId = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
    }

    public static ProductDTO toDTO(Product product) {
        ProductDTO newProduct = new ProductDTO(product.getId(),
             product.getName(),
             product.getType(),
             product.getBrand(),
             product.getColor(),
             product.getNotes()
        );
        return newProduct;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getNotes() {
        return notes;
    }
}
