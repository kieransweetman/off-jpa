package fr.diginamic.dao;

import java.util.List;
import java.util.Set;

import fr.diginamic.entities.Nutrient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class NutrientDao {
    private EntityManager em;

    public NutrientDao(EntityManager em) {
        this.em = em;
    }

    public void create(Nutrient nutrient) {
        if (findByName(nutrient.getName()) == null) {
            em.persist(nutrient);
        } else {
            // System.out.println("Nutrient with name " + nutrient.getName() + " already
            // exists.");
            return;
        }
    }

    public Nutrient find(int id) {
        return em.find(Nutrient.class, id);
    }

    public Nutrient findByName(String name) {
        TypedQuery<Nutrient> query = em.createQuery("SELECT b FROM Nutrient b WHERE LOWER(b.name) = LOWER(:name)",
                Nutrient.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<Nutrient> getAll() {
        return em.createQuery("Select b from Nutrient b", Nutrient.class).getResultList();
    }
}