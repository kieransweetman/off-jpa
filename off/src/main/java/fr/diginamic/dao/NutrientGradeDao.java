package fr.diginamic.dao;

import fr.diginamic.entities.NutrientGrade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class NutrientGradeDao {
    private EntityManager em;

    public NutrientGradeDao(EntityManager em) {
        this.em = em;
    }

    public void create(NutrientGrade nutrientGrade) {
        if (findByName(nutrientGrade.getName()) == null) {
            em.persist(nutrientGrade);
        } else {
            // System.out.println("NutrientGrade with name " + nutrientGrade.getName() + "
            // already
            // exists.");
            return;
        }
    }

    public NutrientGrade find(int id) {
        return em.find(NutrientGrade.class, id);
    }

    public NutrientGrade findByName(String name) {
        TypedQuery<NutrientGrade> query = em.createQuery(
                "SELECT b FROM NutrientGrade b WHERE LOWER(b.name) = LOWER(:name)",
                NutrientGrade.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}