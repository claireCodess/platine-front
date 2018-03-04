package huntermahroug.com.lille1campus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Claire on 04/03/2018.
 */

public class Category {

    private int id;

    @SerializedName("value")
    private String name;

    private int imgResourceId;

    public Category(String name, int imgResourceId) {
        this.name = name;
        this.imgResourceId = imgResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }

}
