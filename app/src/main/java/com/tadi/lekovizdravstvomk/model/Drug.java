package com.tadi.lekovizdravstvomk.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

@Entity(tableName = "drugs",
        indices = { @Index(value = "id") }
)
public class Drug implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public boolean isFavovite;
    private String latinicno_ime, ime, jacina, pakuvanje, farmacevska_forma, nacin_izdavanje, proizvoditel, nositel_odobrenie,broj_na_resenie,
                   datum_na_resenie, datum_na_vaznost, G_O;

    private Double golemoprodazna_cena, maloprodazna_cena, rating;
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

    protected Drug(Parcel in) {
        id = in.readInt();
        latinicno_ime = in.readString();
        ime = in.readString();
        jacina = in.readString();
        pakuvanje = in.readString();
        farmacevska_forma = in.readString();
        nacin_izdavanje = in.readString();
        proizvoditel = in.readString();
        nositel_odobrenie = in.readString();
        broj_na_resenie = in.readString();
        datum_na_resenie = in.readString();
        datum_na_vaznost = in.readString();
        G_O = in.readString();
        if (in.readByte() == 0) {
            golemoprodazna_cena = null;
        } else {
            golemoprodazna_cena = in.readDouble();
        }
        if (in.readByte() == 0) {
            maloprodazna_cena = null;
        } else {
            maloprodazna_cena = in.readDouble();
        }
    }

    public static final Creator<Drug> CREATOR = new Creator<Drug>() {
        @Override
        public Drug createFromParcel(Parcel in) {
            return new Drug(in);
        }

        @Override
        public Drug[] newArray(int size) {
            return new Drug[size];
        }
    };

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


    private static final Comparator<Drug> ALPHABETICAL_COMPARATOR_NAME = new Comparator<Drug>() {
        @Override
        public int compare(Drug a, Drug b) {
            return a.getLatinicno_ime().compareTo(b.getLatinicno_ime());
        }
    };

    private static final Comparator<Drug> ALPHABETICAL_COMPARATOR_DatumNaResenie = new Comparator<Drug>() {
        @Override
        public int compare(Drug a, Drug b) {
            return a.getDatum_na_resenie().compareTo(b.getDatum_na_resenie());
        }
    };

    private static final Comparator<Drug> ALPHABETICAL_COMPARATOR_MaloprodaznaCena= new Comparator<Drug>() {
        @Override
        public int compare(Drug a, Drug b) {
            return a.getMaloprodazna_cena().compareTo(b.getMaloprodazna_cena());
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(latinicno_ime);
        dest.writeString(ime);
        dest.writeString(jacina);
        dest.writeString(pakuvanje);
        dest.writeString(farmacevska_forma);
        dest.writeString(nacin_izdavanje);
        dest.writeString(proizvoditel);
        dest.writeString(nositel_odobrenie);
        dest.writeString(broj_na_resenie);
        dest.writeString(datum_na_resenie);
        dest.writeString(datum_na_vaznost);
        dest.writeString(G_O);
        if (golemoprodazna_cena == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(golemoprodazna_cena);
        }
        if (maloprodazna_cena == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(maloprodazna_cena);
        }
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public boolean isFavovite() {
        return isFavovite;
    }

    public void setFavovite(boolean favovite) {
        isFavovite = favovite;
    }
}
