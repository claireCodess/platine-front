package huntermahroug.com.lille1campus.view.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import huntermahroug.com.lille1campus.R;

/**
 * Created by Claire on 10/03/2018.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    public static String EXISTINGTIME_ARG = "existingdate_arg";
    private String existingTime;
    private java.text.DateFormat timeFormat;

    public static DialogFragment newInstance(String existingDate) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putString(EXISTINGTIME_ARG, existingDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        existingTime = "";
        if (getArguments() != null) {
            existingTime = getArguments().getString(EXISTINGTIME_ARG);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timeFormat = new SimpleDateFormat("HH':'mm");

        // Utiliser l'heure courante en tant qu'heure par défaut
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if(!existingTime.isEmpty()) {
            // Parser l'heure déjà mise pour l'afficher dans le Dialog
            Date date;
            try {
                date = timeFormat.parse(existingTime);
                c.setTime(date);
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            } catch(ParseException e) {
                // ...
            }
        }

        // Créer une nouvelle instance de TimePickerDialog et la retourner
        return new TimePickerDialog(getActivity(), this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Construire l'heure choisie
        final Calendar cal = Calendar.getInstance();
        cal.set(0, 0, 0, hourOfDay, minute);

        // Préparer la date à être mise en texte
        String dateStr = timeFormat.format(cal.getTime());

        // Enfin, mettre le texte dans le bon format dans le EditText
        EditText timeEdit = getActivity().findViewById(R.id.time_edit);
        timeEdit.setText(dateStr);
    }
}
