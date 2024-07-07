/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CashRegister;

import Controller.Controller;
import Store.Store;
import Store.StoreController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Suad's Laptop
 */
public class RegisterController extends Controller {

    private Register register = new Register();

    public RegisterController() {
    }

    public boolean openRegister(Register register) throws SQLException {
        String sqlQuery = "INSERT INTO Kasa "
                + "VALUES ('" + register.getDatum() + "',"
                + " '" + register.getId_maloprodaja() + "',"
                + " '" + register.getVrijeme_otvaranja() + "',"
                + " '" + register.getVrijeme_zatvaranja() + "',"
                + " '" + register.getDepozit() + "',"
                + " '" + register.getPromet() + "',"
                + " '" + register.getId_zaposlenik_otvaranja() + "',"
                + " '" + register.getId_zaposlenik_zatvaranja() + "')";

        try {
            if (InsDelUpd(sqlQuery)) {
                closeConnection();
                return true;
            } else {
                SQLException ex = new SQLException();
                throw ex;
            }
        } catch (SQLException ex) {
            closeConnection();
            return false;
        }
    }

    public boolean reopenRegister(String date, String storeId, Double deposit) throws SQLException {
        String sql = "UPDATE Kasa SET id_zaposlenik_zatvaranje = 0"
                + ", vrijeme_zatvaranja = '-'"
                + ", depozit = " + deposit
                + ", promet = 0"
                + " WHERE datum = '" + date + "' AND id_maloprodaja = " + storeId;

        Controller dbController = new Controller();

        return dbController.InsDelUpd(sql);
    }

    public boolean wasClosedToday(String date, String storeId) throws SQLException {
        String sql = "SELECT * FROM Kasa WHERE datum = ? AND id_maloprodaja = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, date);
        query.setString(2, storeId);
        ResultSet result = query.executeQuery();

        closeConnection();
        return result.next();
    }

    public boolean isOpen(String storeId) throws SQLException {
        String sql = "SELECT * FROM Kasa WHERE id_maloprodaja = ? AND vrijeme_zatvaranja = '-'";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        ResultSet result = query.executeQuery();

        closeConnection();
        return result.next();
    }

    public boolean wasLeftOpen(String storeId, String userId) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d. MMM. yyyy.");
        String dateNow = currentDate.format(format);

        String sql = "SELECT * FROM Kasa WHERE id_maloprodaja = ? AND vrijeme_zatvaranja = '-' AND datum = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        query.setString(2, dateNow);
        ResultSet result = query.executeQuery();

