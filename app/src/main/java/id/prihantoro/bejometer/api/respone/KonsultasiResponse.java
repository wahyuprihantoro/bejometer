package id.prihantoro.bejometer.api.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wahyu on 13 Januari 2017.
 */

public class KonsultasiResponse implements Serializable {
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("gender")
    @Expose
    private List<String> gender = null;
    @SerializedName("suggested_names")
    @Expose
    private List<List<String>> suggestedNames = null;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<List<String>> getSuggestedNames() {
        return suggestedNames;
    }

    public void setSuggestedNames(List<List<String>> suggestedNames) {
        this.suggestedNames = suggestedNames;
    }
}
