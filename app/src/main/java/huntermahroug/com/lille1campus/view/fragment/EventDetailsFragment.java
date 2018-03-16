package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.FragmentEventDetailsBinding;
import huntermahroug.com.lille1campus.model.Event;
import huntermahroug.com.lille1campus.view.MainActivity_;
import huntermahroug.com.lille1campus.viewmodel.EventViewModel;


/**
 * Le Fragment de l'écran de la liste des catégories.
 */
@DataBound
@EFragment(R.layout.fragment_event_details)
public class EventDetailsFragment extends Fragment {

    private static final String EVENT_PARAM = "event_param";

    private Event event;

    // Constructeur vide obligatoire
    public EventDetailsFragment() {
    }

    /**
     * Créer une nouvelle instance de ce fragment en utilisant l'ID
     * de l'événement dont on veut afficher les détails.
     *
     * @param event L'événement dont les détails sont à consulter
     * @return Une nouvelle instance du fragment EventDetailsFragment.
     */
    public static EventDetailsFragment newInstance(Event event) {
        EventDetailsFragment_ fragment = new EventDetailsFragment_();
        Bundle args = new Bundle();
        args.putParcelable(EVENT_PARAM, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(EVENT_PARAM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity_)this.getActivity()).hideProgressBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @BindingObject
    FragmentEventDetailsBinding binding;

    /**
     * Rafraîchit la vue avec les données récupérées grâce à l'API.
     */
    @AfterViews
    void refreshView() {
        binding.setEvent(new EventViewModel(event, EventDetailsFragment.this));
    }

}
