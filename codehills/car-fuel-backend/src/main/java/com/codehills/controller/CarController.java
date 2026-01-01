package com.codehills.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codehills.model.Car;
import com.codehills.model.FuelEntry;
import com.codehills.model.FuelStats;
import com.codehills.service.CarService;


@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    // List cars
    @GetMapping
    public Collection<Car> getCars() {
        return service.getAll();
    }

    // Get car by ID
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Create car
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return service.create(car);
    }

    // Update car
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        return service.update(id, car);
    }

    // Delete car
    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? "Car deleted" : "Car not found";
    }

    // ✅ Adding Fuel
    @PostMapping("/{id}/fuel")
    public ResponseEntity<String> addFuel(
            @PathVariable Long id,
            @RequestBody FuelEntry fuelEntry) {

        service.addFuel(id, fuelEntry);
        return ResponseEntity.ok("Fuel added successfully");
    }

    // fuel statistic endpoint

    @GetMapping("/{id}/fuel/stats")
    public FuelStats getFuelStats(@PathVariable Long id){
        return service.getFuelStats(id);
    }
}
