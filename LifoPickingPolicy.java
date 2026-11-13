package com.logistpulse.strategy.picking;

import com.logistpulse.strategy.model.StockLot;
import java.util.Comparator;

public class LifoPickingPolicy implements PickingPolicy {
    public String name() { return "LIFO"; }
    public Comparator<StockLot> order() {
        return Comparator.comparing(StockLot::getReceivedAt).reversed().thenComparing(StockLot::getLotId);
    }
}
