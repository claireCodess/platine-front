package huntermahroug.com.lille1campus;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import huntermahroug.com.lille1campus.activities.MainActivity;
import huntermahroug.com.lille1campus.model.EventLite;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSearchPerformedListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_search)
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSearchPerformedListener mListener;

    @ViewById(R.id.search_view)
    SearchView searchView;

    @ViewById(R.id.best_result_view)
    TextView bestResultView;

    @ViewById(R.id.result_layout)
    FrameLayout resultLayout;

    @ViewById(R.id.all_results_view)
    Button allResultsButton;

    @ViewById(R.id.list_item_layout)
    View resultContentView;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSearchPerformed(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnSearchPerformedListener) {
            mListener = (OnSearchPerformedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchPerformedListener");
        }*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentManager childFragmentManager = getChildFragmentManager();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Appeler l'activité qui va aller récupérer une liste d'événements
                MainActivity activity = (MainActivity) getActivity();
                List<EventLite> list = activity.getAllEvents();

                List<EventLite> filteredResults;

                // TODO : on affichera le résultat avec la date la plus récente d'abord
                // En attendant, filtrer la liste
                // On peut utiliser les streams pour optimiser, mais uniquement si la version
                // de l'API est au dessus de 24 (seulement ces versions utilisent Java 8)
                if(Build.VERSION.SDK_INT >= 24) {
                     filteredResults = list.stream() // Convertir la liste en "stream"
                            .filter(event -> event.getName().toLowerCase().contains(query.toLowerCase())) // Vérifier que le nom de l'événement contient la requête
                            .collect(Collectors.toList()); // Convertir le stream en List
                } else {
                    filteredResults = new ArrayList<>();
                    for (EventLite event : list) {
                        // Vérifier que le nom de l'événement contient la requête
                        if (event.getName().toLowerCase().contains(query.toLowerCase())) {
                            filteredResults.add(event);
                        }
                    }
                }

                // Afficher le premier élément de la liste
                /*FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.search_fragment_placeholder, ShowSearchResultsFragment_.newInstance("Soirée rock"));
                fragmentTransaction.commit();*/
                bestResultView.setVisibility(View.VISIBLE);

                if(!filteredResults.isEmpty()) {
                    TextView eventNameText = resultContentView.findViewById(R.id.event_name);
                    eventNameText.setText(filteredResults.get(1).getName());
                    resultLayout.setVisibility(View.VISIBLE);
                }

                allResultsButton.setVisibility(View.VISIBLE);

                // TODO : puis proposer à l'utilisateur d'aller à tous les résultats
                // TODO : (retour à l'écran de la liste des événements)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
    public interface OnSearchPerformedListener {
        void onSearchPerformed(Uri uri);
    }
}
