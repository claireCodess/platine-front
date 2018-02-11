package huntermahroug.com.lille1campus.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import huntermahroug.com.lille1campus.BottomNavigationViewHelper;
import huntermahroug.com.lille1campus.EventListFragment_;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.adapters.RecyclerViewAdapter;
import huntermahroug.com.lille1campus.listeners.EventItemClickListener;
import huntermahroug.com.lille1campus.model.EventLite;

@EActivity
public class MainActivity extends AppCompatActivity { //implements EventListFragment.OnFragmentInteractionListener {

    @ViewById(R.id.listEvents)
    RecyclerView listEventsView;

    @ViewById(R.id.bottom_nav_layout)
    BottomNavigationView bottomNavigationView;

    @ViewById(R.id.titleBar)
    Toolbar toolBar;

    // private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        // get fragment manager
        FragmentManager fm = getFragmentManager();

        // add
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_placeholder, new EventListFragment_());
        ft.commit();

        // setupDrawer();

        /* setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true); */

        // refreshView();
    }

    /* @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Synchroniser l'état de basculement après que la méthode
        // onRestoreInstanceState ait été appelée.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Peut-être à utiliser plus tard
        // int id = item.getItemId();

        // Activer le basculement du menu latéral
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.bottom_nav_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } */

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
        listEventsView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Construit le menu latéral
     */
    /* private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, bottomNavigationView,
                R.string.drawer_open, R.string.drawer_close) {

            // Appelé quand le menu latéral vient d'être ouvert
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // appelle onPrepareOptionsMenu()
            }

            // Appelé quand le menu latéral vient d'être fermé
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu(); // appelle onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        bottomNavigationView.addDrawerListener(mDrawerToggle);
    } */
}
