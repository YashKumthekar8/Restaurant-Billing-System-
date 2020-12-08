package sample;

public class AddOrderTable {
    private String itemName;
    private double price;
    private int quantity;
    private String requests;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String foodName) {
        this.itemName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }
}
