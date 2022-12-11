package com.example.trabajo1eva;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Plaza implements Parcelable {
    static int numPlaza = 0;
    private String nombrePlaza;

    public Plaza() {
        numPlaza++;
        nombrePlaza = String.valueOf(numPlaza);
    }

    public Plaza(String nombrePlaza) {
        this.nombrePlaza = nombrePlaza;
    }

    protected Plaza(Parcel in) {
        numPlaza = in.readInt();
    }

    public static final Creator<Plaza> CREATOR = new Creator<Plaza>() {
        @Override
        public Plaza createFromParcel(Parcel in) {
            return new Plaza(in);
        }

        @Override
        public Plaza[] newArray(int size) {
            return new Plaza[size];
        }
    };

    public int getNumPlaza() {
        return numPlaza;
    }

    public String getNombrePlaza() {
        return nombrePlaza;
    }

    @NonNull
    @Override
    public String toString() {
        return nombrePlaza;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(numPlaza);
    }
}
