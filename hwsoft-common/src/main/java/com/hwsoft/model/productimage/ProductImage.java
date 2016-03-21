package com.hwsoft.model.productimage;

import com.hwsoft.common.productimage.ProductImageType;

import javax.persistence.*;

/**
 * 产品相关图片
 */
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image_title", nullable = true)
    private String imageTitle;

    @Column(name = "image_description", nullable = true)
    private String imageDescription;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type", nullable = false)
    private ProductImageType productImageType;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ProductImageType getProductImageType() {
        return productImageType;
    }

    public void setProductImageType(ProductImageType productImageType) {
        this.productImageType = productImageType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", imageTitle='" + imageTitle + '\'' +
                ", imageDescription='" + imageDescription + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", productImageType=" + productImageType +
                ", productId=" + productId +
                '}';
    }
}
