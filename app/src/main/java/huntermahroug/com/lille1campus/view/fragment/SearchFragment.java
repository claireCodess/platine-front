package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import huntermahroug.com.lille1campus.R;


/**
 * Le Fragment de l'écran de la recherche d'un événement.
 */
@EFragment(R.layout.fragment_search)
public class SearchFragment extends Fragment {

    @ViewById(R.id.search_view)
    SearchView searchView;

    @ViewById(R.id.list_item_layout)
    View resultContentView;

    // Constructeur vide obligatoire
    public SearchFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentManager childFragmentManager = getChildFragmentManager();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.search_fragment_placeholder, EventListFragment_.newInstance(true, false, query, -1));
                fragmentTransaction.commit();

                return true;
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
    }

}
