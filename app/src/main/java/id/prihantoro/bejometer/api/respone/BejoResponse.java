package id.prihantoro.bejometer.api.respone;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wahyu Prihantoro on 21-Aug-16.
 */
public class BejoResponse implements Serializable{
    @SerializedName("nama1")
    @Expose
    private String nama1;
    @SerializedName("nama2")
    @Expose
    private String nama2;
    @SerializedName("gender1")
    @Expose
    private List<String> gender1 = new ArrayList<String>();
    @SerializedName("gender2")
    @Expose
    private List<String> gender2 = new ArrayList<String>();
    @SerializedName("persentase")
    @Expose
    private double persentase;
    @SerializedName("hari1")
    @Expose
    private String hari1;
    @SerializedName("hari2")
    @Expose
    private String hari2;


    public String getNama1() {
        return nama1;
    }

    public void setNama1(String nama1) {
        this.nama1 = nama1;
    }

    public String getNama2() {
        return nama2;
    }

    public void setNama2(String nama2) {
        this.nama2 = nama2;
    }

    public List<String> getGender1() {
        return gender1;
    }

    public void setGender1(List<String> gender1) {
        this.gender1 = gender1;
    }

    public List<String> getGender2() {
        return gender2;
    }

    public void setGender2(List<String> gender2) {
        this.gender2 = gender2;
    }

    public Double getPersentase() {
        return persentase;
    }

    public void setPersentase(Double persentase) {
        this.persentase = persentase;
    }

    public String getHari1() {
        return hari1;
    }

    public void setHari1(String hari1) {
        this.hari1 = hari1;
    }

    public String getHari2() {
        return hari2;
    }

    public void setHari2(String hari2) {
        this.hari2 = hari2;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, BejoResponse.class);
    }
}
