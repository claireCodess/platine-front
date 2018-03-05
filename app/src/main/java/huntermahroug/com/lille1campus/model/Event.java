package huntermahroug.com.lille1campus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Claire on 02/03/2018.
 * Classe des détails d'un événement.
 * Implémente l'interface Parcelable afin de passer les données
 * d'un événement du fragment de la liste des événements au fragment
 * des détails de l'événement.
 */

public class Event implements Parcelable {

    /**
     * ID de l'événement
     */
    private int id;

    /**
     * Nom de l'événement
     */
    private String name;

    /**
     * Date de l'événement
     */
    private String date;

    /**
     * Lieu de l'événement
     */
    private String location;

    /**
     * Catégorie de l'événement
     */
    private Category category;

    /**
     * Nombre de places au total pour l'événement
     * (si événement avec réservation)
     */
    @SerializedName("totalplaces")
    private int totalPlaces;

    /**
     * Nombre de places disponibles pour l'événement
     * (si événement avec réservation)
     */
    @SerializedName("availableplaces")
    private int availablePlaces;

    /**
     * Prix de l'événement
     */
    private int price;

    /**
     * E-mail de l'organisateur de l'événement
     * pour pouvoir le contacter
     */
    private String email;

    /**
     * Description de l'événement
     */
    private String description;

    /**
     * Constructeur vide
     */
    public Event() {

    }

    /**
     * Constructeur en définissant les paramètres directement, pour un événement avec
     * toutes les informations (pour la vue des détails d'un événement)
     */
    public Event(int id, String name, String date, String location, Category category,
                      int totalPlaces, int availablePlaces, int price, String email, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.category = category;
        this.totalPlaces = totalPlaces;
        this.availablePlaces = availablePlaces;
        this.price = price;
        this.email = email;
        this.description = description;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.location);
        dest.writeParcelable(this.category, flags);
        dest.writeInt(this.totalPlaces);
        dest.writeInt(this.availablePlaces);
        dest.writeInt(this.price);
        dest.writeString(this.email);
        dest.writeString(this.description);
    }

    protected Event(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
        this.location = in.readString();
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.totalPlaces = in.readInt();
        this.availablePlaces = in.readInt();
        this.price = in.readInt();
        this.email = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
