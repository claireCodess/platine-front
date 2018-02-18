package huntermahroug.com.lille1campus.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.util.adapter.RecyclerViewAdapter;
import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.util.listener.EventItemClickListener;
import huntermahroug.com.lille1campus.model.EventLight;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_event_list)
public class EventListFragment extends Fragment {

    @ViewById(R.id.listEvents)
    RecyclerView listEventsView;

    private LilleCampusAPI lilleCampusAPI;

    private OnFragmentInteractionListener mListener;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lilleCampusAPI = ((LilleCampusApplication) this.getActivity().getApplication()).getLilleCampusAPI();
        refreshView();
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

    }

    private void showEvents(List<EventLight> events) {
        /*
         * Afficher cette liste dans le RecyclerView.
         */
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(events, R.layout.list_item_layout);

        /*
         * Définir le listener
         */
        adapter.setListener(new EventItemClickListener() {
            @Override
            public void onItemClick(View view, EventLight problemLite) {
                // TODO
            }
        });

        listEventsView.setAdapter(adapter);
        listEventsView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
