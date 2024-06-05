package fr.diginamic.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class NutrientGrade {

    private String name;

    public NutrientGrade() {

    }

    public NutrientGrade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
