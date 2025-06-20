package hw20250619.konvertorvalut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverterApp {

    private static final String API_KEY = "YOUR_API_KEY"; // 🔑 Вставьте ваш ключ Fixer API

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Из какой валюты (например, USD): ");
        String from = scanner.nextLine().trim().toUpperCase();

        System.out.print("В какую валюту (например, EUR): ");
        String to = scanner.nextLine().trim().toUpperCase();

        System.out.print("Сумма для конвертации: ");
        double amount = scanner.nextDouble();

        CurrencyRequest request = new CurrencyRequest(from, to, amount);
        CurrencyResponse response = fetchConversion(request);

        if (response != null) {
            System.out.printf("Результат: %.2f %s = %.2f %s%n", amount, from, response.getResult(), to);
        } else {
            System.out.println("Ошибка при получении данных.");
        }

        scanner.close();
    }

    private static CurrencyResponse fetchConversion(CurrencyRequest request) {
        try {
            String urlStr = String.format("https://api.apilayer.com/fixer/convert?from=%s&to=%s&amount=%.2f",
                    request.getFrom(), request.getTo(), request.getAmount());

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("apikey", API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            JSONObject json = new JSONObject(jsonBuilder.toString());
            return new CurrencyResponse(json.getDouble("result"));

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CurrencyRequest {
        private String from;
        private String to;
        private double amount;
    }

    @Data
    @AllArgsConstructor
    static class CurrencyResponse {
        private double result;
    }
}