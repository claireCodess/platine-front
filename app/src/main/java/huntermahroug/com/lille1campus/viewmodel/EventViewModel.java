package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.model.Event;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    @Bindable
    public String getDate() {
        try {
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
    public Category getCategory() {
        return event.getCategory();
    }

    @Bindable
    public String getNeedsResrvation() {
        // Si le nombre de places est non nul, alors cet événement
        // a besoin d'une réservation. On affiche alors "Sur réservation"
        if(event.getTotalPlaces() != 0) {
            return "Sur réservation";
        } else {
            // Sinon, on affiche "Sans réservation"
            return "Sans réservation";
        }
    }

    @Bindable
    public int getShowReservation() {
        // Si le nombre de places est non nul, alors cet événement
        // a besoin d'une réservation et on affiche alors
        // le texte pour indiquer le nombre de places restantes.
        if(event.getTotalPlaces() != 0) {
            return VISIBLE;
        }
        // Sinon, on n'affiche pas le texte
        else {
            return GONE;
        }
    }

    @Bindable
    public String getPlaces() {
        // Si le nombre de places est non nul, alors cet événement
        // a besoin d'une réservation et le texte contiendra le nombre
        // de places restantes sur le nombre de places au total.
        int totalPlaces = event.getTotalPlaces();
        if(totalPlaces != 0) {
            return "(Places disponibles : " + event.getAvailablePlaces()
                    + " / " + totalPlaces + ")";
        }
        // Sinon, le texte n'est pas affiché
        else {
            return "N/A";
        }
    }

    @Bindable
    public String getPrice() {
        int priceInCents = event.getPrice();
        if(priceInCents == 0) {
            return "Gratuit";
        } else {
            // Le prix est stocké dans la BDD en centimes,
            // il faut le convertir en euros (avec virgule)
            // avant de l'afficher.
            double priceInEuros = priceInCents / 100.00;
            NumberFormat form = NumberFormat.getCurrencyInstance(Locale.FRANCE);
            return form.format(priceInEuros);
        }
    }

    @Bindable
    public String getEmail() {
        return event.getEmail();
    }

    @Bindable
    public String getDescription() {
        return event.getDescription();
    }

    /**
     * Selon la catégorie de l'événement, affiche l'image correspondante.
     * @param imageView L'ImageView à modifier pour l'affichage
     * @param category La catégorie de l'événement
     */
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, Category category){
        /*
         * Obligé de faire de cette façon, car sinon on a un NullPointerException
         * sur category
         */
        int resource;
        switch(category.getName()) {
            case "Culturel":
                resource = R.drawable.ic_category_cultural;
                break;
            case "Éducatif":
                resource = R.drawable.ic_category_educational;
                break;
            case "Sortie":
                resource = R.drawable.ic_category_outing;
                break;
            case "Sportif":
                resource = R.drawable.ic_category_sport;
                break;
            default:
                resource = R.drawable.ic_event;
        }
        imageView.setImageResource(resource);
    }

    public void onLocationClick() {
        String encodedLocationStr = Uri.encode(event.getLocation());
        Uri gmmIntentUri = Uri.parse("geo:" + Event.latCentreCampus + "," + Event.lngCentreCampus + "?q=" + encodedLocationStr);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        if (mapIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivity(mapIntent);
        } else {
            Toast.makeText(fragment.getActivity(), "Veuillez installer Google Maps pour localiser l'événement sur une carte.", Toast.LENGTH_LONG).show();
        }
    }

}
