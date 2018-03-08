package huntermahroug.com.lille1campus.viewmodel;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.model.EventTest;
import huntermahroug.com.lille1campus.model.EventToAdd;
import huntermahroug.com.lille1campus.view.fragment.EventListFragment_;
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

    public AddEventViewModel(Fragment fragment) {
        this.event = new EventToAdd();
        this.fragment = fragment;
    }

    @Bindable
    public TwoWayBoundString getName() {
        return event.getName();
    }

    @Bindable
    public int getCategory() {
        return event.getCategoryId().get();
    }

    public void setCategory(int categoryId) {
        event.getCategoryId().set(categoryId);
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
    public TwoWayBoundString getLocation() { return event.getLocation(); }

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
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
            DateFormat presentationFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm");
            Date date = presentationFormat.parse(event.getDate().get() + " " + event.getTime().get());
            return databaseFormat.format(date);
        } catch (ParseException e) {
            return event.getDate().get() + " " + event.getTime().get();
        }
    }

    @BindingAdapter(value = "selectedValueAttrChanged")
    public static void setListener(Spinner spinner, final InverseBindingListener listener) {
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

    public void onSubmitForm() {
        System.out.println("name = " + event.getName().get());
        System.out.println("category = " + event.getCategoryId().get());
        System.out.println("date and time = " + convertDateAndTimeToAPIFormat());
        System.out.println("location = " + event.getLocation().get());
        System.out.println("description = " + event.getDescription().get());
        System.out.println("e-mail = " + event.getEmail().get());
        System.out.println("price = " + convertPriceToAPIFormat());
        System.out.println("nb places = " + event.getNbPlaces().get());

        LilleCampusAPI lilleCampusAPI = ((LilleCampusApplication) fragment.getActivity().getApplication()).getLilleCampusAPI();

        EventTest eventTest = new EventTest(event.getName().get(), event.getCategoryId().get(), convertDateAndTimeToAPIFormat(), convertPriceToAPIFormat(),
                event.getDescription().get(), event.getEmail().get(), event.getLocation().get(), event.getNbPlaces().get());

        lilleCampusAPI.postEvent(eventTest, new Callback<EventTest>() {
            @Override
            public void success(EventTest event, Response response) {
                System.out.println("Successsssssssss !!!!!!!!!!!!");
                // On est redirigé vers le fragment de la liste des événements
                FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_placeholder, EventListFragment_.newInstance(false, false, "", -1));
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
