package com.logistpulse.strategy.replenishment;

import com.logistpulse.strategy.model.ReplenishmentData;

public class MinMaxPolicy implements ReplenishmentPolicy {
    public String name() { return "Min-Max"; }
    public int recommendedOrder(ReplenishmentData data) {
        return data.availableStock() <= data.minimumStock()
                ? data.maximumStock() - data.availableStock() : 0;
    }
}
