package huntermahroug.com.lille1campus.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.listeners.EventItemClickListener;
import huntermahroug.com.lille1campus.model.EventLite;

/**
 * Created by Claire on 22/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    /**
     * La liste des événements
     */
    private List<EventLite> events;

    /**
     * L'ID ressource du layout à utiliser
     */
    private int eventLayout;

    /**
     * Le listener pour le clic de chaque item
     * de la liste des événements
     */
    private EventItemClickListener listener;

    public void setListener(EventItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerViewAdapter(List<EventLite> events, int eventLayout) {
        this.events = events;
        this.eventLayout = eventLayout;
    }

    /**
     * Créer le "view holder" par type
     * @param parent La vue parent
     * @param viewType Le type de vue
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Récupérer l'inflater et récupérer la vue par l'ID ressource eventLayout
        View v = LayoutInflater.from(parent.getContext()).inflate(eventLayout, parent, false);

        // Retourner le ViewHolder avec la vue
        return new ViewHolder(v);

    }

    /**
     * Récupérer la taille de la liste des événements dans l'Adapter
     * @return La taille de la liste des événements dans l'Adapter
     */
    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * Lier le view holder avec les problèmes
     * @param holder Le view holder
     * @param position La position actuelle
     */
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        // Chercher l'élément par sa position
        EventLite item = events.get(position);

        // Sauvegarder les informations dans le holder
        holder.eventLite = item;
        holder.nameText.setText(item.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM 'à' HH'h'mm");
        // Ajouter dans le SimpleDateFormat un Locale non obligatoire mais peut être nécessaire si on veut rendre notre appli internationale : , Locale.FRENCH);
        holder.dateText.setText(dateFormat.format(item.getDate()));
        holder.locationText.setText(item.getLocation());
        holder.itemView.setTag(item);

        if ((position % 2) == 0) {
            holder.itemView.setBackgroundResource(R.color.color1);
        } else {
            holder.itemView.setBackgroundResource(R.color.color2);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Vue texte pour l'affichage du nom de l'événement
        public TextView nameText;

        // Vue texte pour l'affichage de la date de l'événement
        public TextView dateText;

        // Vue texte pour l'affichage du lieu de l'événement
        public TextView locationText;

        // L'événement dans la liste des événements
        public EventLite eventLite;

        /**
         * Constructeur ViewHolder
         * @param itemView La vue de l'élément
         */
        public ViewHolder(View itemView) {
            super(itemView);

            // Définir le listener pour le clic d'un item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, eventLite);
                }
            });

            // Lier nameText, dateText et locationText aux trois vues texte
            nameText = itemView.findViewById(R.id.event_name);
            dateText = itemView.findViewById(R.id.event_date);
            locationText = itemView.findViewById(R.id.event_location);
        }

    }

}
