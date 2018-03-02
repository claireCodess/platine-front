package huntermahroug.com.lille1campus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Claire on 02/03/2018.
 */

public class Event {

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
    // En commentaire pour le moment en attendant que l'API soit corrigé !
    // private String date;

    /**
     * Lieu de l'événement
     */
    private String location;

    /**
     * Catégorie de l'événement
     */
    private String category;

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
    public Event(int id, String name, String date, String location, String category,
                      int totalPlaces, int availablePlaces, int price, String email, String description) {
        this.id = id;
        this.name = name;
        // this.date = date;
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

    /* public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    } */

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

}
