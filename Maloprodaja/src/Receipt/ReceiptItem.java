/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receipt;

/**
 *
 * @author Suad's Laptop
 */
public class ReceiptItem {
    private int id_artikal;
    private double popust;
    private int kolicina;
    private double ukupna_cijena;

    public ReceiptItem() {
    }

    public ReceiptItem(int id_artikal, double popust, int kolicina, double ukupna_cijena) {
        this.id_artikal = id_artikal;
        this.popust = popust;
        this.kolicina = kolicina;
        this.ukupna_cijena = ukupna_cijena;
    }

    /**
     * @return the id_artikal
     */
    public int getId_artikal() {
        return id_artikal;
    }

    /**
     * @param id_artikal the id_artikal to set
     */
    public void setId_artikal(int id_artikal) {
        this.id_artikal = id_artikal;
    }

    /**
     * @return the popust
     */
    public double getPopust() {
        return popust;
    }

    /**
     * @param popust the popust to set
     */
    public void setPopust(double popust) {
        this.popust = popust;
    }

    /**
     * @return the kolicina
     */
    public int getKolicina() {
        return kolicina;
    }

    /**
     * @param kolicina the kolicina to set
     */
    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
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
    
    
}
