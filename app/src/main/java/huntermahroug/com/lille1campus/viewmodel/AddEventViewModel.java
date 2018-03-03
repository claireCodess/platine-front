package huntermahroug.com.lille1campus.viewmodel;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import huntermahroug.com.lille1campus.model.EventToAdd;

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

    private TwoWayBoundString name;

    public AddEventViewModel(Fragment fragment) {
        this.event = new EventToAdd();
        this.fragment = fragment;
    }

    @Bindable
    public TwoWayBoundString getName() {
        return event.getName();
    }

    public void onSubmitForm() {
        System.out.println(event.getName().get());
    }

}
