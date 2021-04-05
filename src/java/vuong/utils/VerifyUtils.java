/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerifyUtils {

    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }
        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);
            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=6LeFuOAZAAAAALLdsh3kw_znpo5HtJUgUFNYu6h0&response=" + gRecaptchaResponse;
            conn.setDoOutput(true);
            try (OutputStream outStream = conn.getOutputStream()) {
                outStream.write(postParams.getBytes());
                outStream.flush();
            }
            InputStream is = conn.getInputStream();
            JsonObject jsonObject;
            try (JsonReader jsonReader = Json.createReader(is)) {
                jsonObject = jsonReader.readObject();
            }
            boolean success = jsonObject.getBoolean("success");
            return success;
        } catch (IOException e) {
            return false;
        }
    }
}