package org.localhost.pizzeria.supplies.system.service.impl;

import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystem;
import org.springframework.stereotype.Service;

@Service
public class SupplySystemImpl implements SupplySystem {
    private final SupplySystemRepository supplySystemRepository;

    public SupplySystemImpl(SupplySystemRepository supplySystemRepository) {
        this.supplySystemRepository = supplySystemRepository;
    }
}
