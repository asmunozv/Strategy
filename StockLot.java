package com.logistpulse.strategy.model;

import java.time.LocalDate;
import java.util.Objects;

public class StockLot {
    private final String sku;
    private final String lotId;
    private final LocalDate receivedAt;
    private final LocalDate expiresAt;
    private int available;

    public StockLot(String sku, String lotId, LocalDate receivedAt, LocalDate expiresAt, int available) {
        this.sku = Objects.requireNonNull(sku);
        this.lotId = Objects.requireNonNull(lotId);
        this.receivedAt = Objects.requireNonNull(receivedAt);
        this.expiresAt = Objects.requireNonNull(expiresAt);
        if (available < 0) throw new IllegalArgumentException("Stock negativo");
        this.available = available;
    }
    public String getSku() { return sku; }
    public String getLotId() { return lotId; }
    public LocalDate getReceivedAt() { return receivedAt; }
    public LocalDate getExpiresAt() { return expiresAt; }
    public int getAvailable() { return available; }
    public void withdraw(int quantity) {
        if (quantity <= 0 || quantity > available) throw new IllegalArgumentException("Cantidad invalida para lote " + lotId);
        available -= quantity;
    }
}
