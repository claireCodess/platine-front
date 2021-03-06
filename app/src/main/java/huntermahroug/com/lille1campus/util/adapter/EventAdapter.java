package huntermahroug.com.lille1campus.util.adapter;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.EventItemLayoutBinding;
import huntermahroug.com.lille1campus.model.EventLight;
import huntermahroug.com.lille1campus.viewmodel.EventLightViewModel;

/**
 * Adapteur pour la liste des événements dans le RecyclerView.
 * @author Claire
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.BindingHolder> {

    /**
     * La liste des événements
     */
    private List<EventLight> events;

    /**
     * Le fragment correspondant à cet Adapter
     */
    private Fragment fragment;

    public EventAdapter(List<EventLight> events, Fragment fragment) {
        this.events = events;
        this.fragment = fragment;
    }

    /**
     * Créer le "view holder" par type
     * @param parent La vue parent
     * @param viewType Le type de vue
     */
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Récupérer l'inflater et récupérer la vue par l'ID ressource eventLayout
        EventItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.event_item_layout, parent,false);

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
        binding.setEventLight(new EventLightViewModel(events.get(position), position, fragment));

    }

    /**
     * Classe Holder pour fixer la vue à utiliser pour l'affichage d'un événement.
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private EventItemLayoutBinding binding;

        public BindingHolder(EventItemLayoutBinding binding) {
            super(binding.listItemLayout);
            this.binding = binding;
        }
    }

}
