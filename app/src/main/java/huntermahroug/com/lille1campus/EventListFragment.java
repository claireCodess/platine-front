package huntermahroug.com.lille1campus;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import huntermahroug.com.lille1campus.adapters.RecyclerViewAdapter;
import huntermahroug.com.lille1campus.listeners.EventItemClickListener;
import huntermahroug.com.lille1campus.model.EventLite;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @ViewById(R.id.listEvents)
    RecyclerView listEventsView;

    private OnFragmentInteractionListener mListener;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
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
        return null;
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
        /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        } */
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        List<EventLite> items = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();

        calendar.set(2017, 9, 5, 19, 30);
        items.add(new EventLite("Soirée bowling", calendar.getTime(), "Bowling Van Gogh, Villeneuve-d'Ascq"));

        calendar.set(2017, 9, 6, 11, 0);
        items.add(new EventLite("Conférence astronomie", calendar.getTime(), "Lilliad, Lille 1"));

        calendar.set(2017, 9, 6, 20, 0);
        items.add(new EventLite("Concert de rock", calendar.getTime(), "MDE, Lille 1"));

        calendar.set(2017, 9, 7, 10, 0);
        items.add(new EventLite("Forum métiers de l'avenir", calendar.getTime(), "Lilliad, Lille 1"));

        calendar.set(2017, 9, 9, 20, 30);
        items.add(new EventLite("Soirée internationale", calendar.getTime(), "Bar L'Apostrophe, Lille"));

        calendar.set(2017, 9, 10, 12, 30);
        items.add(new EventLite("Déjeuner technologique", calendar.getTime(), "Amphi Bacchus, M5, Lille 1"));

        calendar.set(2017, 9, 10, 18, 30);
        items.add(new EventLite("Atelier langues", calendar.getTime(), "Maison des langues, Lille 1"));

        calendar.set(2017, 9, 11, 14, 0);
        items.add(new EventLite("Concours sciences", calendar.getTime(), "Lilliad, Lille 1"));

        /*
         * Afficher cette liste dans le RecyclerView.
         */
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, R.layout.list_item_layout);

        /*
         * Définir le listener
         */
        adapter.setListener(new EventItemClickListener() {
            @Override
            public void onItemClick(View view, EventLite problemLite) {
                // TODO
            }
        });

        listEventsView.setAdapter(adapter);
        listEventsView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
