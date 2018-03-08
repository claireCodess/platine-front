package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.view.fragment.EventListFragment_;

/**
 * Created by Claire on 04/03/2018.
 */

public class CategoryViewModel extends BaseObservable {

    private Category category;
    private int position;
    private Fragment fragment;

    public CategoryViewModel(Category category, int position, Fragment fragment) {
        this.category = category;
        this.position = position;
        this.fragment = fragment;
    }

    @Bindable
    public String getName() {
        return category.getName();
    }

    @Bindable
    public int getImgResourceId() {
        return category.getImgResourceId();
    }

    @Bindable
    public int getPosition() {
        return this.position;
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

    /**
     * Selon la catégorie de l'événement, affiche l'image correspondante.
     * @param imageView L'ImageView à modifier pour l'affichage
     * @param imgResourceId L'ID ressource de l'image de la catégorie de l'événement
     */
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int imgResourceId){
        imageView.setImageResource(imgResourceId);
    }

    public void onClick() {
        LilleCampusAPI lilleCampusAPI = ((LilleCampusApplication) fragment.getActivity().getApplication()).getLilleCampusAPI();

        // Effectuer la transition vers le fragment de tous les événements avec le paramètre "category"
        FragmentManager fragmentManager = fragment.getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, EventListFragment_.newInstance(false, true, "", category.getId()));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
