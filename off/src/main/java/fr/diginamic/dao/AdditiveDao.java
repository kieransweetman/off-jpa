package fr.diginamic.dao;

import fr.diginamic.entities.Additive;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AdditiveDao {
    private EntityManager em;

    public AdditiveDao(EntityManager em) {
        this.em = em;
    }

    public void create(Additive additive) {
        if (findByName(additive.getName()) == null) {
            em.persist(additive);
        } else {
            // System.out.println("additive with name " + additive.getName() + " already
            // exists.");

        }
    }

    public Additive find(int id) {
        return em.find(Additive.class, id);
    }

    public Additive findByName(String name) {
        TypedQuery<Additive> query = em.createQuery("SELECT b FROM Additive b WHERE LOWER(b.name) = LOWER(:name)",
                Additive.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}