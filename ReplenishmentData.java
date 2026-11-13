package com.logistpulse.strategy.model;

public record ReplenishmentData(
    String sku, int availableStock, double dailyDemand, int leadTimeDays,
    int safetyStock, double orderingCost, double annualHoldingCost,
    int minimumStock, int maximumStock) {
    public ReplenishmentData {
        if (sku == null || sku.isBlank() || availableStock < 0 || dailyDemand < 0
                || leadTimeDays < 0 || safetyStock < 0 || orderingCost <= 0
                || annualHoldingCost <= 0 || minimumStock < 0 || maximumStock < minimumStock) {
            throw new IllegalArgumentException("Datos de reabastecimiento invalidos");
        }
    }
    public int reorderPoint() {
        return (int) Math.ceil(dailyDemand * leadTimeDays) + safetyStock;
    }
}
