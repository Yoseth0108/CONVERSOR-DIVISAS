import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

    public class CurrencyConverter {
        // URL de la API, cambiando la base a "USD"
        private static final String API_URL = "https://v6.exchangerate-api.com/v6/c33bc2ebb691492a3bb3a2a0/latest/USD";

        // Método para obtener la tasa de cambio desde USD a otra divisa
        public double fetchExchangeRate(String toCurrency) {
            try {
                // URL de la solicitud con la divisa de destino
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Leer la respuesta de la API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Analizar el JSON y obtener la tasa de cambio
                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getJSONObject("conversion_rates").getDouble(toCurrency);

            } catch (Exception e) {
                System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
                return 0.0; // Devuelve 0.0 si ocurre un error
            }
        }

        // Método para convertir una cantidad desde USD a otra divisa
        public double convert(double amount, String toCurrency) {
            double rate = fetchExchangeRate(toCurrency);
            return amount * rate;
        }

        // Clase principal para realizar pruebas
        public static void main(String[] args) {
            CurrencyConverter converter = new CurrencyConverter();

            // Ejemplo de conversión de USD a EUR
            double amount = 100; // Cantidad a convertir
            String toCurrency = "EUR"; // Divisa de destino

            // Realizar la conversión y mostrar el resultado
            double result = converter.convert(amount, toCurrency);
            System.out.println(amount + " USD equivalen a " + result + " " + toCurrency);
        }
    }
