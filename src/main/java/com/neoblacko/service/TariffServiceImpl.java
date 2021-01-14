package com.neoblacko.service;

import com.neoblacko.model.Tariff;
import com.neoblacko.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService{

    @Autowired
    private TariffRepository tariffRepository;

    @Override
    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @Override
    public Tariff getTariffById(Integer id) {
        return tariffRepository.getOne(id);
    }


}
