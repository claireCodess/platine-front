package huntermahroug.com.lille1campus.util.adapter;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.CategoryItemLayoutBinding;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.viewmodel.CategoryViewModel;

/**
 * Created by Claire on 04/03/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BindingHolder> {

    /**
     * La liste des événements
     */
    private List<Category> categories;

    /**
     * Le fragment correspondant à cet Adapter
     */
    private Fragment fragment;

    public CategoryAdapter(List<Category> categories, Fragment fragment) {
        this.categories = categories;
        this.fragment = fragment;
    }

    /**
     * Créer le "view holder" par type
     * @param parent La vue parent
     * @param viewType Le type de vue
     */
    @Override
    public CategoryAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Récupérer l'inflater et récupérer la vue par l'ID ressource category_item_layout
        CategoryItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_item_layout, parent, false);

        // Retourner le ViewHolder avec la vue
        return new BindingHolder(binding);

    }

    /**
     * Lier le view holder avec les événements
     * @param holder Le view holder
     * @param position La position actuelle
     */
    @Override
    public void onBindViewHolder(CategoryAdapter.BindingHolder holder, int position) {

        CategoryItemLayoutBinding binding = holder.binding;
        binding.setCategory(new CategoryViewModel(categories.get(position), position, fragment));

    }

    /**
     * Récupérer la taille de la liste des événements dans l'Adapter
     * @return La taille de la liste des événements dans l'Adapter
     */
    @Override
    public int getItemCount() {
        return categories.size();
    }

    /**
     * Classe Holder pour fixer la vue à utiliser pour l'affichage d'un événement.
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private CategoryItemLayoutBinding binding;

        public BindingHolder(CategoryItemLayoutBinding binding) {
            super(binding.categoryItemLayout);
            this.binding = binding;
        }
    }

}
