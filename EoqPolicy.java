package com.logistpulse.strategy.replenishment;

import com.logistpulse.strategy.model.ReplenishmentData;

public class EoqPolicy implements ReplenishmentPolicy {
    public String name() { return "EOQ"; }
    public int recommendedOrder(ReplenishmentData data) {
        if (data.availableStock() > data.reorderPoint() || data.dailyDemand() == 0) return 0;
        double annualDemand = data.dailyDemand() * 365.0;
        return (int) Math.ceil(Math.sqrt(2 * annualDemand * data.orderingCost() / data.annualHoldingCost()));
    }
}
