package huntermahroug.com.lille1campus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Claire on 04/03/2018.
 */

public class EventTest {

    private String name;

    @SerializedName("category_id")
    private int categoryId;

    private String date;

    private int price;

    private String description;

    private String email;

    private String location;

    @SerializedName("totalplaces")
    private int totalPlaces;

    public EventTest(String name, int categoryId, String date, int price, String description, String email, String location, int totalPlaces) {
        this.name = name;
        this.categoryId = categoryId;
        this.date = date;
        this.price = price;
        this.description = description;
        this.email = email;
        this.location = location;
        this.totalPlaces = totalPlaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

}
