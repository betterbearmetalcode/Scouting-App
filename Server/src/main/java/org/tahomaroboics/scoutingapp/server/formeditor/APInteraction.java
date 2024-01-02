package org.tahomaroboics.scoutingapp.server.formeditor;


import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APInteraction {

    public static final String baseURL = "https://www.thebluealliance.com/api/v3";
        private static final String apiKey = "gkV8whv2viztnwQybXkOmyQMYYJEGNh7qgbUvG0riVVdDH2YMKk57JNaRwgiTSQB";



    public static String getAsString(String apiRequest) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + apiRequest + "?X-TBA-Auth-Key=" + apiKey))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        return response.body();
    }

    public static JSONObject get(String apiRequest) throws Exception{
        return XML.toJSONObject(getAsString(apiRequest));

    }

}
