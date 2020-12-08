package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceOrderDetails {
    String host = "jdbc:mysql://localhost:3306/restaurantdatabase? autoReconnect=true&useSSL=false";
    String uName = "root";
    String uPass = "Yash@0812";

    private Connection connection;
    public static final String TABLEOrderDetails = "orderdetails";
    private static DataSourceOrderDetails instance = new DataSourceOrderDetails();
    private Statement querySelectOrders;
    private PreparedStatement querySelectOrdersBasedOnDate;
    private PreparedStatement querySelectOrdersBasedOnOrderID;
    private PreparedStatement queryTotalSales;
    private PreparedStatement querySelectOrdersBasedOnDateAndOrderID;

    public DataSourceOrderDetails(){
        this.instance = getInstance();
    }
    private static DataSourceOrderDetails getInstance(){return instance;}

    public boolean open(){
        try{
            connection  = DriverManager.getConnection(host,uName,uPass);
            querySelectOrders = connection.createStatement();

            querySelectOrdersBasedOnDate = connection.prepareStatement(
                    "select * from " + TABLEOrderDetails + " where OrderDate between ? and ?");

            querySelectOrdersBasedOnOrderID = connection.prepareStatement("select * from " + TABLEOrderDetails +
                    " where OrderID = ?");

            queryTotalSales = connection.prepareStatement("select sum(amount) from " + TABLEOrderDetails +
                    " where OrderDate between ? and ?");

            querySelectOrdersBasedOnDateAndOrderID = connection.prepareStatement("select * from " + TABLEOrderDetails +
                    " where OrderDate between ? and ? and OrderID = ?");

            return true;
        }catch (SQLException e){
            System.out.println("Couldn't connect to the database");
            return false;
        }
    }

    public boolean close(){
        try{
            if(querySelectOrdersBasedOnDateAndOrderID != null){
                querySelectOrdersBasedOnDateAndOrderID.close();
            }
            if(queryTotalSales != null){
                queryTotalSales.close();
            }
            if(querySelectOrdersBasedOnOrderID != null){
                querySelectOrdersBasedOnOrderID.close();
            }
            if(querySelectOrdersBasedOnDate != null){
                querySelectOrdersBasedOnDate.close();
            }
            if(querySelectOrders != null){
                querySelectOrders.close();
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

    public List<Orders> selectOrdersDetails(){
        try(ResultSet resultSet = querySelectOrders.executeQuery("select * from orderdetails")){
            List<Orders> ordersList = new ArrayList<>();
            while(resultSet.next()){
                Orders orders = new Orders(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getTime(3),resultSet.getDate(4));
                ordersList.add(orders);
            }
            return ordersList;
        }catch (SQLException e){
            System.out.println("Query Failed Of Select Order Details");
            return null;
        }
    }

    public List<Orders> selectOrderDetailsBasedOnDate(Date firstDate,Date secondDate){
        try{

            querySelectOrdersBasedOnDate.setDate(1, firstDate);
            querySelectOrdersBasedOnDate.setDate(2,secondDate);
            ResultSet resultSet = querySelectOrdersBasedOnDate.executeQuery();
            List<Orders> ordersList = new ArrayList<>();
            while(resultSet.next()){
                Orders orders = new Orders(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getTime(3),resultSet.getDate(4));
                ordersList.add(orders);
            }
            return ordersList;
        }catch (SQLException e){
            System.out.println("Query Failed Of Select Order Details Based On Date");
            return null;
        }
    }

    public List<Orders> selectOrderDetailsBasedOnOrderID(int OrderID){
        try{
            querySelectOrdersBasedOnOrderID.setInt(1,OrderID);
            ResultSet resultSet = querySelectOrdersBasedOnOrderID.executeQuery();
            List<Orders> ordersList = new ArrayList<>();
            while(resultSet.next()){
                Orders orders = new Orders(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getTime(3),resultSet.getDate(4));
                ordersList.add(orders);
            }
            return ordersList;
        }catch (SQLException e){
            System.out.println("Query Failed of Select Order Details Based On OrderID");
            return null;
        }
    }

    public double queryTotalSalesOrders(Date firstDate,Date secondDate){
        try{
            queryTotalSales.setDate(1,firstDate);
            queryTotalSales.setDate(2,secondDate);
            ResultSet resultSet = queryTotalSales.executeQuery();
            double amount = 0;
            while(resultSet.next()){
                amount = resultSet.getDouble(1);
            }
            return amount;
        }catch (SQLException e){
            System.out.println("Query Failed Of Select Order Details Based On Date");
            return Double.parseDouble(null);
        }
    }

    public List<Orders> selectOrderDetailsBasedOnDateAndOrderID(Date firstDate,Date secondDate,int OrderID){
        try{
            querySelectOrdersBasedOnDateAndOrderID.setDate(1,firstDate);
            querySelectOrdersBasedOnDateAndOrderID.setDate(2,secondDate);
            querySelectOrdersBasedOnDateAndOrderID.setInt(3,OrderID);
            ResultSet resultSet = querySelectOrdersBasedOnDateAndOrderID.executeQuery();
            List<Orders> ordersList = new ArrayList<>();
            while(resultSet.next()){
                Orders orders = new Orders(resultSet.getInt(1), resultSet.getDouble(2),resultSet.getTime(3),resultSet.getDate(4));
                ordersList.add(orders);
            }
            return ordersList;
        }catch (SQLException e){
            System.out.println("Query Failed of Select Order Details Based On Date and OrderID");
            return null;
        }
    }
}









