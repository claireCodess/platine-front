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

    public static String INSEARCHFRAG_PARAM = "insearchfrag_param";
    public static String INSEARCHNAME_PARAM = "name";

    private LilleCampusAPI lilleCampusAPI;

    private OnFragmentInteractionListener mListener;

    private boolean inSearchFragment;
    private String inSearchName;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Créer une nouvelle instance de ce fragment en utilisant un booléen
     * pour indiquer si ce fragment se trouve dans le fragment de recherche.
     *
     * @param inSearchFragment Booléen à true si ce fragment est créé dans le
     *                         fragment de recherche, false s'il est crée
     *                         directement dans le MainActivity.
     * @param inSearchName    Boolean
     *
     * @return Une nouvelle instance du fragment EventListFragment.
     */
    public static EventListFragment newInstance(boolean inSearchFragment, String inSearchName) {
        EventListFragment_ fragment = new EventListFragment_();
        Bundle args = new Bundle();
        args.putBoolean(INSEARCHFRAG_PARAM, inSearchFragment);
        args.putString(INSEARCHNAME_PARAM,inSearchName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inSearchFragment = getArguments().getBoolean(INSEARCHFRAG_PARAM);
            inSearchName = getArguments().getString(INSEARCHNAME_PARAM);
        }
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
     * Rafraîchit la vue avec les données récupérées grâce à l'API.
     */
    private void refreshView() {

        if(!inSearchFragment)
        {
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
        else
        {
            lilleCampusAPI.getEventsbyname(inSearchName,new Callback<List<EventLight>>() {
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


    }

    /**
     * Afficher les événements récupérés dans la vue.
     * @param events Les événements à afficher
     */
    private void showEvents(List<EventLight> events) {
        /*
         * Afficher cette liste dans le RecyclerView.
         */
        EventAdapter adapter = new EventAdapter(events, this);

        binding.listEvents.setAdapter(adapter);
        binding.listEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
