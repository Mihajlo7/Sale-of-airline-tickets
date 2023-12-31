package com.diplomski.backend.repository;

import com.diplomski.backend.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {
    Optional<Country> findByIso2Code(String iso2Code);
}
