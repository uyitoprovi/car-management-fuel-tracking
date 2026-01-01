package com.codehills.cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarFuelCLI {

    private static final String BASE_URL = "http://localhost:8080/api/cars";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: create-car | add-fuel | fuel-stats");
            return;
        }

        String command = args[0];
        switch (command) {
            case "create-car":
                handleCreateCar(args);
                break;
            case "add-fuel":
                handleAddFuel(args);
                break;
            case "fuel-stats":
                handleFuelStats(args);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private static void handleCreateCar(String[] args) throws Exception {
        String brand = getArgValue(args, "--brand");
        String model = getArgValue(args, "--model");
        String year = getArgValue(args, "--year");

        String json = String.format("{\"brand\":\"%s\",\"model\":\"%s\",\"year\":%s}", brand, model, year);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static void handleAddFuel(String[] args) throws Exception {
        String carId = getArgValue(args, "--carId");
        String liters = getArgValue(args, "--liters");
        String price = getArgValue(args, "--price");
        String odometer = getArgValue(args, "--odometer");

        String json = String.format("{\"liters\":%s,\"price\":%s,\"odometer\":%s}", liters, price, odometer);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + carId + "/fuel"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static void handleFuelStats(String[] args) throws Exception {
    String carId = getArgValue(args, "--carId");

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + carId + "/fuel/stats"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String body = response.body();

    if (body == null || body.isBlank()) {
        System.out.println("No fuel stats available.");
        return;
    }

    ObjectMapper mapper = new ObjectMapper();
    FuelStats stats = mapper.readValue(body, FuelStats.class);

    System.out.printf("Total fuel: %.0f L%n", stats.totalFuel);
    System.out.printf("Total cost: %.2f%n", stats.totalCost);
    System.out.printf("Average consumption: %.1f L/100km%n", stats.avgConsumption);
}


    private static String getArgValue(String[] args, String key) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(key) && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException("Missing argument: " + key);
    }
    public static class FuelStats {
    @JsonProperty("totalFuel")
    public double totalFuel;

    @JsonProperty("totalCost")
    public double totalCost;

    @JsonProperty("averageConsumption")
    public double avgConsumption;
}


}
