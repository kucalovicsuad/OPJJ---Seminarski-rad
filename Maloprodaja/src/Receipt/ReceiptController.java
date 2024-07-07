/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receipt;

import Controller.Controller;
import Item.ItemController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Suad's Laptop
 */
public class ReceiptController extends Controller {

    private Receipt receipt = new Receipt();

    private final ItemController itemController = new ItemController();

    public boolean addNewReceipt(Receipt newReceipt) throws SQLException {
        String sql = "INSERT INTO Racun (id_maloprodaja, id_zaposlenik, datum, vrijeme, ukupna_cijena, ukupni_popust, pdv)"
                + " VALUES (" + newReceipt.getId_maloprodaja()
                + ", " + newReceipt.getId_zaposlenik()
                + ", '" + newReceipt.getDatum()
                + "', '" + newReceipt.getVrijeme()
                + "', " + newReceipt.getUkupna_cijena()
                + ", " + newReceipt.getUkupni_popust()
                + ", " + newReceipt.getPdv()
                + ")";

        if (InsDelUpd(sql)) {
            sql = "SELECT id_racun FROM Racun WHERE id_maloprodaja = ? AND id_zaposlenik = ? AND datum = ? AND vrijeme = ?";
            PreparedStatement query = getConn().prepareStatement(sql);
            query.setString(1, Integer.toString(newReceipt.getId_maloprodaja()));
            query.setString(2, Integer.toString(newReceipt.getId_zaposlenik()));
            query.setString(3, newReceipt.getDatum());
            query.setString(4, newReceipt.getVrijeme());
            ResultSet result = query.executeQuery();

            System.out.println(newReceipt.getItems().size());

            for (int i = 0; i < newReceipt.getItems().size(); i++) {
                sql = "INSERT INTO Racun_Artikal"
                        + " VALUES (" + result.getInt(1)
                        + ", " + newReceipt.getItems().get(i).getId_artikal()
                        + ", " + newReceipt.getItems().get(i).getPopust()
                        + ", " + newReceipt.getItems().get(i).getKolicina()
                        + ", " + newReceipt.getItems().get(i).getUkupna_cijena()
                        + ")";

                System.out.println(i);
                if (InsDelUpd(sql)) {
                    sql = "UPDATE Artikal SET kolicina = kolicina - " + newReceipt.getItems().get(i).getKolicina();

                    if (!InsDelUpd(sql)) {
                        SQLException ex = new SQLException();
                        throw ex;
                    }
                } else {
                    SQLException ex = new SQLException();
                    throw ex;
                }
            }

            return true;
        } else {
            SQLException ex = new SQLException();
            throw ex;
        }
    }

    public ArrayList<Receipt> getAllReceipts(String storeId, String searchQuery) throws SQLException {
        ArrayList<Receipt> allReceipts = new ArrayList<>();

        String sql = "SELECT * FROM Racun WHERE id_maloprodaja = ? AND (id_racun LIKE '" + searchQuery + "%' OR id_racun LIKE '% " + searchQuery + "%'"
                + " OR datum LIKE '" + searchQuery + "%' OR datum LIKE '% " + searchQuery + "%')"
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
                + " END,"
                + " vrijeme DESC";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, storeId);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            Receipt receiptFromDB = new Receipt();
            ArrayList<ReceiptItem> items = new ArrayList<>();

            receiptFromDB.setId_racun(result.getInt("id_racun"));
            receiptFromDB.setId_maloprodaja(result.getInt("id_maloprodaja"));
            receiptFromDB.setId_zaposlenik(result.getInt("id_zaposlenik"));

            sql = "SELECT * FROM Racun_Artikal WHERE id_racun = " + Integer.toString(receiptFromDB.getId_racun());
            query = getConn().prepareStatement(sql);
            ResultSet result2 = query.executeQuery();

            while (result2.next()) {
                ReceiptItem receiptItem = new ReceiptItem();

                receiptItem.setId_artikal(result2.getInt("id_artikal"));
                receiptItem.setKolicina(result2.getInt("kolicina"));
                receiptItem.setPopust(result2.getDouble("popust"));
                receiptItem.setUkupna_cijena(result2.getDouble("ukupna_cijena"));

                items.add(receiptItem);
            }

            receiptFromDB.setItems(items);

            receiptFromDB.setDatum(result.getString("datum"));
            receiptFromDB.setVrijeme(result.getString("vrijeme"));
            receiptFromDB.setUkupna_cijena(result.getDouble("ukupna_cijena"));
            receiptFromDB.setUkupni_popust(result.getDouble("ukupni_popust"));
            receiptFromDB.setPdv(result.getDouble("pdv"));

            allReceipts.add(receiptFromDB);
        }

        closeConnection();
        return allReceipts;
    }

    public Receipt getReceipt(String receiptId) throws SQLException {

        String sql = "SELECT * FROM Racun WHERE id_racun = ?";
        PreparedStatement query = getConn().prepareStatement(sql);
        query.setString(1, receiptId);
        ResultSet result = query.executeQuery();

        while (result.next()) {
            ArrayList<ReceiptItem> items = new ArrayList<>();

            this.getReceipt().setId_racun(result.getInt("id_racun"));
            this.getReceipt().setId_maloprodaja(result.getInt("id_maloprodaja"));
            this.getReceipt().setId_zaposlenik(result.getInt("id_zaposlenik"));

            sql = "SELECT * FROM Racun_Artikal WHERE id_racun = " + Integer.toString(this.getReceipt().getId_racun());
            query = getConn().prepareStatement(sql);
            ResultSet result2 = query.executeQuery();

            while (result2.next()) {
                ReceiptItem receiptItem = new ReceiptItem();

                receiptItem.setId_artikal(result2.getInt("id_artikal"));
                receiptItem.setKolicina(result2.getInt("kolicina"));
                receiptItem.setPopust(result2.getDouble("popust"));
                receiptItem.setUkupna_cijena(result2.getDouble("ukupna_cijena"));

                items.add(receiptItem);
            }

            this.getReceipt().setItems(items);

            this.getReceipt().setDatum(result.getString("datum"));
            this.getReceipt().setVrijeme(result.getString("vrijeme"));
            this.getReceipt().setUkupna_cijena(result.getDouble("ukupna_cijena"));
            this.getReceipt().setUkupni_popust(result.getDouble("ukupni_popust"));
            this.getReceipt().setPdv(result.getDouble("pdv"));
        }

        return this.getReceipt();
    }

    /**
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * @param receipt the receipt to set
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

}