        if (result.next()) {
            return false;
        } else {
            sql = "UPDATE Kasa SET vrijeme_zatvaranja = '23:59'"
                    + ", id_zaposlenik_zatvaranje = " + userId
                    + ", promet = COALESCE((SELECT SUM(ukupna_cijena) AS promet FROM Racun WHERE id_maloprodaja = " + storeId
                    + " AND datum = (SELECT datum FROM Kasa WHERE id_maloprodaja = " + storeId + " AND vrijeme_zatvaranja = '-')), 0)"
                    + " WHERE id_maloprodaja = " + storeId + " AND vrijeme_zatvaranja = '-' AND datum != '" + dateNow + "'";

            return InsDelUpd(sql);
        }
    }

    public ArrayList<Register> getAllRegisters(String storeId, String userId, String currentUserPrivilege, String searchQuery) throws SQLException {
        ArrayList<Register> allRegisters = new ArrayList<>();
        String sql = null;
        PreparedStatement query = null;
        ResultSet result = null;

        sql = "SELECT * FROM Kasa WHERE id_maloprodaja = ? AND (datum LIKE '" + searchQuery + "%' OR datum LIKE '% " + searchQuery + "%')"
                + " ORDER BY SUBSTR(datum, -4) || '-' ||"
                + " CASE SUBSTR(datum, INSTR(datum, '. ') + 2, 3)"
                + " WHEN 'Jan' THEN '01'"
                + " WHEN 'Feb' THEN '02'"
                + " WHEN 'Mar' THEN '03'"
                + " WHEN 'Apr' THEN '04'"
                + " WHEN 'Maj' THEN '05'"
                + " WHEN 'Jun' THEN '06'"
                + " WHEN 'Jul' THEN '07'"
                + " WHEN 'Avg' THEN '08'"
                + " WHEN 'Sep' THEN '09'"
                + " WHEN 'Okt' THEN '10'"
                + " WHEN 'Nov' THEN '11'"
                + " WHEN 'Dec' THEN '12'"
                + " END || '-' ||"
                + " CASE LENGTH(SUBSTR(datum, 1, INSTR(datum, '. ') - 1))"
                + " WHEN 1 THEN '0' || SUBSTR(datum, 1, INSTR(datum, '. ') - 1)"
                + " ELSE SUBSTR(datum, 1, INSTR(datum, '. ') - 1)"
                + " END DESC";
        query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        result = query.executeQuery();

        while (result.next()) {
            Register registerFromDB = new Register();

            registerFromDB.setDatum(result.getString("datum"));
            registerFromDB.setId_maloprodaja(result.getInt("id_maloprodaja"));
            registerFromDB.setVrijeme_otvaranja(result.getString("vrijeme_otvaranja"));
            registerFromDB.setVrijeme_zatvaranja(result.getString("vrijeme_zatvaranja"));
            registerFromDB.setDepozit(result.getDouble("depozit"));
            registerFromDB.setId_zaposlenik_otvaranja(result.getInt("id_zaposlenik_otvaranje"));
            registerFromDB.setId_zaposlenik_zatvaranja(result.getInt("id_zaposlenik_zatvaranje"));
            registerFromDB.setPromet(result.getDouble("promet"));

            allRegisters.add(registerFromDB);
        }

        closeConnection();
        return allRegisters;
    }

    public boolean closeRegister(String date, String storeId, String userId, String time) throws SQLException {
        String sql = null;
        PreparedStatement query = null;
        ResultSet result = null;

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d. MMM. yyyy.");
        String dateNow = currentDate.format(format);

        sql = "SELECT SUM(ukupna_cijena) AS promet FROM Racun WHERE id_maloprodaja = ? AND datum = ?";
        query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        query.setString(2, date);
        result = query.executeQuery();

        if (result.next()) {
            sql = "UPDATE Kasa SET id_zaposlenik_zatvaranje = " + userId
                    + ", vrijeme_zatvaranja = '" + time
                    + "', promet = " + result.getDouble(1)
                    + " WHERE id_maloprodaja = " + storeId + " AND vrijeme_zatvaranja = '-'";
        } else {
            sql = "UPDATE Kasa SET id_zaposlenik_zatvaranje = " + userId
                    + ", vrijeme_zatvaranja = '" + time
                    + "', promet = 0"
                    + " WHERE id_maloprodaja = " + storeId + " AND vrijeme_zatvaranja = '-'";
        }

        Controller dbController = new Controller();

        return dbController.InsDelUpd(sql);
    }

    public boolean closeAllRegisters(String date, String userId, String time) throws SQLException {
        String storeList = "";

        StoreController storeController = new StoreController();
        ArrayList<Store> stores = storeController.storesByUser(userId, "");

        ArrayList<String> storeIds = new ArrayList<>();
        for (Store store : stores) {
            storeIds.add(Integer.toString(store.getId_maloprodaja()));
        }

        storeList = String.join(", ", storeIds);

        String sql = "UPDATE Kasa SET id_zaposlenik_zatvaranje = " + userId
                + ", vrijeme_zatvaranja = '" + time
                + "', promet = 0 "
                + "WHERE id_maloprodaja IN (" + storeList + ") AND vrijeme_zatvaranja = '-'";

        Controller dbController = new Controller();

        return dbController.InsDelUpd(sql);
    }

    /**
     * @return the register
     */
    public Register getRegister() {
        return register;
    }

    /**
     * @param register the register to set
     */
    public void setRegister(Register register) {
        this.register = register;
    }
}
