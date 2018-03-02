package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import org.androidannotations.annotations.EFragment;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Event;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_event_details)
public class EventDetailsFragment extends Fragment {

    private LilleCampusAPI lilleCampusAPI;

    private static final String ID_EVENT = "id_event";

    private int idEvent;

    private OnFragmentInteractionListener mListener;

    public EventDetailsFragment() {

    }

    /**
     * Créer une nouvelle instance de ce fragment en utilisant l'ID
     * de l'événement dont on veut afficher les détails.
     *
     * @param idEvent L'ID de l'événement à consulter
     * @return Une nouvelle instance du fragment EventDetailsFragment.
     */
    public static EventDetailsFragment newInstance(int idEvent) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ID_EVENT, idEvent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idEvent = getArguments().getInt(ID_EVENT);
            lilleCampusAPI = ((LilleCampusApplication) this.getActivity().getApplication()).getLilleCampusAPI();
            refreshView();
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details, container, false);
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

    /**
     * Rafraîchit la vue avec des données pour l'instant statiques (par la suite, de la base de données).
     */
    private void refreshView() {
        lilleCampusAPI.getOneEvent(idEvent, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                System.out.println(event.getName());
                System.out.println(event.getLocation());
                System.out.println(event.getCategory());
                System.out.println(event.getTotalPlaces());
                System.out.println(event.getAvailablePlaces());
                System.out.println(event.getPrice());
                System.out.println(event.getEmail());
                System.out.println(event.getDescription());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
            }
        });

    }

}
