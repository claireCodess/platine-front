package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@DataBound
@EFragment(R.layout.fragment_event_details)
public class EventDetailsFragment extends Fragment {

    private static final String EVENT_PARAM = "event_param";

    private Event event;

    private OnFragmentInteractionListener mListener;

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @BindingObject
    FragmentEventDetailsBinding binding;

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

    /**
     * Rafraîchit la vue avec les données récupérées grâce à l'API.
     */
    @AfterViews
    void refreshView() {
        binding.setEvent(new EventViewModel(event, EventDetailsFragment.this));
    }

}
