/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

import java.security.SecureRandom;
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
public class UserController extends Controller.Controller {

    private User user = new User();

    public UserController() {
    }

    public void userDetails(String userId) throws SQLException {
        String sql = "SELECT * FROM Zaposlenik WHERE id_zaposlenik = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, userId);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            getUser().setId_user(result.getInt("id_zaposlenik"));
            getUser().setId_maloprodaja(result.getInt("id_maloprodaja"));
            getUser().setIme(result.getString("ime"));
            getUser().setPrezime(result.getString("prezime"));
            getUser().setDatum_rodjenja(result.getString("datum_rodjenja"));
            getUser().setDatum_zaposlenja(result.getString("datum_zaposlenja"));
            getUser().setKontakt_broj(result.getString("kontakt_broj"));
            getUser().setEmail(result.getString("email"));
            getUser().setUsername(result.getString("username"));
            getUser().setPassword(null);
            getUser().setOvlasti(result.getString("ovlasti"));
            getUser().setPozicija(result.getString("pozicija"));
            getUser().setOpis_posla(result.getString("opis_posla"));
            getUser().setSatnica(result.getDouble("satnica"));
        }

        closeConnection();
    }

    public String randomPassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int STRING_LENGTH = 8;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public boolean isLoggedIn(String username, String password) throws SQLException {
        boolean isLoggedIn = false;

        String sql = "SELECT * FROM Zaposlenik WHERE (username = ? AND password = ?)";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, username);
        query.setString(2, password);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            getUser().setId_user(result.getInt("id_zaposlenik"));
            getUser().setId_maloprodaja(result.getInt("id_maloprodaja"));
            getUser().setIme(result.getString("ime"));
            getUser().setPrezime(result.getString("prezime"));
            getUser().setDatum_rodjenja(result.getString("datum_rodjenja"));
            getUser().setDatum_zaposlenja(result.getString("datum_zaposlenja"));
            getUser().setKontakt_broj(result.getString("kontakt_broj"));
            getUser().setEmail(result.getString("email"));
            getUser().setUsername(result.getString("username"));
            getUser().setPassword(result.getString("password"));
            getUser().setOvlasti(result.getString("ovlasti"));
            getUser().setPozicija(result.getString("pozicija"));
            getUser().setOpis_posla(result.getString("opis_posla"));
            getUser().setSatnica(result.getDouble("satnica"));

            isLoggedIn = true;
        }

        closeConnection();
        return isLoggedIn;
    }

    public String userNameAndSurname(String userId) throws SQLException {
        String userNameAndSurname;

        String sql = "SELECT ime,prezime FROM Zaposlenik WHERE id_zaposlenik = ? ";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, userId);
        ResultSet result = query.executeQuery();
        userNameAndSurname = result.getString("ime") + " " + result.getString("prezime");

        closeConnection();
        return userNameAndSurname;
    }

    public String storeId(String userId) throws SQLException {
        String storeId;

        String sql = "SELECT id_maloprodaja FROM Zaposlenik WHERE id_zaposlenik = ? ";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, userId);
        ResultSet result = query.executeQuery();
        storeId = result.getString(1);

        closeConnection();
        return storeId;
    }

    public Map<String, String> superAdminInfo() throws SQLException {
        Map<String, String> superAdminInfo = new HashMap<>();

        String sql = "SELECT ime,prezime,email,kontakt_broj FROM Zaposlenik WHERE ovlasti = 'superadmin' ";
        PreparedStatement query = getConn().prepareStatement(sql);
        ResultSet result = query.executeQuery();
        superAdminInfo.put("ime", result.getString("ime") + " " + result.getString("prezime"));
        superAdminInfo.put("kontakt", result.getString("email") + ", " + result.getString("kontakt_broj"));

        closeConnection();
        return superAdminInfo;
    }

    public ArrayList<Map<String, String>> getAllUsers() throws SQLException {
        ArrayList<Map<String, String>> allUsers = new ArrayList<>();

        String sql = "SELECT id_zaposlenik, ime, prezime, ovlasti FROM Zaposlenik WHERE ovlasti != ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, "zaposlenik");
        ResultSet result = query.executeQuery();

        while (result.next()) {
            Map<String, String> userFromDB = new HashMap<>();

            userFromDB.put("id_zaposlenik", Integer.toString(result.getInt("id_zaposlenik")));
            userFromDB.put("imeIPrezime", result.getString("ime") + " " + result.getString("prezime"));
            userFromDB.put("ovlasti", result.getString("ovlasti"));

            allUsers.add(userFromDB);
        }

        closeConnection();
        return allUsers;
    }

    public ArrayList<User> getAllUsersInfo(String storeId, String currentUserPrivilege, String currentUserId, String searchQuery) throws SQLException {
        ArrayList<User> allUsers = new ArrayList<>();
        String sql = null;
        PreparedStatement query = null;
        ResultSet result = null;
        
        sql = "SELECT * FROM Zaposlenik WHERE id_maloprodaja = " + storeId + " AND ((ime LIKE '" + searchQuery + "%' OR ime LIKE '% " + searchQuery + "%')"
                + " OR (prezime LIKE '" + searchQuery + "%' OR prezime LIKE '% " + searchQuery + "%')"
                + " OR (username LIKE '" + searchQuery + "%' OR username LIKE '% " + searchQuery + "%')"
                + " OR (id_zaposlenik LIKE '" + searchQuery + "%' OR id_zaposlenik LIKE '% " + searchQuery + "%')) ORDER BY "
                + " CASE ovlasti "
                + " WHEN 'superadmin' THEN 1"
                + " WHEN 'admin' THEN 2"
                + " WHEN 'vlasnik' THEN 3"
                + " WHEN 'zaposlenik' THEN 4"
                + " ELSE 5"
                + " END;";
        query = getConn().prepareStatement(sql);
        result = query.executeQuery();

        while (result.next()) {
            User userFromDB = new User();

            userFromDB.setId_user(result.getInt("id_zaposlenik"));
            userFromDB.setId_maloprodaja(result.getInt("id_maloprodaja"));
            userFromDB.setIme(result.getString("ime"));
            userFromDB.setPrezime(result.getString("prezime"));
            userFromDB.setDatum_rodjenja(result.getString("datum_rodjenja"));
            userFromDB.setDatum_zaposlenja(result.getString("datum_zaposlenja"));
            userFromDB.setEmail(result.getString("email"));
            userFromDB.setKontakt_broj(result.getString("kontakt_broj"));
            userFromDB.setUsername(result.getString("username"));
            userFromDB.setPassword(null);
            userFromDB.setOvlasti(result.getString("ovlasti"));
            userFromDB.setPozicija(result.getString("pozicija"));
            userFromDB.setOpis_posla(result.getString("opis_posla"));
            userFromDB.setSatnica(result.getDouble("satnica"));

            allUsers.add(userFromDB);
        }

        closeConnection();
        return allUsers;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
