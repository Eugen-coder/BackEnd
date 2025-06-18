import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageColorAnalyzer {
    private static final String API_KEY = "acc_bd64e207bc12a71";
    private static final String API_SECRET = "41ed637475ca6d6b9f4fb35e19283c71";

    public static void main(String[] args) throws Exception {
        String imageUrl = "https://example.com/path/to/image.jpg";
        String credentials = Base64.getEncoder().encodeToString((API_KEY + ":" + API_SECRET).getBytes());

        URL url = new URL("https://api.imagga.com/v2/colors?image_url=" + imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Basic " + credentials);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder responseStr = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            responseStr.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(responseStr.toString());
        JSONArray colors = json.getJSONObject("result").getJSONObject("colors").getJSONArray("image_colors");

        System.out.printf("%-25s%-25s%-20s%n", "color name", "parent color name", "coverage percent");
        for (int i = 0; i < colors.length(); i++) {
            JSONObject color = colors.getJSONObject(i);
            String name = color.getString("closest_palette_color");
            String parent = color.getString("closest_palette_color_parent");
            double percent = color.getDouble("percent");
            System.out.printf("%-25s%-25s%-20.2f%n", name, parent, percent);
        }
    }

}
