package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.model.EventTest;
import huntermahroug.com.lille1campus.model.EventToAdd;
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

    /*@Bindable
    public TwoWayBoundInteger getCategory() {
        return event.getCategoryId();
    }*/

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

    public String convertTimeToAPIFormat() {
        try {
            DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
            DateFormat presentationFormat = new SimpleDateFormat("HH:mm");
            Date date = presentationFormat.parse(event.getDate().get());
            return databaseFormat.format(date);
        } catch (ParseException e) {
            return event.getDate().get();
        }
    }

    public void onSubmitForm() {
        /*System.out.println("name = " + event.getName().get());
        System.out.println("category = " + event.getCategoryId().get());
        System.out.println("date and time = " + convertDateAndTimeToAPIFormat());
        System.out.println("location = " + event.getLocation().get());
        System.out.println("description = " + event.getDescription().get());
        System.out.println("e-mail = " + event.getEmail().get());
        System.out.println("price = " + convertPriceToAPIFormat());
        System.out.println("nb places = " + event.getNbPlaces().get());*/

        LilleCampusAPI lilleCampusAPI = ((LilleCampusApplication) fragment.getActivity().getApplication()).getLilleCampusAPI();

        //EventTest eventTest = new EventTest(event.getName().get(), event.getPrice().get(), event.getEmail().get());

        EventTest eventTest = new EventTest("Test sortie", 4, "2018-03-07T19:30:00+00:00", 5, "description test 3","claire@bidon.com", "Villeneuve d'Ascq", 50);

        lilleCampusAPI.postEvent(eventTest, new Callback<EventTest>() {
            @Override
            public void success(EventTest event, Response response) {
                System.out.println("Successsssssssss !!!!!!!!!!!!");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
            }
        });

    }

}
