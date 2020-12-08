package sample;

import java.sql.Date;
import java.sql.Time;

public class Orders {
    private int orderID;
    private double amount;
    private Time orderTime;
    private Date orderDate;

    public Orders(Integer orderID, Double amount, Time orderTime, Date orderDate){
        setOrderID(orderID);
        setAmount(amount);
        setOrderTime(orderTime);
        setOrderDate(orderDate);
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
