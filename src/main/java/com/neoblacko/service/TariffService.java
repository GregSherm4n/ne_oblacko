package com.neoblacko.service;

import com.neoblacko.model.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> getAllTariffs();
    Tariff getTariffById(Integer id);
}
