package huntermahroug.com.lille1campus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.adapters.RecyclerViewAdapter;
import huntermahroug.com.lille1campus.listeners.EventItemClickListener;
import huntermahroug.com.lille1campus.model.EventLite;

@EActivity
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.listEvents)
    RecyclerView listEventsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshView();
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
        listEventsView.setLayoutManager(new LinearLayoutManager(this));
    }
}
