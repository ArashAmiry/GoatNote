package com.goatnote.goatnote.models;


import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CalculatoraAPI {

    public static double calculateImage(String file) throws IOException {
        OkHttpClient client = new OkHttpClient();
        File imageFile = new File("C:\\Users\\Arash\\Desktop\\test1.jpg");

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("locale", "en")
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(MediaType.parse("text/plain"), imageFile))
                .build();

        Request request = new Request.Builder()
                .url("https://photomath1.p.rapidapi.com/maths/solve-problem")
                .post(body)
                .addHeader("X-RapidAPI-Key", "YOUR API KEY")
                .addHeader("X-RapidAPI-Host", "photomath1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JSONObject jsonResponse = new JSONObject(responseBody);
        double solution = jsonResponse.getJSONObject("result")
                .getJSONArray("groups")
                .getJSONObject(0)
                .getJSONArray("entries")
                .getJSONObject(0)
                .getJSONObject("preview")
                .getJSONObject("content")
                .getJSONObject("solution")
                .getDouble("value");

        return solution;
    }
}
