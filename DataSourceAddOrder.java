package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceAddOrder {
    String host = "jdbc:mysql://localhost:3306/restaurantdatabase? autoReconnect=true&useSSL=false";
    String uName = "root";
    String uPass = "Yash@0812";

    private Connection connection;
    public static DataSourceAddOrder instance = new DataSourceAddOrder();
    PreparedStatement selectFoodItem;
    PreparedStatement insertIntoQuantityDetails;
    PreparedStatement selectOrderID;
    PreparedStatement joinOnFoodItemAndQuantityDetails;
    PreparedStatement removeFromQuantityDetails;
    PreparedStatement addOrderToOrderDetails;
    PreparedStatement cancelOrderFromOrderDetails;
    PreparedStatement cancelFromQuantityDetails;
    PreparedStatement foreignKeys;
    PreparedStatement priceDetails;
    PreparedStatement quantityDetails;
    PreparedStatement updateQuantityDetails;
    
    public int generateOrderID(){
        try{
            ResultSet resultSet = selectOrderID.executeQuery();
            resultSet.next();
            int orderID = resultSet.getInt(1);
            return orderID + 1;
        }catch (SQLException e){
            System.out.println("Query Failed");
            return 0;
        }
    }
    public DataSourceAddOrder(){
        this.instance = getInstance();
    }

    public static DataSourceAddOrder getInstance(){
        return instance;
    }

    public boolean open(){
        try{
            connection = DriverManager.getConnection(host,uName,uPass);
            updateQuantityDetails = connection.prepareStatement("update quantitydetails set Quantity = ? where OrderID = ? and ItemName = ?");
            quantityDetails = connection.prepareStatement("select Quantity,SpecialRequests from quantitydetails where OrderID = ? and ItemName = ?");
            priceDetails = connection.prepareStatement("select ItemPrice from fooditem where ItemName = ?");
            selectFoodItem = connection.prepareStatement("select ItemName from menutable");
            selectOrderID = connection.prepareStatement("select max(OrderID) from orderdetails");

            insertIntoQuantityDetails = connection.prepareStatement("insert into quantitydetails values(?,?,?,?)");

            joinOnFoodItemAndQuantityDetails = connection.prepareStatement(" select q.ItemName,ItemPrice,Quantity," +
                    "SpecialRequests from fooditem f" +
                    " inner join quantitydetails q where q.ItemName = f.ItemName;");

            removeFromQuantityDetails = connection.prepareStatement("delete from quantitydetails where" +
                    " OrderID = ? and ItemName = ?");

            addOrderToOrderDetails = connection.prepareStatement("insert into orderdetails values(?,?,?,?)");

            cancelOrderFromOrderDetails = connection.prepareStatement("delete from orderdetails where OrderID = ?");

            cancelFromQuantityDetails = connection.prepareStatement("delete from quantitydetails where OrderID = ?");

            foreignKeys = connection.prepareStatement("set global foreign_key_checks = 0");

            return true;
        }catch (SQLException e){
            System.out.println("Couldn't connect to the database");
            return false;
        }
    }

    public boolean close(){
        try{
            if(updateQuantityDetails != null){
                updateQuantityDetails.close();
            }
            if(quantityDetails != null){
                quantityDetails.close();
            }
            if(priceDetails != null){
                priceDetails.close();
            }
            if(foreignKeys != null){
                foreignKeys.close();
            }
            if(cancelFromQuantityDetails != null){
                cancelFromQuantityDetails.close();
            }
            if(cancelOrderFromOrderDetails != null){
                cancelOrderFromOrderDetails.close();
            }
            if(addOrderToOrderDetails != null){
                addOrderToOrderDetails.close();
            }
            if(removeFromQuantityDetails != null){
                removeFromQuantityDetails.close();
            }
            if(joinOnFoodItemAndQuantityDetails != null){
                joinOnFoodItemAndQuantityDetails.close();
            }
            if(selectOrderID != null){
                selectOrderID.close();
            }
            if(selectFoodItem != null){
                selectFoodItem.close();
            }
            if(insertIntoQuantityDetails != null){
                insertIntoQuantityDetails.close();
            }
            if(connection != null){
                connection.close();
            }
            return true;
        }catch (SQLException e){
            System.out.println("Couldn't close the connection to the database");
            return false;
        }
    }

    public List<String> menuAddOrder(){
        try{
            ResultSet resultSet = selectFoodItem.executeQuery();
            List<String> ItemNames = new ArrayList<>();
            while(resultSet.next()){
                String foodName = resultSet.getString(1);
                ItemNames.add(foodName);
            }
            return ItemNames;
        }catch (SQLException e){
            System.out.println("Query Failed");
            return null;
        }
    }

    public static double amount = 0;
    public AddOrderTable addItem(int OrderID, String foodName, int Quantity, String requests){
        try{
            foreignKeys.executeUpdate();
            insertIntoQuantityDetails.setInt(1,OrderID);
            insertIntoQuantityDetails.setString(2,foodName);
            insertIntoQuantityDetails.setInt(3,Quantity);
            insertIntoQuantityDetails.setString(4,requests);
            insertIntoQuantityDetails.executeUpdate();

            priceDetails.setString(1,foodName);
            ResultSet resultSet = priceDetails.executeQuery();
            resultSet.next();
            double price = resultSet.getDouble(1);
            System.out.println(price);

            AddOrderTable addOrderTable = new AddOrderTable();
            addOrderTable.setItemName(foodName);
            addOrderTable.setPrice(price);
            addOrderTable.setQuantity(Quantity);
            addOrderTable.setRequests(requests);


            amount = calculateForAdd(Quantity,price);
            return addOrderTable;
        }catch (SQLException e){
            System.out.println("Query Failed");
            return null;
        }
    }

    public AddOrderTable removeItem(int OrderID,String ItemName,int Quantity){
        try{
            quantityDetails.setInt(1,OrderID);
            quantityDetails.setString(2,ItemName);
            ResultSet resultSet = quantityDetails.executeQuery();
            resultSet.next();
            int QuantityGUI = resultSet.getInt(1);
            String requests = resultSet.getString(2);
            priceDetails.setString(1,ItemName);
            resultSet = priceDetails.executeQuery();
            resultSet.next();
            double price = resultSet.getDouble(1);
            AddOrderTable addOrderTable = new AddOrderTable();
            if(QuantityGUI - Quantity <= 0){
                foreignKeys.executeUpdate();
                removeFromQuantityDetails.setInt(1,OrderID);
                removeFromQuantityDetails.setString(2,ItemName);
                removeFromQuantityDetails.executeUpdate();
                addOrderTable = null;
            }else{
                foreignKeys.executeUpdate();
                updateQuantityDetails.setInt(1,QuantityGUI - Quantity);
                updateQuantityDetails.setInt(2,OrderID);
                updateQuantityDetails.setString(3,ItemName);
                updateQuantityDetails.executeUpdate();
                addOrderTable.setItemName(ItemName);
                addOrderTable.setPrice(price);
                addOrderTable.setQuantity(QuantityGUI - Quantity);
                addOrderTable.setRequests(requests);
            }
            amount = calculateForRemove(Quantity,price);
            return addOrderTable;
        }catch (SQLException e){
            System.out.println("Query Failed");
            return null;
        }
    }

    public double calculateForAdd(int Quantity,double price){
        amount = amount + Quantity * price;
        return amount;
    }

    public double calculateForRemove(int Quantity,double price){
        amount = amount - Quantity * price;
        return amount;
    }

    public void addOrder(int OrderID,Date date,Time time){
        try{
            addOrderToOrderDetails.setInt(1,OrderID);
            addOrderToOrderDetails.setDouble(2,amount);
            addOrderToOrderDetails.setTime(3,time);
            addOrderToOrderDetails.setDate(4,date);
            addOrderToOrderDetails.executeUpdate();
            amount = 0;

        }catch (SQLException e){
            System.out.println("Query Failed");
        }
    }

    public void cancelOrder(int OrderID){
        try{
            cancelOrderFromOrderDetails.setInt(1,OrderID);
            cancelOrderFromOrderDetails.executeUpdate();

            cancelFromQuantityDetails.setInt(1,OrderID);
            cancelFromQuantityDetails.executeUpdate();
        }catch (SQLException e){
            System.out.println("Query Failed");
        }
    }
}








