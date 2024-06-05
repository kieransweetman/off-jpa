package fr.diginamic.dao;

import fr.diginamic.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CategoryDao {
    private EntityManager em;

    public CategoryDao(EntityManager em) {
        this.em = em;
    }

    public void create(Category category) {
        if (findByName(category.getName()) == null) {
            em.persist(category);
        } else {
            // System.out.println("Category with name " + category.getName() + " already
            // exists.");

        }
    }

    public Category find(int id) {
        return em.find(Category.class, id);
    }

    public Category findByName(String name) {
        TypedQuery<Category> query = em.createQuery("SELECT b FROM Category b WHERE LOWER(b.name) = LOWER(:name)",
                Category.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}