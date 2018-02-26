package huntermahroug.com.lille1campus.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.EventLight;

/**
 * Created by Claire on 20/02/2018.
 */

public class EventLightViewModel extends BaseObservable {

    /**
     * L'objet "model" de l'événement
     */
    private EventLight event;

    /**
     * La position de l'événement dans la liste d'événements
     */
    private int position;

    public EventLightViewModel(EventLight event, int position) {
        this.event = event;
        this.position = position;
    }

    @Bindable
    public String getName() {
        return event.getName();
    }

    /**
     * Retourne la date de l'événement pour l'affichage
     * @return String La date formatée
     */
    @Bindable
    public String getDate() {
        try {
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat presentationFormat = new SimpleDateFormat("EEEE d MMMM yyyy 'à' HH'h'mm");
            Date date = databaseFormat.parse(event.getDate());
            return presentationFormat.format(date);
        } catch (ParseException e) {
            return event.getDate();
        }
    }

    @Bindable
    public String getLocation() {
        return event.getLocation();
    }

    @Bindable
    public String getCategory() {
        return event.getCategory();
    }

    @Bindable
    public int getPosition() {
        return this.position;
    }

    /**
     * Selon la catégorie de l'événement, affiche l'image correspondante.
     * @param imageView L'ImageView à modifier pour l'affichage
     * @param category String de la catégorie de l'événement
     */
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, String category){
        int resource;
        switch(category) {
            case "Culture":
                resource = R.drawable.ic_category_cultural;
                break;
            case "educational":
                resource = R.drawable.ic_category_educational;
                break;
            case "outing":
                resource = R.drawable.ic_category_outing;
                break;
            case "Sport":
                resource = R.drawable.ic_category_sport;
                break;
            default:
                resource = R.drawable.ic_view_events;
        }
        imageView.setImageResource(resource);
    }

    /**
     * Selon la position de l'événement dans la liste, fixe la couleur de
     * fond de cet événement.
     * @param itemView La vue à modifier pour changer la couleur de fond
     * @param position La position de l'événement dans la liste
     */
    @BindingAdapter("android:background")
    public static void setBackground(View itemView, int position) {
        if ((position % 2) == 0) {
            itemView.setBackgroundResource(R.color.white);
        } else {
            itemView.setBackgroundResource(R.color.colorSecondaryLight);
        }
    }

}
