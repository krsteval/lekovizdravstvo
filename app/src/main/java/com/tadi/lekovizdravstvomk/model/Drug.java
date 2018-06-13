package com.tadi.lekovizdravstvomk.model;

public class Drug {

    private int id;
    private String latinicno_ime, ime, jacina, pakuvanje, farmacevska_forma, nacin_izdavanje, proizvoditel, nositel_odobrenie,broj_na_resenie,
                   datum_na_resenie, datum_na_vaznost, G_O;
    ;
    private Double golemoprodazna_cena, maloprodazna_cena;
    public Drug(){}

    public Drug(String latinicno_ime, String ime, String jacina, String pakuvanje, String farmacevska_forma, String nacin_izdavanje, String proizvoditel, String nositel_odobrenie, String broj_na_resenie, String datum_na_resenie, String datum_na_vaznost, String g_O, Double golemoprodazna_cena, Double maloprodazna_cena) {
        this.latinicno_ime = latinicno_ime;
        this.ime = ime;
        this.jacina = jacina;
        this.pakuvanje = pakuvanje;
        this.farmacevska_forma = farmacevska_forma;
        this.nacin_izdavanje = nacin_izdavanje;
        this.proizvoditel = proizvoditel;
        this.nositel_odobrenie = nositel_odobrenie;
        this.broj_na_resenie = broj_na_resenie;
        this.datum_na_resenie = datum_na_resenie;
        this.datum_na_vaznost = datum_na_vaznost;
        this.golemoprodazna_cena = golemoprodazna_cena;
        this.maloprodazna_cena = maloprodazna_cena;
        G_O = g_O;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatinicno_ime() {
        return latinicno_ime;
    }

    public void setLatinicno_ime(String latinicno_ime) {
        this.latinicno_ime = latinicno_ime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getJacina() {
        return jacina;
    }

    public void setJacina(String jacina) {
        this.jacina = jacina;
    }

    public String getPakuvanje() {
        return pakuvanje;
    }

    public void setPakuvanje(String pakuvanje) {
        this.pakuvanje = pakuvanje;
    }

    public String getFarmacevska_forma() {
        return farmacevska_forma;
    }

    public void setFarmacevska_forma(String farmacevska_forma) {
        this.farmacevska_forma = farmacevska_forma;
    }

    public String getNacin_izdavanje() {
        return nacin_izdavanje;
    }

    public void setNacin_izdavanje(String nacin_izdavanje) {
        this.nacin_izdavanje = nacin_izdavanje;
    }

    public String getProizvoditel() {
        return proizvoditel;
    }

    public void setProizvoditel(String proizvoditel) {
        this.proizvoditel = proizvoditel;
    }

    public String getNositel_odobrenie() {
        return nositel_odobrenie;
    }

    public void setNositel_odobrenie(String nositel_odobrenie) {
        this.nositel_odobrenie = nositel_odobrenie;
    }

    public String getBroj_na_resenie() {
        return broj_na_resenie;
    }

    public void setBroj_na_resenie(String broj_na_resenie) {
        this.broj_na_resenie = broj_na_resenie;
    }

    public String getDatum_na_resenie() {
        return datum_na_resenie;
    }

    public void setDatum_na_resenie(String datum_na_resenie) {
        this.datum_na_resenie = datum_na_resenie;
    }

    public String getDatum_na_vaznost() {
        return datum_na_vaznost;
    }

    public void setDatum_na_vaznost(String datum_na_vaznost) {
        this.datum_na_vaznost = datum_na_vaznost;
    }

    public String getG_O() {
        return G_O;
    }

    public void setG_O(String g_O) {
        G_O = g_O;
    }

    public Double getGolemoprodazna_cena() {
        return golemoprodazna_cena;
    }

    public void setGolemoprodazna_cena(Double golemoprodazna_cena) {
        this.golemoprodazna_cena = golemoprodazna_cena;
    }

    public Double getMaloprodazna_cena() {
        return maloprodazna_cena;
    }

    public void setMaloprodazna_cena(Double maloprodazna_cena) {
        this.maloprodazna_cena = maloprodazna_cena;
    }
}
