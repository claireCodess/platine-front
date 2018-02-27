package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.FragmentEventListBinding;
import huntermahroug.com.lille1campus.model.EventLight;
import huntermahroug.com.lille1campus.util.adapter.EventAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@DataBound
@EFragment(R.layout.fragment_event_list)
public class EventListFragment extends Fragment {

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

    @BindingObject
    FragmentEventListBinding binding;

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
        EventAdapter adapter = new EventAdapter(events, this);

        binding.listEvents.setAdapter(adapter);
        binding.listEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
