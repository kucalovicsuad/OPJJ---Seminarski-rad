/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Item;

/**
 *
 * @author mahmu
 */
public class Item {
    private int id_artikal;
    private int id_distributor;
    private String naziv;
    private int kolicina;
    private double popust;
    private double cijena_nabavke;
    private double cijena_prodaje;

    public Item() {
    }

    public Item(int id_artikal, int id_distributor, String naziv, int kolicina, double popust, double cijena_nabavke, double cijena_prodaje) {
        this.id_artikal = id_artikal;
        this.id_distributor = id_distributor;
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.popust = popust;
        this.cijena_nabavke = cijena_nabavke;
        this.cijena_prodaje = cijena_prodaje;
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
     * @return the id_distributor
     */
    public int getId_distributor() {
        return id_distributor;
    }

    /**
     * @param id_distributor the id_distributor to set
     */
    public void setId_distributor(int id_distributor) {
        this.id_distributor = id_distributor;
    }

    /**
     * @return the naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * @param naziv the naziv to set
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
     * @return the cijena_nabavke
     */
    public double getCijena_nabavke() {
        return cijena_nabavke;
    }

    /**
     * @param cijena_nabavke the cijena_nabavke to set
     */
    public void setCijena_nabavke(double cijena_nabavke) {
        this.cijena_nabavke = cijena_nabavke;
    }

    /**
     * @return the cijena_prodaje
     */
    public double getCijena_prodaje() {
        return cijena_prodaje;
    }

    /**
     * @param cijena_prodaje the cijena_prodaje to set
     */
    public void setCijena_prodaje(double cijena_prodaje) {
        this.cijena_prodaje = cijena_prodaje;
    }

    
    
}
