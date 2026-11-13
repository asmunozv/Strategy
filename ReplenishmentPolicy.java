package com.logistpulse.strategy.replenishment;

import com.logistpulse.strategy.model.ReplenishmentData;

public interface ReplenishmentPolicy {
    String name();
    int recommendedOrder(ReplenishmentData data);
}
