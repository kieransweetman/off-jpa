package fr.diginamic.dao;

import fr.diginamic.entities.Brand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class BrandDao {
    private EntityManager em;

    public BrandDao(EntityManager em) {
        this.em = em;
    }

    public void create(Brand brand) {
        if (findByName(brand.getName()) == null) {
            em.persist(brand);
        } else {
            return;
            // System.out.println("Brand with name " + brand.getName() + " already exists.");
        }
    }

    public Brand find(int id) {
        return em.find(Brand.class, id);
    }

    public Brand findByName(String name) {
        TypedQuery<Brand> query = em.createQuery("SELECT b FROM Brand b WHERE LOWER(b.name) = LOWER(:name)",
                Brand.class);
        query.setParameter("name", name.toLowerCase());
        return query.getResultList().stream().findFirst().orElse(null);
    }
}