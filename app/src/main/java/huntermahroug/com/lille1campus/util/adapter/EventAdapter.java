package huntermahroug.com.lille1campus.util.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.EventItemLayoutBinding;
import huntermahroug.com.lille1campus.model.EventLight;
import huntermahroug.com.lille1campus.util.listener.EventItemClickListener;
import huntermahroug.com.lille1campus.viewmodel.EventLightViewModel;

/**
 * Created by Claire on 22/01/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.BindingHolder> {

    /**
     * La liste des événements
     */
    private List<EventLight> events;

    /**
     * Le contexte
     */
    Context context;

    /**
     * Le listener pour le clic de chaque item
     * de la liste des événements
     */
    private EventItemClickListener listener;

    public void setListener(EventItemClickListener listener) {
        this.listener = listener;
    }

    public EventAdapter(List<EventLight> events, Context context) {
        this.events = events;
        this.context = context;
    }

    /**
     * Créer le "view holder" par type
     * @param parent La vue parent
     * @param viewType Le type de vue
     */
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Récupérer l'inflater et récupérer la vue par l'ID ressource eventLayout
        EventItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.event_item_layout, parent, false);

        // Retourner le ViewHolder avec la vue
        return new BindingHolder(binding);

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
     * Lier le view holder avec les événements
     * @param holder Le view holder
     * @param position La position actuelle
     */
    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {

        EventItemLayoutBinding binding = holder.binding;
        binding.setEvent(new EventLightViewModel(events.get(position), position));

        // Sauvegarder les informations dans le holder
        /*holder.eventLight = item;
        holder.nameText.setText(item.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM 'à' HH'h'mm");
        // Ajouter dans le SimpleDateFormat un Locale non obligatoire mais peut être nécessaire si on veut rendre notre appli internationale : , Locale.FRENCH);
        holder.dateText.setText(item.getDate());
        holder.locationText.setText(item.getLocation());

        // Associer le nom de la catégorie à l'image
        switch(item.getCategory()) {
            case "Culture":
                holder.categoryImage.setImageResource(R.drawable.ic_category_cultural);
                break;
            case "educational":
                holder.categoryImage.setImageResource(R.drawable.ic_category_educational);
                break;
            case "outing":
                holder.categoryImage.setImageResource(R.drawable.ic_category_outing);
                break;
            case "Sport":
                holder.categoryImage.setImageResource(R.drawable.ic_category_sport);
        }

        holder.itemView.setTag(item);

        if ((position % 2) == 0) {
            holder.itemView.setBackgroundResource(R.color.white);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorSecondaryLight);
        }*/

    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private EventItemLayoutBinding binding;

        public BindingHolder(EventItemLayoutBinding binding) {
            super(binding.listItemLayout);
            this.binding = binding;
        }
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder {

        // Vue texte pour l'affichage du nom de l'événement
        public TextView nameText;

        // Vue texte pour l'affichage de la date de l'événement
        public TextView dateText;

        // Vue texte pour l'affichage du lieu de l'événement
        public TextView locationText;

        // Image pour la catégorie de l'événement
        public ImageView categoryImage;

        // L'événement dans la liste des événements
        public EventLight eventLight;

        /**
         * Constructeur ViewHolder
         * @param itemView La vue de l'élément
         */
        /*public ViewHolder(View itemView) {
            super(itemView);

            // Définir le listener pour le clic d'un item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, eventLight);
                }
            });

            // Lier nameText, dateText et locationText aux trois vues texte
            nameText = itemView.findViewById(R.id.event_name);
            dateText = itemView.findViewById(R.id.event_date);
            locationText = itemView.findViewById(R.id.event_location);
            categoryImage = itemView.findViewById(R.id.icon_category);
        }

    }*/

}
