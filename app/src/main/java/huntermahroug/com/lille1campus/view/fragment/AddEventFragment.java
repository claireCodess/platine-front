package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.FragmentAddEventBinding;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.util.adapter.CategorySpinnerArrayAdapter;
import huntermahroug.com.lille1campus.viewmodel.AddEventViewModel;

import static android.app.Activity.RESULT_OK;


/**
 * Le Fragment de l'écran de proposition.
 */
@DataBound
@EFragment(R.layout.fragment_add_event)
public class AddEventFragment extends Fragment {

    @ViewById(R.id.category_choices)
    Spinner categorySpinner;

    @ViewById(R.id.time_edit)
    EditText timeEdit;

    @ViewById(R.id.date_edit)
    EditText dateEdit;

    @ViewById(R.id.location_edit)
    EditText locationEdit;

    private AddEventViewModel addEventViewModel;

    public static int PLACE_PICKER_REQUEST = 1;

    // Constructeur vide obligatoire
    public AddEventFragment() {
    }

    @AfterViews
    public void setInputNull() {
        dateEdit.setInputType(InputType.TYPE_NULL);
        timeEdit.setInputType(InputType.TYPE_NULL);
        locationEdit.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Category> categoryChoicesList = new ArrayList<>();
        // Ci-dessous ce n'est pas une vraie catégorie, juste un "placeholder" pour le Spinner
        // qui est visible tant que l'utilisateur n'a pas sélectionné une catégorie
        categoryChoicesList.add(new Category("Catégorie", -1));
        List<Category> categoriesList = ((LilleCampusApplication) this.getActivity().getApplication()).getCategoriesList();
        categoryChoicesList.addAll(categoriesList);

        // Construire un Spinner en utilisant un ArrayAdapter customisé
        CategorySpinnerArrayAdapter adapter = new CategorySpinnerArrayAdapter(this.getActivity(), R.layout.category_item_spinner_layout, R.id.name_category_spinner, R.id.icon_category_spinner, categoryChoicesList);
        adapter.setDropDownViewResource(R.layout.category_item_spinner_layout);
        categorySpinner.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this.getActivity(), data);
                locationEdit.setText(place.getName());
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @BindingObject
    FragmentAddEventBinding binding;

    @AfterViews
    void refreshView() {
        addEventViewModel = new AddEventViewModel(this);
        binding.setEvent(addEventViewModel);
    }

}
