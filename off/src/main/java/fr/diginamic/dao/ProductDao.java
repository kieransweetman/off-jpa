package fr.diginamic.dao;

import fr.diginamic.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProductDao {
    private EntityManager em;

    public ProductDao(EntityManager em) {
        this.em = em;
    }

    public void create(Product product) {
        if (findByName(product.getName()) == null) {
            em.persist(product);
        } else {
            // System.out.println("Product with name " + product.getName() + " already
            // exists.");
            return;
        }
    }

    public Product find(int id) {
        return em.find(Product.class, id);
    }

    public Product findByName(String name) {
        TypedQuery<Product> query = em.createQuery("SELECT b FROM Product b WHERE LOWER(b.name) = LOWER(:name)",
                Product.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}