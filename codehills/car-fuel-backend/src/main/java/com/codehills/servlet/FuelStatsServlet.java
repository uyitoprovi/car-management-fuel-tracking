package com.codehills.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.codehills.model.FuelStats;
import com.codehills.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/servlet/fuel-stats")
public class FuelStatsServlet extends HttpServlet {

    @Autowired
    private CarService carService; // Spring will inject the same service used by REST API

    @Override
    public void init() throws ServletException {
        super.init();
        // Enable Spring dependency injection in servlet
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carIdParam = request.getParameter("carId");
        if (carIdParam == null) {
            writeError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing carId parameter");
            return;
        }

        long carId;
        try {
            carId = Long.parseLong(carIdParam);
        } catch (NumberFormatException e) {
            writeError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid carId format");
            return;
        }

        FuelStats stats;
        try {
            stats = carService.getFuelStats(carId);
        } catch (RuntimeException e) {
            writeError(response, HttpServletResponse.SC_NOT_FOUND, "Car not found");
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        try (PrintWriter out = response.getWriter()) {
            out.write(mapper.writeValueAsString(stats));
        }
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.write("{\"error\":\"" + message + "\"}");
        }
    }
}
