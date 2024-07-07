/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receipt;

import java.util.ArrayList;

/**
 *
 * @author Suad's Laptop
 */
public class Receipt {
    private int id_racun;
    private int id_maloprodaja;
    private int id_zaposlenik;
    private ArrayList<ReceiptItem> items;
    private String datum;
    private String vrijeme;
    private double ukupna_cijena;
    private double ukupni_popust;
    private double pdv;

    public Receipt() {
    }

    public Receipt(int id_racun, int id_maloprodaja, int id_zaposlenik, ArrayList<ReceiptItem> items, double ukupni_popust, String datum, String vrijeme, double ukupna_cijena, double pdv) {
        this.id_racun = id_racun;
        this.id_maloprodaja = id_maloprodaja;
        this.id_zaposlenik = id_zaposlenik;
        this.items = items;
        this.ukupni_popust = ukupni_popust;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.ukupna_cijena = ukupna_cijena;
        this.pdv = pdv;
    }

    /**
     * @return the id_racun
     */
    public int getId_racun() {
        return id_racun;
    }

    /**
     * @param id_racun the id_racun to set
     */
    public void setId_racun(int id_racun) {
        this.id_racun = id_racun;
    }

    /**
     * @return the id_maloprodaja
     */
    public int getId_maloprodaja() {
        return id_maloprodaja;
    }

    /**
     * @param id_maloprodaja the id_maloprodaja to set
     */
    public void setId_maloprodaja(int id_maloprodaja) {
        this.id_maloprodaja = id_maloprodaja;
    }

    /**
     * @return the id_zaposlenik
     */
    public int getId_zaposlenik() {
        return id_zaposlenik;
    }

    /**
     * @param id_zaposlenik the id_zaposlenik to set
     */
    public void setId_zaposlenik(int id_zaposlenik) {
        this.id_zaposlenik = id_zaposlenik;
    }

    /**
     * @return the items
     */
    public ArrayList<ReceiptItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<ReceiptItem> items) {
        this.items = items;
    }

    /**
     * @return the ukupni_popust
     */
    public double getUkupni_popust() {
        return ukupni_popust;
    }

    /**
     * @param ukupni_popust the ukupni_popust to set
     */
    public void setUkupni_popust(double ukupni_popust) {
        this.ukupni_popust = ukupni_popust;
    }

    /**
     * @return the datum
     */
    public String getDatum() {
        return datum;
    }

    /**
     * @param datum the datum to set
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * @return the vrijeme
     */
    public String getVrijeme() {
        return vrijeme;
    }

    /**
     * @param vrijeme the vrijeme to set
     */
    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    /**
     * @return the ukupna_cijena
     */
    public double getUkupna_cijena() {
        return ukupna_cijena;
    }

    /**
     * @param ukupna_cijena the ukupna_cijena to set
     */
    public void setUkupna_cijena(double ukupna_cijena) {
        this.ukupna_cijena = ukupna_cijena;
    }

    /**
     * @return the pdv
     */
    public double getPdv() {
        return pdv;
    }

    /**
     * @param pdv the pdv to set
     */
    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

}
