package huntermahroug.com.lille1campus.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import huntermahroug.com.lille1campus.R;

/**
 * Created by Claire on 10/03/2018.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    public static String EXISTINGDATE_ARG = "existingdate_arg";
    private String existingDate;
    private java.text.DateFormat dateFormat;

    public static DatePickerFragment newInstance(String existingDate) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString(EXISTINGDATE_ARG, existingDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        existingDate = "";
        if (getArguments() != null) {
            existingDate = getArguments().getString(EXISTINGDATE_ARG);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy");

        // Utiliser la date courante en tant que date par défaut
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(!existingDate.isEmpty()) {
            // Parser la date déjà mise pour l'afficher dans le Dialog
            Date date;
            try {
                date = dateFormat.parse(existingDate);
                c.setTime(date);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            } catch(ParseException e) {
                // ...
            }
        }

        // Créer une nouvelle instance de DatePickerDialog et la retourner
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
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
