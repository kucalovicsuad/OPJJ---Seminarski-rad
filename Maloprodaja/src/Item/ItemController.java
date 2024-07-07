/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Item;

import Controller.Controller;
import Distributor.DistributorController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Suad's Laptop
 */
public class ItemController extends Controller {

    private Item item = new Item();
    private final DistributorController distributorController = new DistributorController();

    public ArrayList<Item> getAllItems(String storeId, String searchQuery) throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();

        String distributors = distributorController.getAllDistributorsByStore(storeId);

        String sql = "SELECT * FROM Artikal WHERE id_distributer IN (" + distributors + ")"
                + " AND (naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%')"
                + " ORDER BY naziv DESC";
        PreparedStatement query = getConn().prepareStatement(sql);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            Item itemsFromDB = new Item();

            itemsFromDB.setId_artikal(result.getInt("id_artikal"));
            itemsFromDB.setId_distributor(result.getInt("id_distributer"));
            itemsFromDB.setNaziv(result.getString("naziv"));
            itemsFromDB.setKolicina(result.getInt("kolicina"));
            itemsFromDB.setPopust(result.getDouble("popust"));
            itemsFromDB.setCijena_nabavke(result.getDouble("cijena_nabavke"));
            itemsFromDB.setCijena_prodaje(result.getDouble("cijena_prodaje"));

            allItems.add(itemsFromDB);
        }

        closeConnection();
        return allItems;
    }

    public boolean doesItemWithIdExist(String itemId, String itemQuantity) throws SQLException {
        String sql = "SELECT * FROM Artikal WHERE id_artikal = ? AND kolicina >= ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, itemId);
        query.setString(2, itemQuantity);
        ResultSet result = query.executeQuery();

        return result.next();
    }

    public boolean doesItemWithNameExist(String itemName, String itemQuantity) throws SQLException {
        String sql = "SELECT * FROM Artikal WHERE naziv = ? AND kolicina >= ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, itemName);
        query.setString(2, itemQuantity);
        ResultSet result = query.executeQuery();

        return result.next();
    }

    public Item getItemByName(String itemName, String storeId, String itemQuantity) throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();

        String distributors = distributorController.getAllDistributorsByStore(storeId);

        String sql = "SELECT * FROM Artikal WHERE id_distributer IN (" + distributors + ") AND naziv = ? AND kolicina >= ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, itemName);
        query.setString(2, itemQuantity);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            item.setId_artikal(result.getInt("id_artikal"));
            item.setId_distributor(result.getInt("id_distributer"));
            item.setNaziv(result.getString("naziv"));
            item.setKolicina(result.getInt("kolicina"));
            item.setPopust(result.getDouble("popust"));
            item.setCijena_nabavke(result.getDouble("cijena_nabavke"));
            item.setCijena_prodaje(result.getDouble("cijena_prodaje"));

            return item;
        }

        closeConnection();

        SQLException ex = new SQLException();
        throw ex;
    }

    public Item getItemById(String itemId, String storeId, String itemQuantity) throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();

        String distributors = distributorController.getAllDistributorsByStore(storeId);

        String sql = "SELECT * FROM Artikal WHERE id_distributer IN (" + distributors + ") AND id_artikal = ? AND kolicina >= ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, itemId);
        query.setString(2, itemQuantity);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            item.setId_artikal(result.getInt("id_artikal"));
            item.setId_distributor(result.getInt("id_distributer"));
            item.setNaziv(result.getString("naziv"));

            if (itemQuantity.equals("-1")) {
                item.setKolicina(result.getInt("kolicina"));
            } else {
                item.setKolicina(Integer.parseInt(itemQuantity));
            }

            item.setPopust(result.getDouble("popust"));
            item.setCijena_nabavke(result.getDouble("cijena_nabavke"));
            item.setCijena_prodaje(result.getDouble("cijena_prodaje"));

            return item;
        }

        closeConnection();

        SQLException ex = new SQLException();
        throw ex;
    }

    public String getItemName(String itemId) throws SQLException {
        String sql = "SELECT naziv FROM Artikal WHERE id_artikal = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, itemId);
        ResultSet result = query.executeQuery();

        return result.getString(1);
    }

    public boolean newQuantity(String itemId, int newQuantity) throws SQLException {
        String sql = "UPDATE Artikal SET kolicina = kolicina + " + newQuantity + " WHERE id_artikal = " + itemId;

        return InsDelUpd(sql);
    }

    public boolean addNewItem(Item newItem) throws SQLException {
        String sql = "INSERT INTO Artikal (id_distributer, naziv, kolicina, popust, cijena_nabavke, cijena_prodaje)"
                + " VALUES(" 
                + newItem.getId_distributor() + ",'"
                + newItem.getNaziv() + "',"
                + newItem.getKolicina() + ","
                + newItem.getPopust() + ","
                + newItem.getCijena_nabavke() + ","
                + newItem.getCijena_prodaje()
                + ")";
        
        return InsDelUpd(sql);
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }
}
