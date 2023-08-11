package com.diplomski.backend.dto;

import java.time.LocalDate;

public record BookingCustomerDTO(
        LocalDate flightDate,
        String departureScheduled,
        String arrivalScheduled,
        String airlineName,
        String iataCodeDep,
        String iataCodeArr,
        String seatCode,
        String seatClass,
        double amount
) {
}
