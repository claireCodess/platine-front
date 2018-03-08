package huntermahroug.com.lille1campus.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.util.helper.BottomNavigationViewHelper;
import huntermahroug.com.lille1campus.view.fragment.AddEventFragment_;
import huntermahroug.com.lille1campus.view.fragment.CategoriesFragment_;
import huntermahroug.com.lille1campus.view.fragment.EventListFragment_;
import huntermahroug.com.lille1campus.view.fragment.SearchFragment_;

@EActivity
public class MainActivity extends AppCompatActivity { //implements EventListFragment.OnSearchPerformedListener {

    @ViewById(R.id.list_events)
    RecyclerView listEventsView;

    @ViewById(R.id.bottom_nav_layout)
    BottomNavigationView bottomNavigationView;

    @ViewById(R.id.titleBar)
    Toolbar toolBar;

    @ViewById(R.id.progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.fragment_placeholder)
    FrameLayout fragmentPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        final FragmentManager fragmentManager = getFragmentManager();

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        // TODO : si on a appuyé sur un élément du menu et qu'on réappuie dessus ça ne fait rien
                        case R.id.nav_view:
                            // TODO : migrer l'appel API getAll ici
                            fragmentTransaction.add(R.id.fragment_placeholder, EventListFragment_.newInstance(false, false, "", -1));
                            break;

                        case R.id.nav_search:
                            fragmentTransaction.add(R.id.fragment_placeholder, new SearchFragment_());
                            break;

                        case R.id.nav_categories:
                            fragmentTransaction.add(R.id.fragment_placeholder, new CategoriesFragment_());
                            break;

                        case R.id.nav_add:
                            fragmentTransaction.add(R.id.fragment_placeholder, new AddEventFragment_());

                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                }
            }
        );

        // add
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, new EventListFragment_());
        fragmentTransaction.commit();

    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        fragmentPlaceholder.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        fragmentPlaceholder.setVisibility(View.INVISIBLE);
    }

}
