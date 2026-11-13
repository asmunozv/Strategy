package com.logistpulse.strategy.picking;

import com.logistpulse.strategy.model.StockLot;
import java.util.Comparator;

public interface PickingPolicy {
    String name();
    Comparator<StockLot> order();
}
