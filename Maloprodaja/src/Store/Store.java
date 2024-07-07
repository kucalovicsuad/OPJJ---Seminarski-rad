/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Store;

/**
 *
 * @author Suad's Laptop
 */
public class Store {
    private int id_maloprodaja, id_vlasnik, id_admin;
    private String naziv, adresa, grad, drzava, email, kontakt_broj;

    public Store() {
    }

    public Store(int id_maloprodaja, int id_vlasnik, int id_admin, String naziv, String adresa, String grad, String drzava, String email, String kontakt_broj) {
        this.id_maloprodaja = id_maloprodaja;
        this.id_vlasnik = id_vlasnik;
        this.id_admin = id_admin;
        this.naziv = naziv;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.email = email;
        this.kontakt_broj = kontakt_broj;
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
     * @return the id_vlasnik
     */
    public int getId_vlasnik() {
        return id_vlasnik;
    }

    /**
     * @param id_vlasnik the id_vlasnik to set
     */
    public void setId_vlasnik(int id_vlasnik) {
        this.id_vlasnik = id_vlasnik;
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
     * @return the adresa
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * @param adresa the adresa to set
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * @return the grad
     */
    public String getGrad() {
        return grad;
    }

    /**
     * @param grad the grad to set
     */
    public void setGrad(String grad) {
        this.grad = grad;
    }

    /**
     * @return the drzava
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * @param drzava the drzava to set
     */
    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the kontakt_broj
     */
    public String getKontakt_broj() {
        return kontakt_broj;
    }

    /**
     * @param kontakt_broj the kontakt_broj to set
     */
    public void setKontakt_broj(String kontakt_broj) {
        this.kontakt_broj = kontakt_broj;
    }   

    /**
     * @return the id_admin
     */
    public int getId_admin() {
        return id_admin;
    }

    /**
     * @param id_admin the id_admin to set
     */
    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}
