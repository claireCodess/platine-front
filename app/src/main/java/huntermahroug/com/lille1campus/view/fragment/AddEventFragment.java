package huntermahroug.com.lille1campus.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.FragmentAddEventBinding;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.viewmodel.AddEventViewModel;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    public void setInputNullTimeAndDate() {
        dateEdit.setInputType(InputType.TYPE_NULL);
        timeEdit.setInputType(InputType.TYPE_NULL);
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
        binding.setEvent(new AddEventViewModel(this));
    }

    // TODO: fonction à déplacer dans le ModelView correspondant
    @Click(R.id.date_edit)
    public void onDateEditClick() {
        showDateDialog();
    }

    // TODO: fonction à déplacer dans le ModelView correspondant
    @Click(R.id.time_edit)
    public void onTimeEditClick() {
        showTimeDialog();
    }

    // TODO: fonction à déplacer dans le ModelView correspondant
    @FocusChange(R.id.date_edit)
    public void onDateEditFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            showDateDialog();
        }
    }

    // TODO: fonction à déplacer dans le ModelView correspondant
    @FocusChange(R.id.time_edit)
    public void onTimeEditFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            showTimeDialog();
        }
    }

    // TODO: fonction à déplacer dans le ModelView correspondant
    public void showDateDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    // TODO: fonction à déplacer dans le ModelView correspondant
    public void showTimeDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Utiliser la date courante en tant que date par défaut
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Créer une nouvelle instance de DatePickerDialog et la retourner
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Construire la date choisie
            final Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);

            // Préparer la date à être mise en texte
            java.text.DateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy");
            String dateStr = dateFormat.format(cal.getTime());

            // Enfin, mettre le texte dans le bon format dans le EditText
            EditText dateEdit = getActivity().findViewById(R.id.date_edit);
            dateEdit.setText(dateStr);
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Utiliser l'heure courante en tant qu'heure par défaut
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Créer une nouvelle instance de TimePickerDialog et la retourner
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Construire l'heure choisie
            final Calendar cal = Calendar.getInstance();
            cal.set(0, 0, 0, hourOfDay, minute);

            // Préparer la date à être mise en texte
            java.text.DateFormat timeFormat = new SimpleDateFormat("HH':'mm");
            String dateStr = timeFormat.format(cal.getTime());

            // Enfin, mettre le texte dans le bon format dans le EditText
            EditText timeEdit = getActivity().findViewById(R.id.time_edit);
            timeEdit.setText(dateStr);
        }
    }

}
