package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

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

    @Bindable
    public TwoWayBoundInteger getPrice() { return event.getPrice(); }

    @Bindable
    public TwoWayBoundString getEmail() { return event.getEmail(); }

    public void onSubmitForm() {
        System.out.println(event.getName().get());

        LilleCampusAPI lilleCampusAPI = ((LilleCampusApplication) fragment.getActivity().getApplication()).getLilleCampusAPI();

        //EventTest eventTest = new EventTest(event.getName().get(), event.getPrice().get(), event.getEmail().get());

        EventTest eventTest = new EventTest("Nouvel evenement", 5, "claire@bidon.com");

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

        /*
        lilleCampusAPI.getAllEvents(new Callback<List<EventLight>>() {
            @Override
            public void success(List<EventLight> events, Response response) {
                showEvents(events);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
            }
        });
         */
    }

}
