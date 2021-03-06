package huntermahroug.com.lille1campus.viewmodel;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.model.Event;
import huntermahroug.com.lille1campus.model.EventPost;
import huntermahroug.com.lille1campus.model.EventToAdd;
import huntermahroug.com.lille1campus.util.Util;
import huntermahroug.com.lille1campus.view.fragment.AddEventFragment_;
import huntermahroug.com.lille1campus.view.fragment.DatePickerFragment;
import huntermahroug.com.lille1campus.view.fragment.EventListFragment_;
import huntermahroug.com.lille1campus.view.fragment.TimePickerFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Claire on 03/03/2018.
 */

public class AddEventViewModel extends BaseObservable {

    /**
     * L'objet "model" de l'événement
     */
    private EventToAdd event;

    /**
     * Le fragment correspondant à ce ViewModel
     */
    private Fragment fragment;

    /**
     * Définition des coordonnées des coins N-W et S-E de la Google Map affichée
     * lors de la sélection d'un lieu
     */
    private LatLngBounds mapCorners;

    public AddEventViewModel(Fragment fragment) {
        this.event = new EventToAdd();
        this.fragment = fragment;
        this.mapCorners = toBounds(new LatLng(Event.latCentreCampus, Event.lngCentreCampus), 800);
    }

    private View.OnFocusChangeListener onDateEditFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showDateDialog(event.getDate().get());
            }
        }
    };

    private View.OnFocusChangeListener onTimeEditFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showTimeDialog(event.getDate().get());
            }
        }
    };

    private View.OnFocusChangeListener onLocationEditFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                getLocationFromGooglePlaces();
            }
        }
    };

    public EventToAdd getEvent() {
        return event;
    }

    @Bindable
    public TwoWayBoundString getName() {
        return event.getName();
    }

    @Bindable
    public int getCategory() {
        return event.getCategoryId();
    }

    public void setCategory(int categoryId) {
        event.setCategoryId(categoryId);
    }

    @Bindable
    public TwoWayBoundString getDate() {
        return event.getDate();
    }

    @Bindable
    public TwoWayBoundString getTime() {
        return event.getTime();
    }

    @Bindable
    public String getLocation() { return event.getLocation().get(); }

    public void setLocation(String location) {
        event.getLocation().set(location);
    }

    @Bindable
    public TwoWayBoundString getDescription() { return event.getDescription(); }

    @Bindable
    public TwoWayBoundString getEmail() { return event.getEmail(); }

    @Bindable
    public TwoWayBoundDouble getPrice() { return event.getPrice(); }

    @Bindable
    public TwoWayBoundInteger getNbPlaces() { return event.getNbPlaces(); }

    public int convertPriceToAPIFormat() {
        double priceInCentsDouble = event.getPrice().get() * 100;
        return (int)priceInCentsDouble;
    }

    public String convertDateAndTimeToAPIFormat() {
        try {
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            DateFormat presentationFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm");
            Date date = presentationFormat.parse(event.getDate().get() + " " + event.getTime().get());
            return databaseFormat.format(date);
        } catch (ParseException e) {
            return event.getDate().get() + " " + event.getTime().get();
        }
    }

    @BindingAdapter(value = "selectedValueAttrChanged")
    public static void setSpinnerListener(Spinner spinner, final InverseBindingListener listener) {
        if (listener != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listener.onChange();

                    System.out.println("hello world");
                    System.out.println("Categorie selectionnee : " + ((Category)parent.getItemAtPosition(position)).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Nothing
                }
            });
        }
    }

    @BindingAdapter("selectedValue")
    public static void setSelectedValue(Spinner view, int newValue) {
        int oldValue = ((Category)view.getSelectedItem()).getId();
        if(oldValue != newValue) {
            view.setSelection(view.getSelectedItemPosition());
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue")
    public static int getSelectedValue(AdapterView view) {
        return ((Category) view.getSelectedItem()).getId();
    }

    public LatLngBounds toBounds(LatLng center, double radiusInMeters) {
        double distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0);
        LatLng southwestCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 225.0);
        LatLng northeastCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 45.0);
        return new LatLngBounds(southwestCorner, northeastCorner);
    }

    public void getLocationFromGooglePlaces() {
        // On veut ouvrir l'appli Google Places avec la carte centrée sur Lille 1,
        // avec un rayon autour de 800m.
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        builder.setLatLngBounds(mapCorners);
        try {
            fragment.startActivityForResult(builder.build(fragment.getActivity()), AddEventFragment_.PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public EventPost createEventPost() {
        return new EventPost(event.getName().get(), event.getCategoryId(), convertDateAndTimeToAPIFormat(), convertPriceToAPIFormat(),
                event.getDescription().get(), event.getEmail().get(), event.getLocation().get(), event.getNbPlaces().get());
    }

    public void onSubmitForm() {
        LilleCampusAPI lilleCampusAPI = ((LilleCampusApplication) fragment.getActivity().getApplication()).getLilleCampusAPI();

        if(champsValides()) {
            EventPost eventPost = createEventPost();

            if(Util.isConnected(fragment.getActivity())) {
                lilleCampusAPI.postEvent(eventPost, new Callback<EventPost>() {
                    @Override
                    public void success(EventPost event, Response response) {
                        // On est redirigé vers le fragment de la liste des événements
                        FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_placeholder, EventListFragment_.newInstance(false, false, "", -1));
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error.getMessage());
                    }
                });
            } else {
                Toast.makeText(fragment.getActivity(), R.string.internet_connection_error_msg, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this.fragment.getActivity(), "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean champsValides() {
        if(event.getName().get().equals("") || event.getCategoryId() == -1 || event.getDate().get().equals("")
                || event.getTime().get().equals("") || event.getLocation().get().equals("") || event.getDescription().get().equals("")
                || event.getEmail().get().equals("")) {
            return false;
        }
        return true;
    }

    public void onDateEditClick() {
        showDateDialog(event.getDate().get());
    }

    public void onTimeEditClick() {
        showTimeDialog(event.getTime().get());
    }

    @Bindable
    public View.OnFocusChangeListener getOnDateEditFocusChange() {
        return onDateEditFocusChange;
    }

    @Bindable
    public View.OnFocusChangeListener getOnTimeEditFocusChange() {
        return onTimeEditFocusChange;
    }

    @Bindable
    public View.OnFocusChangeListener getOnLocationEditFocusChange() { return onLocationEditFocusChange; }

    @BindingAdapter("onFocusChange")
    public static void setOnFocusChange(View view, View.OnFocusChangeListener focusChangeListener) {
        view.setOnFocusChangeListener(focusChangeListener);
    }

    private void showDateDialog(String existingDate) {
        DialogFragment newFragment = DatePickerFragment.newInstance(existingDate);
        newFragment.show(fragment.getFragmentManager(), null);
    }

    private void showTimeDialog(String existingTime) {
        DialogFragment newFragment = TimePickerFragment.newInstance(existingTime);
        newFragment.show(fragment.getFragmentManager(), null);
    }

}
