package com.logistpulse.strategy;

import com.logistpulse.strategy.context.SmartInventory;
import com.logistpulse.strategy.model.ReplenishmentData;
import com.logistpulse.strategy.model.StockLot;
import com.logistpulse.strategy.picking.*;
import com.logistpulse.strategy.replenishment.*;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

public class Main {
    private static List<StockLot> exampleLots() {
        return List.of(
            new StockLot("SKU-101", "LOTE-A", LocalDate.of(2026, 1, 10), LocalDate.of(2027, 8, 1), 15),
            new StockLot("SKU-101", "LOTE-B", LocalDate.of(2026, 3, 10), LocalDate.of(2027, 3, 1), 12),
            new StockLot("SKU-101", "LOTE-C", LocalDate.of(2026, 6, 10), LocalDate.of(2027, 6, 1), 20)
        );
    }
    public static void main(String[] args) {
        SmartInventory inventory = new SmartInventory(new FefoPickingPolicy(), new ReorderPointPolicy());
        LocalDate today = LocalDate.of(2026, 11, 13);
        for (PickingPolicy p : List.of(new FefoPickingPolicy(), new FifoPickingPolicy(), new LifoPickingPolicy())) {
            inventory.setPickingPolicy(p);
            System.out.println(p.name() + " picking 18 unidades: " + inventory.pick("SKU-101", 18, exampleLots(), today));
        }
        ReplenishmentData data = new ReplenishmentData("SKU-101", 25, 10, 4, 5, 30, 2, 30, 150);
        for (ReplenishmentPolicy p : List.of(new ReorderPointPolicy(), new EoqPolicy(), new MinMaxPolicy())) {
            inventory.setReplenishmentPolicy(p);
            System.out.println(p.name() + " recomienda pedir: " + inventory.recommendedOrder(data));
        }
    }
}
