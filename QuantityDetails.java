package sample;

public class QuantityDetails {
    private
    String ItemName;
    int OrderID;
    int Quantity;
    String SpecialRequests;


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getSpecialRequests() {
        return SpecialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        SpecialRequests = specialRequests;
    }
}
