package fr.diginamic.dao;

import fr.diginamic.entities.Nutrient;
import fr.diginamic.entities.Product;
import fr.diginamic.entities.ProductNutrient;
import fr.diginamic.entities.ProductNutrient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProductNutrientDao {
    private EntityManager em;

    public ProductNutrientDao(EntityManager em) {
        this.em = em;
    }

    public void create(ProductNutrient productNutrient) {
        if (findByPandN(productNutrient.getProduct(), productNutrient.getNutrient()) == null) {
            em.persist(productNutrient);
        } else {
            // System.out.println("ProductNutrient with name " + productNutrient.getName() +
            // "
            // already
            // exists.");
            return;
        }
    }

    public ProductNutrient find(int id) {
        return em.find(ProductNutrient.class, id);
    }

    public ProductNutrient findByPandN(Product p, Nutrient n) {
        TypedQuery<ProductNutrient> query = em.createQuery(
                "SELECT b FROM ProductNutrient b WHERE b.product = :product AND b.nutrient = :nutrient",
                ProductNutrient.class);
        query.setParameter("product", p);
        query.setParameter("nutrient", n);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}