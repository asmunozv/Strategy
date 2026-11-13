package com.logistpulse.strategy.context;

import com.logistpulse.strategy.model.PickAllocation;
import com.logistpulse.strategy.model.ReplenishmentData;
import com.logistpulse.strategy.model.StockLot;
import com.logistpulse.strategy.picking.PickingPolicy;
import com.logistpulse.strategy.replenishment.ReplenishmentPolicy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SmartInventory {
    private PickingPolicy pickingPolicy;
    private ReplenishmentPolicy replenishmentPolicy;

    public SmartInventory(PickingPolicy pickingPolicy, ReplenishmentPolicy replenishmentPolicy) {
        setPickingPolicy(pickingPolicy);
        setReplenishmentPolicy(replenishmentPolicy);
    }
    public void setPickingPolicy(PickingPolicy policy) {
        this.pickingPolicy = Objects.requireNonNull(policy);
    }
    public void setReplenishmentPolicy(ReplenishmentPolicy policy) {
        this.replenishmentPolicy = Objects.requireNonNull(policy);
    }
    public String pickingName() { return pickingPolicy.name(); }
    public String replenishmentName() { return replenishmentPolicy.name(); }

    // Selecciona lotes y descuenta unidades solo despues de comprobar disponibilidad.
    public List<PickAllocation> pick(String sku, int quantity, List<StockLot> lots, LocalDate today) {
        if (sku == null || sku.isBlank() || quantity <= 0) throw new IllegalArgumentException("Solicitud de picking invalida");
        Objects.requireNonNull(lots);
        Objects.requireNonNull(today);
        List<StockLot> eligible = lots.stream()
            .filter(l -> l.getSku().equals(sku) && l.getAvailable() > 0 && !l.getExpiresAt().isBefore(today))
            .sorted(pickingPolicy.order()).toList();
        int total = eligible.stream().mapToInt(StockLot::getAvailable).sum();
        if (total < quantity) throw new IllegalStateException("Stock elegible insuficiente para " + sku);
        List<PickAllocation> picks = new ArrayList<>();
        int remaining = quantity;
        for (StockLot lot : eligible) {
            if (remaining == 0) break;
            int take = Math.min(remaining, lot.getAvailable());
            lot.withdraw(take);
            picks.add(new PickAllocation(lot.getLotId(), take));
            remaining -= take;
        }
        return List.copyOf(picks);
    }

    public int recommendedOrder(ReplenishmentData data) {
        return replenishmentPolicy.recommendedOrder(Objects.requireNonNull(data));
    }
}
