package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
import huntermahroug.com.lille1campus.viewmodel.AddEventViewModel;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@DataBound
@EFragment(R.layout.fragment_add_event)
public class AddEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @ViewById(R.id.category_choices)
    Spinner categorySpinner;

    @ViewById(R.id.time_edit)
    EditText timeEdit;

    @ViewById(R.id.date_edit)
    EditText dateEdit;

    @ViewById(R.id.location_edit)
    EditText locationEdit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private AddEventViewModel addEventViewModel;

    public static int PLACE_PICKER_REQUEST = 1;

    public AddEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEventFragment newInstance(String param1, String param2) {
        AddEventFragment fragment = new AddEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @AfterViews
    public void setInputNull() {
        dateEdit.setInputType(InputType.TYPE_NULL);
        timeEdit.setInputType(InputType.TYPE_NULL);
        locationEdit.setInputType(InputType.TYPE_NULL);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Category> categoryChoicesList = new ArrayList<>();
        // Ci-dessous ce n'est pas une vraie catégorie, juste un "placeholder" pour le Spinner
        // qui est visible tant que l'utilisateur n'a pas sélectionné une catégorie
        categoryChoicesList.add(new Category("Catégories", -1));
        List<Category> categoriesList = ((LilleCampusApplication) this.getActivity().getApplication()).getCategoriesList();
        categoryChoicesList.addAll(categoriesList);

        // TODO: construction du Spinner à déplacer dans une classe séparée
        // Construire un Spinner en utilisant un ArrayAdapter customisé qui met en texte gris la première entrée (Catégorie) et la cache
        // dès qu'on appuie sur le Spinner
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this.getActivity(), android.R.layout.simple_spinner_item, categoryChoicesList) {

            // Méthode servant à mettre en texte gris la première entrée
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, null, parent);
                TextView tv = (TextView)v;

                if(position == 0) {
                    tv.setTextColor(0xffa8a8a8); // Même couleur que le "hint" d'un EditText
                }

                // Dans tous les cas, également définir le contenu du texte et la
                // taille du texte de la vue à 18sp (pareil que les autres champs)
                tv.setText(categoryChoicesList.get(position).getName());
                tv.setTextSize(18);

                v = tv;
                return v;
            }

            // Méthode servant à cacher la première entrée dès qu'on appuie sur le Spinner
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v;

                if(position == 0) {
                    // Cacher la première entrée du Spinner dès sa sélection
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    // Mettre le paramètre convertView à null pour empêcher
                    // la réutilisation de vues particulières
                    v = super.getDropDownView(position, null, parent);
                    // Egalement définir le contenu du texte
                    TextView tv = (TextView)v;
                    tv.setText(categoryChoicesList.get(position).getName());
                }

                // Cacher le scroll bar car il apparaît parfois inutilement ; il n'empêche pas le scroll
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @BindingObject
    FragmentAddEventBinding binding;

    @AfterViews
    void refreshView() {
        addEventViewModel = new AddEventViewModel(this);
        binding.setEvent(addEventViewModel);
    }

}
