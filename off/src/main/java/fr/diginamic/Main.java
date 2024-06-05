package fr.diginamic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.csv.*;

import fr.diginamic.dao.AdditiveDao;
import fr.diginamic.dao.AllergenDao;
import fr.diginamic.dao.BrandDao;
import fr.diginamic.dao.CategoryDao;
import fr.diginamic.dao.NutrientDao;
import fr.diginamic.dao.NutrientGradeDao;
import fr.diginamic.dao.ProductDao;
import fr.diginamic.dao.ProductNutrientDao;
import fr.diginamic.entities.Additive;
import fr.diginamic.entities.Allergen;
import fr.diginamic.entities.Brand;
import fr.diginamic.entities.Category;
import fr.diginamic.entities.Nutrient;
import fr.diginamic.entities.NutrientGrade;
import fr.diginamic.entities.Product;
import fr.diginamic.entities.ProductNutrient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("open-food-facts");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            Reader in = new FileReader("open-food-facts.csv");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter('|')
                    .withFirstRecordAsHeader()
                    .parse(in);

            t.begin();

            long startTime = System.currentTimeMillis(); // Capture start time
            int count = 0;
            Iterator<CSVRecord> recordIterator = records.iterator();

            // parsing just the nutrient headers
            if (recordIterator.hasNext()) {
                CSVRecord headerRecord = recordIterator.next();
                Map<String, String> headerMap = headerRecord.toMap();

                List<String> nutrientColumns = headerMap.keySet().stream()
                        .filter(columnName -> columnName.endsWith("100g"))
                        .collect(Collectors.toList());

                nutrientColumns.stream().forEach(n -> {
                    NutrientDao nd = new NutrientDao(em);
                    nd.create(new Nutrient(n));
                });

            }

            t.commit();

            while (recordIterator.hasNext()) {
                CSVRecord record = recordIterator.next();

                // Categorie
                String categoryString = record.get("categorie");
                Category category = new Category(categoryString);
                CategoryDao cd = new CategoryDao(em);
                cd.create(category);
                Category c = cd.findByName(category.getName());

                // Brand processing
                String brandString = record.get("marque");
                Brand brand = new Brand(brandString);
                BrandDao bd = new BrandDao(em);
                bd.create(brand);
                Brand b = bd.findByName(brand.getName());

                // Product processing
                String productName = record.get("nom");
                NutrientGrade grade = new NutrientGrade(record.get("nutritionGradeFr"));
                boolean palmOil = Boolean.parseBoolean(record.get("presenceHuilePalme"));

                ProductDao productDao = new ProductDao(em);
                Product newProduct = new Product(productName, b, c, grade,
                        palmOil);

                // AdditiveDao additiveDao = new AdditiveDao(em);
                // String additiveString = record.get("additif");
                // Additive additive = new Additive(additiveString);

                String allergenString = record.get("allergenes");
                Set<Allergen> allergens = new HashSet<>();
                if (allergenString != null) {
                    String[] allergenArray = allergenString.split(","); // Replace "," with your actual delimiter
                    AllergenDao allergenDao = new AllergenDao(em);
                    for (String allString : allergenArray) {
                        String[] tokens = allString.split(":");
                        System.out.println(tokens);
                        // Allergen allergen = new Allergen(tokens[0], tokens[1]);
                        // allergenDao.create(allergen); // Persist the allergen
                        // allergens.add(allergen);
                    }
                    newProduct.setAllergens(allergens);
                }
                // Nutrients processing
                // ...

                productDao.create(newProduct);
                Product product = productDao.findByName(newProduct.getName());
                NutrientDao nd = new NutrientDao(em);
                List<Nutrient> nutrients = nd.getAll();
                for (Nutrient n : nutrients) {
                    String nutrientValueString = record.get(n.getName());
                    if (nutrientValueString != null) {
                        double nutrientValue = Double.parseDouble(nutrientValueString);

                        ProductNutrient productNutrient = new ProductNutrient();
                        productNutrient.setProduct(product);
                        productNutrient.setNutrient(n);
                        productNutrient.setValue(nutrientValue);

                        ProductNutrientDao productNutrientDao = new ProductNutrientDao(em);
                        productNutrientDao.create(productNutrient); // Persist the ProductNutrient entity
                    }
                }

                System.out.println("Processed record " + count);
                count++;
            }

            t.commit();
            // TIMER SECTION
            long endTime = System.currentTimeMillis(); // Capture end time

            long duration = endTime - startTime; // Calculate duration

            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
            long milliseconds = duration - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
            System.out.println("\n---\n");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "Duration", "Minutes", "Seconds", "Milliseconds",
                    "Lines");
            System.out.printf("%-15s %-15d %-15d %-15d %-15d%n", "Execution time:", minutes, seconds, milliseconds,
                    count);
            System.out.println("\n---\n");

        } catch (IOException e) {
            e.printStackTrace();
            if (t.isActive()) {
                t.rollback();
            }
        } finally {
            em.close();
            emf.close();
        }

        // br.readLine();
        // while ((line = br.readLine()) != null) {
        // String[] tokens = line.split(csvSeparator);
        // System.out.println(line.toString());
        // // for (int i = 0; i < tokens.length; i++) {
        // // String token = tokens[i];
        // // // Now you can use 'token' to process each column.
        // // switch (i) {
        // // case 0:
        // // // process the token when it's in the 'categorie' column
        // // String category = token;
        // // break;
        // // case 1:
        // // // process the token when it's in the 'marque' column
        // // String brand = token;
        // // BrandDao bd = new BrandDao(em);
        // // bd.create(new Brand(brand));
        // // break;
        // // case 2:
        // // // process the token when it's in the 'nom' column
        // // String product = token;
        // // break;
        // // case 3:
        // // // process the token when it's in the 'nutritionGradeFr' column
        // // String nutritionGradeFr = token;
        // // break;
        // // case 4:
        // // // process the token when it's in the 'ingredients' column
        // // String ingredients = token;
        // // break;
        // // // Add more cases as needed for other columns.
        // // default:
        // // // process the token when it's in a column without a specific case
        // // // This will handle all the 'nutrient' columns
        // // String nutrient = token;
        // // break;
        // // }
        // // }
        // }
        // t.commit();
        // } catch (IOException e) {
        // e.printStackTrace();
        // if (t.isActive()) {
        // t.rollback();
        // }
        // } finally {
        // em.close();
        // emf.close();
        // }
    }
}