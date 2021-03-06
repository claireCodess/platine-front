package huntermahroug.com.lille1campus.model;

/**
 * Created by Claire on 22/01/2018.
 */

public class EventLight {

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
     * Constructeur vide
     */
    public EventLight() {

    }

    /**
     * Constructeur en définissant les paramètres directement, pour un événement avec
     * le minimum d'informations (pour la liste des événements)
     */
    public EventLight(int id, String name, String date, String location, Category category) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.category = category;
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

}
