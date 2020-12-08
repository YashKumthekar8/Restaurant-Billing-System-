package sample;

import java.sql.*;
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
            return true;
        }catch (SQLException e){
            System.out.println("Couldn't connect to the database");
            return false;
        }
    }

    public boolean close(){
        try{
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

    public FoodItem addFoodItemToMenu(String foodName, double foodPrice){
        try{
            queryMenuItems.setString(1,foodName);
            ResultSet resultSet = queryMenuItems.executeQuery();

            FoodItem foodItem = new FoodItem();

            if(resultSet.next()){
                if(resultSet.getDouble("ItemPrice") == foodPrice){
                    foodItem.setFoodName(resultSet.getString(1));
                    foodItem.setFoodPrice(resultSet.getDouble(2));
                }else {
                    updatePriceOfItem.setDouble(1,foodPrice);
                    updatePriceOfItem.setString(2,foodName);
                    updatePriceOfItem.executeUpdate();
                    foodItem.setFoodName(foodName);
                    foodItem.setFoodPrice(foodPrice);
                }
            }else{
                insertItem.setString(1, foodName);
                insertItem.setDouble(2, foodPrice);
                foodItem.setFoodName(foodName);
                foodItem.setFoodPrice(foodPrice);
                int rowsAffected = insertItem.executeUpdate();
            }
            return foodItem;
        }catch (SQLException e){
            System.out.println("Query Failed to Add Item in Menu");
            return null;
        }
    }

    public List<FoodItem> removeFoodItemFromMenu(List<FoodItem> items,String foodName){
        for(FoodItem item : items){
            if(item.getFoodName().equals(foodName)){
                items.remove(item);
            }
        }
        return items;
    }

}








