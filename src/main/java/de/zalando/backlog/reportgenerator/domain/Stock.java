package de.zalando.backlog.reportgenerator.domain;

public class Stock {

    private String status;
    private int quantity;


    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "status='" + status + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
