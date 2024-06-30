package Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private  String baseUrl = "http://10.0.2.2:5183/";

    private  static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    private  ApiService apiService;


    public ApiClient() {
        apiService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
