package huntermahroug.com.lille1campus.model;

import java.util.Date;

/**
 * Created by Claire on 22/01/2018.
 */

public class EventLite {

    /**
     * Nom de l'événement
     */
    private String name;

    /**
     * Date de l'événement
     */
    private Date date;

    /**
     * Lieu de l'événement
     */
    private String location;

    /**
     * Constructeur vide
     */
    public EventLite() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
