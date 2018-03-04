package huntermahroug.com.lille1campus.model;

/**
 * Created by Claire on 04/03/2018.
 */

public class Category {

    private String name;
    private int imgResourceId;

    public Category(String name, int imgResourceId) {
        this.name = name;
        this.imgResourceId = imgResourceId;
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
