package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import huntermahroug.com.lille1campus.model.Event;

/**
 * Created by Claire on 02/03/2018.
 */

public class EventViewModel extends BaseObservable {

    /**
     * L'objet "model" de l'événement
     */
    private Event event;

    /**
     * Le fragment correspondant à ce ViewModel
     */
    private Fragment fragment;

    public EventViewModel(Event event, Fragment fragment) {
        this.event = event;
        this.fragment = fragment;
    }

    @Bindable
    public String getName() {
        return event.getName();
    }

    /**
     * Retourne la date de l'événement pour l'affichage
     * @return String La date formatée
     */
    /* @Bindable
    public String getDate() {
        try {
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat presentationFormat = new SimpleDateFormat("EEEE d MMMM yyyy 'à' HH'h'mm");
            Date date = databaseFormat.parse(event.getDate());
            return presentationFormat.format(date);
        } catch (ParseException e) {
            return event.getDate();
        }
    } */

    @Bindable
    public String getLocation() {
        return event.getLocation();
    }

    /* @Bindable
    public String getCategory() {
        return event.getCategory();
    } */

}
