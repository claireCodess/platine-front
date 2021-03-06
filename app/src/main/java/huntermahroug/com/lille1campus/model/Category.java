package huntermahroug.com.lille1campus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Claire on 04/03/2018.
 */

public class Category implements Parcelable {

    private int id;

    @SerializedName("value")
    private String name;

    /**
     * Variable marquée "transient" pour éviter sa désérialisation
     * lors de la lecture du JSON (car un champ avec ce nom n'existe
     * pas dans le JSON renvoyé par l'API)
     */
    private transient int imgResourceId;

    public Category(String name, int imgResourceId) {
        // En construisant "à la main" une catégorie, on met un ID égal à -1
        // pour dire que cette catégorie n'est pas liée à la BDD.
        this.id = -1;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.imgResourceId);
    }

    protected Category(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.imgResourceId = in.readInt();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
