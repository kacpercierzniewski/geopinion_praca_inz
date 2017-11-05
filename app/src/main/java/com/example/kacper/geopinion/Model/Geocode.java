
package com.example.kacper.geopinion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geocode {

    @SerializedName("what")
    @Expose
    private String what;
    @SerializedName("where")
    @Expose
    private String where;
    @SerializedName("center")
    @Expose
    private Center center;
    @SerializedName("displayString")
    @Expose
    private String displayString;
    @SerializedName("cc")
    @Expose
    private String cc;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

}
