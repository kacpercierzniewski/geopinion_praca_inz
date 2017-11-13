
package com.example.kacper.geopinion.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("ratingColor")
    @Expose
    private String ratingColor;
    @SerializedName("ratingSignals")
    @Expose
    private Integer ratingSignals;
    @SerializedName("beenHere")
    @Expose
    private BeenHere beenHere;
    @SerializedName("hours")
    @Expose
    private Hours hours;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("hereNow")
    @Expose
    private HereNow hereNow;

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    @SerializedName("Tip")
    @Expose
    private Tip tip;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public Integer getRatingSignals() {
        return ratingSignals;
    }

    public void setRatingSignals(Integer ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    public BeenHere getBeenHere() {
        return beenHere;
    }

    public void setBeenHere(BeenHere beenHere) {
        this.beenHere = beenHere;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public HereNow getHereNow() {
        return hereNow;
    }

    public void setHereNow(HereNow hereNow) {
        this.hereNow = hereNow;
    }

}
