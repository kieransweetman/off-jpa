
package fr.diginamic.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProductNutrient")
public class ProductNutrient {

    @EmbeddedId
    private ProductNutrientId id;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @MapsId("nutrientId")
    @ManyToOne
    @JoinColumn(name = "nutrientid")
    private Nutrient nutrient;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Nutrient getNutrient() {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Column(name = "value")
    private Double value;

    public ProductNutrient() {
    }

    @Embeddable
    public static class ProductNutrientId implements Serializable {
        @Column(name = "productid")
        private int productId;

        @Column(name = "nutrientid")
        private int nutrientId;

        public ProductNutrientId() {
            this.productId = 0;
            this.nutrientId = 0;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getNutrientId() {
            return nutrientId;
        }

        public void setNutrientId(int nutrientId) {
            this.nutrientId = nutrientId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + productId;
            result = prime * result + nutrientId;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ProductNutrientId other = (ProductNutrientId) obj;
            if (productId != other.productId)
                return false;
            if (nutrientId != other.nutrientId)
                return false;
            return true;
        }

        // Constructors, getters, setters, equals, hashCode...
    }
}