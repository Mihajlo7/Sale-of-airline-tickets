package com.diplomski.backend.repository;

import com.diplomski.backend.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
    Optional<Route> findByFlight(Integer flight);
}
