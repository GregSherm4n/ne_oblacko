package com.neoblacko.repository;

import com.neoblacko.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Integer> {
    Tariff findByTariffId(Integer tariffid);

}
