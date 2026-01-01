package com.codehills.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.codehills.model.Car;
import com.codehills.model.FuelEntry;
import com.codehills.model.FuelStats;

public class CarServiceTest {

    private final CarService carService = new CarService();

    @Test
    public void testFuelStatsCalculation() {
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        Car createdCar = carService.create(car);
        Long carId = createdCar.getId();

        FuelEntry entry1 = new FuelEntry();
        entry1.setLiters(40.0);
        entry1.setPrice(50.0);
        entry1.setOdometer(10000);

        FuelEntry entry2 = new FuelEntry();
        entry2.setLiters(30.0);
        entry2.setPrice(45.0);
        entry2.setOdometer(10500);

        carService.addFuel(carId, entry1);
        carService.addFuel(carId, entry2);

        FuelStats stats = carService.getFuelStats(carId);

        assertEquals(70.0, stats.getTotalFuel());
        assertEquals(95.0, stats.getTotalCost());
        assertEquals(14.0, stats.getAverageConsumption(), 0.1);
    }
}
