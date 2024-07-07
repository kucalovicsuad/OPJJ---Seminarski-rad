/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Store;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Suad's Laptop
 */
public class StoreController extends Controller.Controller {

    public StoreController() {
    }

    public String storeName(String storeId) throws SQLException {
        String storeName = null;

        String sql = "SELECT naziv FROM Maloprodaja WHERE id_maloprodaja = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        ResultSet result = query.executeQuery();
        storeName = result.getString(1);

        closeConnection();
        return storeName;
    }
    
    public String numOfEmployees (String storeId) throws SQLException {
        
        String sql = "SELECT COUNT(*) FROM Zaposlenik WHERE id_maloprodaja = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        ResultSet result = query.executeQuery();
        
        String numOfEmployees = result.getString(1);
        
        closeConnection();
        return numOfEmployees;
    }

    public ArrayList<Store> storesByUser(String userId, String searchQuery) throws SQLException {
        ArrayList<Store> stores = new ArrayList();

        String sql = "SELECT ovlasti FROM Zaposlenik WHERE id_zaposlenik = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, userId);
        ResultSet result = query.executeQuery();
        String ovlasti = result.getString(1);
        closeConnection();

        if ("superadmin".equals(ovlasti)) {
            sql = "SELECT * FROM Maloprodaja WHERE naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%'";
            query = getConn().prepareStatement(sql);
            result = query.executeQuery();
        } else if ("admin".equals(ovlasti) || "vlasnik".equals(ovlasti)) {
            sql = "SELECT * FROM Maloprodaja WHERE (id_vlasnik = ? OR id_admin = ?) AND (naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%')";
            query = getConn().prepareStatement(sql);
            query.setString(1, userId);
            query.setString(2, userId);
            result = query.executeQuery();
        } else {
            sql = "SELECT id_maloprodaja FROM Zaposlenik WHERE id_zaposlenik = ?";
            query = getConn().prepareStatement(sql);
            query.setString(1, userId);
            result = query.executeQuery();

            sql = "SELECT * FROM Maloprodaja WHERE id_maloprodaja = ? AND (naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%')";
            query = getConn().prepareStatement(sql);
            query.setString(1, result.getString(1));
            result = query.executeQuery();
        }

        while (result.next()) {
            stores.add(new Store(result.getInt("id_maloprodaja"), result.getInt("id_vlasnik"), result.getInt("id_admin"),
                    result.getString("naziv"), result.getString("adresa"), result.getString("grad"), result.getString("drzava"),
                    result.getString("email"), result.getString("kontakt_broj")));
        }

        closeConnection();
        return stores;
    }

    public Map<String, String> storeOwnerAndAdmin(String storeId) throws SQLException {
        Map<String, String> storeOwnerAndAdmin = new HashMap<>();

        String sql = "SELECT id_vlasnik, id_admin FROM Maloprodaja WHERE id_maloprodaja = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        ResultSet result = query.executeQuery();

        storeOwnerAndAdmin.put("id_vlasnik", result.getString("id_vlasnik"));
        storeOwnerAndAdmin.put("id_admin", result.getString("id_admin"));

        closeConnection();

        sql = "SELECT ime, prezime FROM Zaposlenik WHERE id_zaposlenik = ?";
        query = getConn().prepareStatement(sql);
        query.setString(1, storeOwnerAndAdmin.get("id_vlasnik"));
        result = query.executeQuery();

        storeOwnerAndAdmin.put("vlasnikName", result.getString("ime") + " " + result.getString("prezime"));

        sql = "SELECT ime, prezime FROM Zaposlenik WHERE id_zaposlenik = ?";
        query = getConn().prepareStatement(sql);
        query.setString(1, storeOwnerAndAdmin.get("id_admin"));
        result = query.executeQuery();

        storeOwnerAndAdmin.put("adminName", result.getString("ime") + " " + result.getString("prezime"));

        closeConnection();
        return storeOwnerAndAdmin;
    }
}
