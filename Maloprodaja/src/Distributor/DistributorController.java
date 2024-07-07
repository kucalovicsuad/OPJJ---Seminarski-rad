/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Distributor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mahmu
 */
public class DistributorController extends Controller.Controller {

    public String distributorName(String distributorId) throws SQLException {
        String distributorName = null;

        String sql = "SELECT naziv FROM Distributer WHERE id_distributer = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, distributorId);
        ResultSet result = query.executeQuery();
        distributorName = result.getString(1);

        closeConnection();
        return distributorName;
    }
    
    public String getAllDistributorsByStore (String storeId) throws SQLException {
        String distributorList = "";

        String sql = "SELECT * FROM Maloprodaja_Distributer WHERE id_maloprodaja = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        ResultSet result = query.executeQuery();

        ArrayList<String> distributorIds = new ArrayList<>();

        while (result.next()) {
            distributorIds.add(result.getString("id_distributer"));
        }

        distributorList = String.join(", ", distributorIds);
        
        return distributorList;
    }

    public ArrayList<Distributor> distributorsByUser(String userId, String storeId, String searchQuery) throws SQLException {
        ArrayList<Distributor> distributors = new ArrayList();

        String sql = "SELECT ovlasti FROM Zaposlenik WHERE id_zaposlenik = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, userId);
        ResultSet result = query.executeQuery();
        String ovlasti = result.getString(1);
        closeConnection();

        if ("superadmin".equals(ovlasti)) {
            sql = "SELECT * FROM Distributer WHERE naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%'";
            query = getConn().prepareStatement(sql);
            result = query.executeQuery();
        } else {
            sql = "SELECT id_distributer FROM Maloprodaja_Distributer WHERE id_maloprodaja = ?";
            query = getConn().prepareStatement(sql);
            query.setString(1, storeId);
            result = query.executeQuery();

            String allDistributorIds = null;
            ArrayList<String> allDistributorIdsInArray = new ArrayList<>();

            while (result.next()) {
                allDistributorIdsInArray.add(result.getString("id_distributer"));
            }

            allDistributorIds = String.join(", ", allDistributorIdsInArray);

            closeConnection();

            sql = "SELECT * FROM Distributer WHERE id_distributer IN (" + allDistributorIds + ") "
                    + "AND naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%'";
            query = getConn().prepareStatement(sql);
            result = query.executeQuery();
        }

        while (result.next()) {
            distributors.add(new Distributor(result.getInt("id_distributer"), result.getString("naziv"), result.getString("adresa"),
                    result.getString("grad"), result.getString("drzava"), result.getString("email"), result.getString("kontakt_broj")));
        }

        closeConnection();
        return distributors;
    }
    
    public ArrayList<Distributor> getAllDistributors(String storeId, String searchQuery) throws SQLException {
        ArrayList<Distributor> allDistributors = new ArrayList<>();
        String distributorList = "";
        String sql = null;
        PreparedStatement query = null;
        ResultSet result = null;

        sql = "SELECT id_distributer FROM Maloprodaja_Distributer WHERE id_maloprodaja = ?";
        query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        result = query.executeQuery();
        ArrayList<String> distributorIds = new ArrayList<>();
        while (result.next()) {
            distributorIds.add(result.getString("id_distributer"));
        }

        distributorList = String.join(", ", distributorIds);

        sql = "SELECT * FROM Distributer WHERE id_distributer IN (" + distributorList + ") "
                + "AND (id_distributer LIKE '" + searchQuery + "%' OR id_distributer LIKE '% " + searchQuery + "%'"
                + " OR naziv LIKE '" + searchQuery + "%' OR naziv LIKE '% " + searchQuery + "%'"
                + " OR email LIKE '" + searchQuery + "%' OR email LIKE '% " + searchQuery + "%'"
                + " OR kontakt_broj LIKE '" + searchQuery + "%' OR kontakt_broj LIKE '% " + searchQuery + "%')";
        query = getConn().prepareStatement(sql);
        result = query.executeQuery();

        while (result.next()) {
            Distributor distributorFromDB = new Distributor();

            distributorFromDB.setId_distributer(result.getInt("id_distributer"));
            distributorFromDB.setNaziv(result.getString("naziv"));
            distributorFromDB.setAdresa(result.getString("adresa"));
            distributorFromDB.setGrad(result.getString("grad"));
            distributorFromDB.setDrzava(result.getString("drzava"));
            distributorFromDB.setEmail(result.getString("email"));
            distributorFromDB.setKontakt_broj(result.getString("kontakt_broj"));

            allDistributors.add(distributorFromDB);
        }

        closeConnection();
        return allDistributors;
    }

    public boolean addNewDistributor(Distributor newDistributor, String storeId) throws SQLException {
        String sql = "INSERT INTO Distributer (naziv, adresa, grad, drzava, email, kontakt_broj)"
                + " VALUES ('" + newDistributor.getNaziv() + "',"
                + "'" + newDistributor.getAdresa() + "',"
                + "'" + newDistributor.getGrad() + "',"
                + "'" + newDistributor.getDrzava() + "',"
                + "'" + newDistributor.getEmail() + "',"
                + "'" + newDistributor.getKontakt_broj() + "')";

        if (InsDelUpd(sql)) {
            return addDistributorToStore(newDistributor, storeId);
        }

        SQLException ex = new SQLException();
        throw ex;
    }

    public boolean doesDistributerAlreadyExist(Distributor distributor) throws SQLException {
        String sql = "SELECT id_distributer FROM Distributer WHERE naziv = '" + distributor.getNaziv()
                + "' AND kontakt_broj = '" + distributor.getKontakt_broj() + "'";
        PreparedStatement query = getConn().prepareStatement(sql);
        ResultSet result = query.executeQuery();

        return result.next();
    }

    public boolean addDistributorToStore(Distributor distributor, String storeId) throws SQLException {
        String sql = "SELECT id_distributer FROM Distributer WHERE naziv = '" + distributor.getNaziv()
                + "' AND kontakt_broj = '" + distributor.getKontakt_broj() + "'";
        PreparedStatement query = getConn().prepareStatement(sql);
        ResultSet result = query.executeQuery();

        sql = "INSERT INTO Maloprodaja_Distributer "
                + " VALUES (" + storeId + ", "
                + result.getInt("id_distributer") + ")";

        return InsDelUpd(sql);
    }

    public boolean ifDistributorAndStoreAreConnected(Distributor distributor, String storeId) throws SQLException {
        String sql = "SELECT id_distributer FROM Distributer WHERE naziv = '" + distributor.getNaziv()
                + "' AND kontakt_broj = '" + distributor.getKontakt_broj() + "'";
        PreparedStatement query = getConn().prepareStatement(sql);
        ResultSet result = query.executeQuery();
        
        sql = "SELECT * FROM Maloprodaja_Distributer WHERE id_maloprodaja = " + storeId + " AND id_distributer = " + result.getInt("id_distributer");
        query = getConn().prepareStatement(sql);
        result = query.executeQuery();
        
        return result.next();
    }
}
