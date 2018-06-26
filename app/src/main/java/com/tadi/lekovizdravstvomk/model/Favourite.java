package com.tadi.lekovizdravstvomk.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favourite",
        indices = { @Index(value = "id") }
)
public class Favourite implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idDrug;
    private String idUser;

    public Favourite() {
    }

    public Favourite(int idDrug, String idUser) {
        this.idDrug = idDrug;
        this.idUser = idUser;
    }

    protected Favourite(Parcel in) {
        id = in.readInt();
        idDrug = in.readInt();
        idUser = in.readString();
    }

    public static final Creator<Favourite> CREATOR = new Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(Parcel in) {
            return new Favourite(in);
        }

        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        this.idDrug = idDrug;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idDrug);
        dest.writeString(idUser);
    }
}
