package com.coxplore.helper.HttpRequest;

import com.coxplore.model.GoogleInfo;
import com.coxplore.security.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    @Autowired
    private SecurityConstants securityConstants;

    public HttpRequest() {
    }

    public GoogleInfo getGoogleUserInfo(String accessToken) throws IOException {
        URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        // read the response
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        GoogleInfo googleInfo = mapper.readValue(response.toString(), GoogleInfo.class);

        //print result
        System.out.println(response.toString());

        return googleInfo;
    }
}
