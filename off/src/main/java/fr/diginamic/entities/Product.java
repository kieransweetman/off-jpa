package fr.diginamic.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brandid")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "catogoryid")
    private Category catogory;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "grade"))
    private NutrientGrade nutrientGrade;

    private boolean palmOil;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "ProductAllergen", joinColumns = @JoinColumn(name = "productid"), inverseJoinColumns = @JoinColumn(name = "allergenid"))
    private Set<Allergen> allergens = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "ProductAdditive", joinColumns = @JoinColumn(name = "productid"), inverseJoinColumns = @JoinColumn(name = "additiveid"))
    private Set<Additive> additives = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductNutrient> productNutrients = new HashSet<>();

    public Product() {

    }

    public Product(String name, Brand brand, Category category, NutrientGrade nutrientGrade, boolean palmOil) {
        this.name = name;
        this.brand = brand;
        this.catogory = category;
        this.nutrientGrade = nutrientGrade;
        this.palmOil = palmOil;
    }

    public Set<ProductNutrient> getProductNutrients() {
        return productNutrients;
    }

    public void setProductNutrients(Set<ProductNutrient> productNutrients) {
        this.productNutrients = productNutrients;
    }

    public boolean isPalmOil() {
        return palmOil;
    }

    public void setPalmOil(boolean palmOil) {
        this.palmOil = palmOil;
    }

    public Set<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set<Allergen> allergens) {
        this.allergens = allergens;
    }

    public Set<Additive> getAdditives() {
        return additives;
    }

    public void setAdditives(Set<Additive> additives) {
        this.additives = additives;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCatogory() {
        return catogory;
    }

    public void setCatogory(Category catogory) {
        this.catogory = catogory;
    }

    public NutrientGrade getNutrientGrade() {
        return nutrientGrade;
    }

    public void setNutrientGrade(NutrientGrade nutrientGrade) {
        this.nutrientGrade = nutrientGrade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
