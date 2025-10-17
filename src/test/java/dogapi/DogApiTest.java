package dogapi;

import java.io.IOException;
import java.util.List;

public class DogApiTest {
    public static void main(String[] args) {
        BreedFetcher fetcher = new DogApiBreedFetcher();

        try {
            // Test with a valid breed
            String breed = "deerhound";
            List<String> subs = fetcher.getSubBreeds(breed);
            System.out.println("Sub-breeds of " + breed + ": " + subs);

            // Test with an invalid breed
            String invalidBreed = "notabreed";
            List<String> badSubs = fetcher.getSubBreeds(invalidBreed);
            System.out.println("Sub-breeds of " + invalidBreed + ": " + badSubs);

        } catch (BreedFetcher.BreedNotFoundException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

