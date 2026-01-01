package com.codehills.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.codehills.cli.CarFuelCLI.FuelStats;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarFuelCLITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testFuelStatsDeserialization() throws Exception {
        String json = "{\"totalFuel\":70.0,\"totalCost\":95.0,\"averageConsumption\":14.0}";

        ObjectMapper mapper = new ObjectMapper();
        FuelStats stats = mapper.readValue(json, FuelStats.class);

        assertEquals(70.0, stats.totalFuel);
        assertEquals(95.0, stats.totalCost);
        assertEquals(14.0, stats.avgConsumption);
    }

    @Test
    public void testFuelStatsOutputFormat() {
        FuelStats stats = new FuelStats();
        stats.totalFuel = 70.0;
        stats.totalCost = 95.0;
        stats.avgConsumption = 14.0;

        System.out.printf("Total fuel: %.0f L%n", stats.totalFuel);
        System.out.printf("Total cost: %.2f%n", stats.totalCost);
        System.out.printf("Average consumption: %.1f L/100km%n", stats.avgConsumption);

        String output = outContent.toString();
        assertTrue(output.contains("Total fuel: 70"));
        assertTrue(output.contains("Total cost: 95.00"));
        assertTrue(output.contains("Average consumption: 14.0"));
    }
}
