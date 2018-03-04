package huntermahroug.com.lille1campus.model;

import huntermahroug.com.lille1campus.viewmodel.TwoWayBoundInteger;
import huntermahroug.com.lille1campus.viewmodel.TwoWayBoundString;

/**
 * Created by Claire on 04/03/2018.
 */

public class EventToAdd {

    private TwoWayBoundString name;
    private TwoWayBoundInteger categoryId;
    private TwoWayBoundString date;
    private TwoWayBoundString time;
    private TwoWayBoundString location;
    private TwoWayBoundString description;
    private TwoWayBoundString email;
    private TwoWayBoundInteger price;
    private TwoWayBoundInteger nbPlaces;

    public EventToAdd() {
        name = new TwoWayBoundString();
        email = new TwoWayBoundString();
        price = new TwoWayBoundInteger();
    }

    public TwoWayBoundString getName() {
        return name;
    }

    public void setName(TwoWayBoundString name) {
        this.name = name;
    }

    public TwoWayBoundInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(TwoWayBoundInteger categoryId) {
        this.categoryId = categoryId;
    }

    public TwoWayBoundString getDate() {
        return date;
    }

    public void setDate(TwoWayBoundString date) {
        this.date = date;
    }

    public TwoWayBoundString getTime() {
        return time;
    }

    public void setTime(TwoWayBoundString time) {
        this.time = time;
    }

    public TwoWayBoundString getLocation() {
        return location;
    }

    public void setLocation(TwoWayBoundString location) {
        this.location = location;
    }

    public TwoWayBoundString getDescription() {
        return description;
    }

    public void setDescription(TwoWayBoundString description) {
        this.description = description;
    }

    public TwoWayBoundString getEmail() {
        return email;
    }

    public void setEmail(TwoWayBoundString email) {
        this.email = email;
    }

    public TwoWayBoundInteger getPrice() {
        return price;
    }

    public void setPrice(TwoWayBoundInteger price) {
        this.price = price;
    }

    public TwoWayBoundInteger getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(TwoWayBoundInteger nbPlaces) {
        this.nbPlaces = nbPlaces;
    }
}
