package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceMenuManager {

    String host = "jdbc:mysql://localhost:3306/restaurantdatabase? autoReconnect=true&useSSL=false";
    String uName = "root";
    String uPass = "Yash@0812";

    private Connection connection;
    public static final String TABLEFoodItem = "fooditem";
    private static DataSourceMenuManager instance = new DataSourceMenuManager();
    PreparedStatement queryMenuItems;
    PreparedStatement insertItem;
    PreparedStatement updatePriceOfItem;
    PreparedStatement insertItemInTable;
    PreparedStatement deleteItemFromTable;
    PreparedStatement queryTableItems;
    PreparedStatement updatePrice;
    PreparedStatement selectItems;

    public DataSourceMenuManager(){
        this.instance = getInstance();
    }

    public static DataSourceMenuManager getInstance(){
        return instance;
    }

    public boolean open(){
        try{
            connection = DriverManager.getConnection(host,uName,uPass);
            queryMenuItems = connection.prepareStatement("select * from " + TABLEFoodItem + " where ItemName = ?");
            insertItem = connection.prepareStatement("insert into " + TABLEFoodItem + " values(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            updatePriceOfItem = connection.prepareStatement("update fooditem set ItemPrice = ? where ItemName = ?");
            insertItemInTable = connection.prepareStatement("insert into menutable values(?,?)");
            deleteItemFromTable = connection.prepareStatement("delete from menutable where ItemName = ?");
            queryTableItems = connection.prepareStatement("select * from menutable where ItemName = ?");
            updatePrice = connection.prepareStatement("update menutable set ItemPrice = ? where ItemName = ?");
            selectItems = connection.prepareStatement("select * from menutable");
            return true;
        }catch (SQLException e){
            System.out.println("Couldn't connect to the database");
            return false;
        }
    }

    public boolean close(){
        try{
            if(selectItems != null){
                selectItems.close();
            }
            if(updatePrice != null){
                updatePrice.close();
            }
            if(insertItemInTable != null){
                insertItemInTable.close();
            }
            if(deleteItemFromTable != null){
                deleteItemFromTable.close();
            }
            if(queryTableItems != null){
                queryTableItems.close();
            }
            if(updatePriceOfItem != null){
                updatePriceOfItem.close();
            }
            if(insertItem != null){
                insertItem.close();
            }
            if(queryMenuItems != null){
                queryMenuItems.close();
            }
            if(connection != null){
                connection.close();
            }
            return true;
        }catch (SQLException e){
            System.out.println("Couldn't close the connection");
            return false;
        }
    }

    public List<FoodItem> selectItems(){
        try{
            ResultSet resultSet = selectItems.executeQuery();
            List<FoodItem> foodItems = new ArrayList<>();
            while(resultSet.next()){
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodName(resultSet.getString(1));
                foodItem.setFoodPrice(resultSet.getDouble(2));
                foodItems.add(foodItem);
            }
            return foodItems;
        }catch (SQLException e){
            System.out.println("Query Failed");
            return null;
        }
    }

    public List<FoodItem> addFoodItemToMenu(String foodName, double foodPrice){
        try{
            queryMenuItems.setString(1,foodName);
            queryTableItems.setString(1,foodName);
            ResultSet resultSet = queryMenuItems.executeQuery();
            ResultSet resultSet1 = queryTableItems.executeQuery();

            List<FoodItem> foodItems = new ArrayList<>();

            if(resultSet.next()){
                if(resultSet.getDouble("ItemPrice") == foodPrice){

                }else {
                    updatePriceOfItem.setDouble(1,foodPrice);
                    updatePriceOfItem.setString(2,foodName);
                    updatePriceOfItem.executeUpdate();

                }
            }else{
                insertItem.setString(1, foodName);
                insertItem.setDouble(2, foodPrice);
                insertItem.executeUpdate();
            }

            if(resultSet1.next()){
                if(resultSet1.getDouble("ItemPrice") == foodPrice){

                }else {
                    updatePrice.setDouble(1,foodPrice);
                    updatePrice.setString(2,foodName);
                    updatePrice.executeUpdate();
                }
            }else{
                insertItemInTable.setString(1, foodName);
                insertItemInTable.setDouble(2, foodPrice);
                insertItemInTable.executeUpdate();
            }
            ResultSet resultSet2 = selectItems.executeQuery();
            while(resultSet2.next()){
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodName(resultSet2.getString(1));
                foodItem.setFoodPrice(resultSet2.getDouble(2));
                foodItems.add(foodItem);
            }
            return foodItems;
        }catch (SQLException e){
            System.out.println("Query Failed to Add Item in Menu");
            return null;
        }
    }

    public List<FoodItem> removeFoodItemFromMenu(String foodName){
        try{
            deleteItemFromTable.setString(1,foodName);
            deleteItemFromTable.executeUpdate();
            List<FoodItem> foodItems = new ArrayList<>();
            foodItems = selectItems();
            return foodItems;
        }catch (SQLException e){
            System.out.println("Query Failed");
            return null;
        }
    }

}








