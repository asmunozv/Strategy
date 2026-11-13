package com.logistpulse.strategy.picking;

import com.logistpulse.strategy.model.StockLot;
import java.util.Comparator;

public class FefoPickingPolicy implements PickingPolicy {
    public String name() { return "FEFO"; }
    public Comparator<StockLot> order() {
        return Comparator.comparing(StockLot::getExpiresAt)
                .thenComparing(StockLot::getReceivedAt)
                .thenComparing(StockLot::getLotId);
    }
}
