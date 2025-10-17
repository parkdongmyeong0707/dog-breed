package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, BreedFetcher.BreedNotFoundException {
        String breed = "hound";
        BreedFetcher breedFetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());
        int result = getNumberOfSubBreeds(breed, breedFetcher);
        System.out.println(breed + " has " + result + " sub breeds");

        breed = "cat";
        result = getNumberOfSubBreeds(breed, breedFetcher);
        System.out.println(breed + " has " + result + " sub breeds");
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * @param breed the name of the dog breed
     * @param breedFetcher the breedFetcher to use
     * @return the number of sub breeds. Zero should be returned if there are no sub breeds
     * returned by the fetcher ok
     */

    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) throws IOException, BreedFetcher.BreedNotFoundException {
        final String url = String.format("https://dog.ceo/api/breed/%s/list", breed);
        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful() ||  response.body() == null){
                return 0;
            }
        }
        List<String> subBreeds = breedFetcher.getSubBreeds(breed);
        return subBreeds.size();
    }
}