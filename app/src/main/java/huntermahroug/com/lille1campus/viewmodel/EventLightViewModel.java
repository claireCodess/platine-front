package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.model.Event;
import huntermahroug.com.lille1campus.model.EventLight;
import huntermahroug.com.lille1campus.view.fragment.EventDetailsFragment_;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Claire on 20/02/2018.
 */

public class EventLightViewModel extends BaseObservable {

    /**
     * L'objet "model" de l'événement
     */
    private EventLight eventLight;

    /**
     * Le fragment correspondant à ce ViewModel
     */
    private Fragment fragment;

    /**
     * La position de l'événement dans la liste d'événements
     */
    private int position;

    /**
     * La liste des catégories récupérée de l'application
     */
    private static List<Category> categoriesList;

    public EventLightViewModel(EventLight eventLight, int position, Fragment fragment) {
        this.eventLight = eventLight;
        this.position = position;
        this.fragment = fragment;
        categoriesList = ((LilleCampusApplication) fragment.getActivity().getApplication()).getCategoriesList();
    }

    @Bindable
    public String getName() {
        return eventLight.getName();
    }

    /**
     * Retourne la date de l'événement pour l'affichage
     * @return String La date formatée
     */
    @Bindable
    public String getDate() {
        try {
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
            DateFormat presentationFormat = new SimpleDateFormat("EEEE d MMMM yyyy 'à' HH'h'mm");
            Date date = databaseFormat.parse(eventLight.getDate());
            return presentationFormat.format(date);
        } catch (ParseException e) {
            return eventLight.getDate();
        }
    }

    @Bindable
    public String getLocation() {
        return eventLight.getLocation();
    }

    @Bindable
    public Category getCategory() {
        return eventLight.getCategory();
    }

    @Bindable
    public int getPosition() {
        return this.position;
    }

    /**
     * Selon la catégorie de l'événement, affiche l'image correspondante.
     * @param imageView L'ImageView à modifier pour l'affichage
     * @param category La catégorie de l'événement
     */
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, Category category){
        imageView.setImageResource(categoriesList.get(category.getId()-1).getImgResourceId());
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

    public void onClick() {
        LilleCampusAPI lilleCampusAPI = ((LilleCampusApplication) fragment.getActivity().getApplication()).getLilleCampusAPI();

        // Récupérer les données de l'événement grâce à l'API
        lilleCampusAPI.getOneEvent(eventLight.getId(), new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                // Une fois les données de l'événement recupérées, effectuer la transition vers le fragment
                // des détails d'un événement
                FragmentManager fragmentManager = fragment.getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_placeholder, EventDetailsFragment_.newInstance(event));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
            }
        });

    }

}
