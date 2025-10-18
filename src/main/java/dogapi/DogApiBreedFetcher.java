package dogapi;

import jdk.jfr.ContentType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {

    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws IOException, BreedNotFoundException {
        final String url = String.format("https://dog.ceo/api/breed/%s/list", breed);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.

        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful() ||  response.body() == null) {
                throw new  BreedFetcher.BreedNotFoundException(
                        "Breed not found: " + breed);
            }
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            if (!"success".equals(json.getString("status"))){
                throw new BreedFetcher.BreedNotFoundException("Breed not found: " + breed);
            }
            JSONArray msg = json.getJSONArray("message");
            List<String> result = new ArrayList<>();

            for(int i = 0; i < msg.length(); i++){
                result.add(msg.getString(i));
                }
            return result;
        } catch ( IOException e) {
            throw new IOException(e);
        }
    }
}