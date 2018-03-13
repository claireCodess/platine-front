package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusAPI;
import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.FragmentEventListBinding;
import huntermahroug.com.lille1campus.model.EventLight;
import huntermahroug.com.lille1campus.util.Util;
import huntermahroug.com.lille1campus.util.adapter.EventAdapter;
import huntermahroug.com.lille1campus.view.MainActivity_;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@DataBound
@EFragment(R.layout.fragment_event_list)
public class EventListFragment extends Fragment {

    @ViewById(R.id.error_view)
    TextView errorView;

    @StringRes(R.string.internet_connection_error_msg)
    String internetConnectionErrorMsg;

    public static String INSEARCHFRAG_PARAM = "insearchfrag_param";
    public static String INCATEGORIESFRAG_PARAM = "incategoriesfrag_param";
    public static String SEARCHQUERY_PARAM = "searchquery_param";
    public static String CATEGORYID_PARAM = "categoryid_param";

    private LilleCampusApplication lilleCampusApplication;
    private LilleCampusAPI lilleCampusAPI;

    private OnFragmentInteractionListener mListener;

    private boolean inSearchFragment;
    private boolean inCategoriesFragment;
    private String searchQuery;
    private int categoryId;

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
     * @param searchQuery    Boolean
     *
     * @return Une nouvelle instance du fragment EventListFragment.
     */
    public static EventListFragment newInstance(boolean inSearchFragment, boolean inCategoriesFragment, String searchQuery, int categoryId) {
        EventListFragment_ fragment = new EventListFragment_();
        Bundle args = new Bundle();
        args.putBoolean(INSEARCHFRAG_PARAM, inSearchFragment);
        args.putBoolean(INCATEGORIESFRAG_PARAM, inCategoriesFragment);
        args.putString(SEARCHQUERY_PARAM, searchQuery);
        args.putInt(CATEGORYID_PARAM, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inSearchFragment = getArguments().getBoolean(INSEARCHFRAG_PARAM);
            inCategoriesFragment = getArguments().getBoolean(INCATEGORIESFRAG_PARAM);
            searchQuery = getArguments().getString(SEARCHQUERY_PARAM);
            categoryId = getArguments().getInt(CATEGORYID_PARAM);
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
        lilleCampusApplication = ((LilleCampusApplication) this.getActivity().getApplication());
        lilleCampusAPI = lilleCampusApplication.getLilleCampusAPI();
        ((MainActivity_)this.getActivity()).showProgressBar();
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

        if(Util.isConnected(getActivity())) {
            if(lilleCampusApplication.getCategoriesList() == null) {
                lilleCampusApplication.getCategoriesFromSharedPrefOrAPI();
            }
            if (!inSearchFragment && !inCategoriesFragment) {
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
            } else if (inSearchFragment && !inCategoriesFragment) {
                lilleCampusAPI.getEventsbyname(searchQuery, new Callback<List<EventLight>>() {
                    @Override
                    public void success(List<EventLight> events, Response response) {
                        showEvents(events);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
            } else if (!inSearchFragment) {
                lilleCampusAPI.getEventsbycatid(categoryId, new Callback<List<EventLight>>() {
                    @Override
                    public void success(List<EventLight> events, Response response) {
                        showEvents(events);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getResponse().getStatus() == 404) {
                            // Si le code d'erreur est 404, alors cela veut dire
                            // qu'il n'y a aucun événement dans cette catégorie/
                            // On appelle showEvents avec une liste vide, qui va
                            // faire le travail.
                            showEvents(new ArrayList<>());
                        } else {
                            // C'est une autre erreur, non gérée pour le moment...
                        }
                    }
                });
            }
        } else {
            ((MainActivity_)this.getActivity()).hideProgressBar();
            errorView.setText(internetConnectionErrorMsg);
            errorView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Afficher les événements récupérés dans la vue.
     * @param events Les événements à afficher
     */
    private void showEvents(List<EventLight> events) {
        ((MainActivity_)this.getActivity()).hideProgressBar();

        if(!events.isEmpty()) {
            // Afficher cette liste dans le RecyclerView.
            EventAdapter adapter = new EventAdapter(events, this);

            binding.listEvents.setAdapter(adapter);
            binding.listEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            // Si la liste est vide, alors c'est qu'il n'y a aucun événement !
            // (Après une recherche, dans une catégorie, ou aucun événement tout court...)
            errorView.setVisibility(View.VISIBLE);
        }
    }

}
