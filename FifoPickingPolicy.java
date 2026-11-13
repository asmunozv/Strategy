package com.logistpulse.strategy.picking;

import com.logistpulse.strategy.model.StockLot;
import java.util.Comparator;

public class FifoPickingPolicy implements PickingPolicy {
    public String name() { return "FIFO"; }
    public Comparator<StockLot> order() {
        return Comparator.comparing(StockLot::getReceivedAt).thenComparing(StockLot::getLotId);
    }
}
