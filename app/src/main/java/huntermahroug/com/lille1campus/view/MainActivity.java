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

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.EventLight;
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
                            fragmentTransaction.add(R.id.fragment_placeholder, EventListFragment_.newInstance(false, "tou"));
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

    /**
     * Récupère les données pour l'instant statiques (par la suite, de la base de données).
     */
    public List<EventLight> getAllEvents() {
        List<EventLight> items = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();

        /*calendar.set(2018, 1, 2, 19, 30);
        items.add(new EventLight(1, "Soirée bowling", calendar.getTime(), "Bowling Van Gogh, Villeneuve-d'Ascq", "outing"));

        calendar.set(2018, 1, 3, 11, 0);
        items.add(new EventLight(2, "Conférence astronomie", calendar.getTime(), "Lilliad, Lille 1", "educational"));

        calendar.set(2018, 1, 5, 20, 0);
        items.add(new EventLight(3, "Course hivernale", calendar.getTime(), "Halle Vallin, Lille 1", "sport"));

        calendar.set(2018, 1, 6, 10, 0);
        items.add(new EventLight(4, "Forum métiers de l'avenir", calendar.getTime(), "Lilliad, Lille 1", "educational"));

        calendar.set(2018, 1, 6, 12, 30);
        items.add(new EventLight(5, "Déjeuner technologique", calendar.getTime(), "Amphi Bacchus, M5, Lille 1", "educational"));

        calendar.set(2018, 1, 6, 18, 30);
        items.add(new EventLight(6, "Atelier langues", calendar.getTime(), "Maison des langues, Lille 1", "educational"));

        calendar.set(2018, 1, 7, 14, 0);
        items.add(new EventLight(7, "Concours sciences", calendar.getTime(), "Lilliad, Lille 1", "educational"));

        calendar.set(2018, 1, 8, 20, 30);
        items.add(new EventLight(8, "Soirée rock", calendar.getTime(), "MDE, Lille 1", "cultural"));*/

        return items;
    }

}
