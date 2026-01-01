package com.codehills.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.codehills.model.Car;
import com.codehills.model.FuelEntry;
import com.codehills.model.FuelStats;

@Service
public class CarService {

    private final Map<Long, Car> cars = new HashMap<>();
    private Long idCounter = 1L;

    // =====================
    // CREATE CAR
    // =====================
    public Car create(Car car) {
        car.setId(idCounter++);
        cars.put(car.getId(), car);
        return car;
    }

    // =====================
    // READ ALL CARS
    // =====================
    public Collection<Car> getAll() {
        return cars.values();
    }

    // =====================
    // READ CAR BY ID
    // =====================
    public Car getById(Long id) {
        return cars.get(id);
    }

    // =====================
    // UPDATE CAR
    // =====================
    public Car update(Long id, Car updatedCar) {
        Car existing = cars.get(id);
        if (existing == null) {
            return null;
        }

        existing.setBrand(updatedCar.getBrand());
        existing.setModel(updatedCar.getModel());
        existing.setYear(updatedCar.getYear());

        return existing;
    }

    // =====================
    // DELETE CAR
    // =====================
    public boolean delete(Long id) {
        return cars.remove(id) != null;
    }

    // =====================
    // ADD FUEL ENTRY
    // =====================
    public void addFuel(Long carId, FuelEntry fuelEntry) {
        Car car = cars.get(carId);
        if (car == null) {
            throw new RuntimeException("Car not found");
        }

        car.getFuelEntries().add(fuelEntry);
    }

    // =====================
    // FUEL STATISTICS
    // =====================
    public FuelStats getFuelStats(Long carId) {
        Car car = cars.get(carId);

        if (car == null || car.getFuelEntries().size() < 2) {
            return new FuelStats(0, 0, 0);
        }

        double totalFuel = 0;
        double totalCost = 0;

        int firstOdometer = car.getFuelEntries().get(0).getOdometer();
        int lastOdometer = car.getFuelEntries()
                .get(car.getFuelEntries().size() - 1)
                .getOdometer();

        for (FuelEntry entry : car.getFuelEntries()) {
            totalFuel += entry.getLiters();
            totalCost += entry.getPrice();
        }

        double distance = lastOdometer - firstOdometer;
        double avgConsumption = distance > 0
                ? (totalFuel / distance) * 100
                : 0;

        return new FuelStats(totalFuel, totalCost, avgConsumption);
    }
}
