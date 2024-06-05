package fr.diginamic.dao;

import fr.diginamic.entities.Allergen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AllergenDao {
    private EntityManager em;

    public AllergenDao(EntityManager em) {
        this.em = em;
    }

    public void create(Allergen allergen) {
        if (findByName(allergen.getName()) == null) {
            em.persist(allergen);
        } else {
            // System.out.println("allergen with name " + allergen.getName() + " already
            // exists.");

        }
    }

    public Allergen find(int id) {
        return em.find(Allergen.class, id);
    }

    public Allergen findByName(String name) {
        TypedQuery<Allergen> query = em.createQuery("SELECT b FROM Allergen b WHERE LOWER(b.name) = LOWER(:name)",
                Allergen.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}