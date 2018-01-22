package huntermahroug.com.lille1campus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
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

        /*
         * Afficher cette liste récupérée dans le RecyclerView.
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
