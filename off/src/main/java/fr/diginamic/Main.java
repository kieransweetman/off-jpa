package fr.diginamic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.*;

import fr.diginamic.dao.BrandDao;
import fr.diginamic.entities.Brand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("open-food-facts");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        String csvFile = "open-food-facts.csv";
        String line;
        String csvSeparator = "|";

        try {
            Reader in = new FileReader("open-food-facts.csv");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter('|')
                    .withFirstRecordAsHeader()
                    .parse(in);

            t.begin();

            long startTime = System.currentTimeMillis(); // Capture start time
            int count = 0;
            for (CSVRecord record : records) {
                String category = record.get("categorie");

                // Brand processing
                String brand = record.get("marque");
                BrandDao bd = new BrandDao(em);
                bd.create(new Brand(brand));
                // process the fields

                count++;
            }
            long endTime = System.currentTimeMillis(); // Capture end time
            t.commit();

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

        // try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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