package com.logistpulse.strategy.replenishment;

import com.logistpulse.strategy.model.ReplenishmentData;

public class ReorderPointPolicy implements ReplenishmentPolicy {
    public String name() { return "Punto de reorden"; }
    public int recommendedOrder(ReplenishmentData data) {
        int point = data.reorderPoint();
        if (data.availableStock() > point) return 0;
        // Politica order-up-to: reponer hasta un ciclo adicional de demanda tras el lead time.
        int target = point + (int) Math.ceil(data.dailyDemand() * data.leadTimeDays());
        return Math.max(0, target - data.availableStock());
    }
}
