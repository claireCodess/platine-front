package huntermahroug.com.lille1campus.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.databinding.FragmentCategoriesBinding;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.util.adapter.CategoryAdapter;
import huntermahroug.com.lille1campus.view.MainActivity_;


/**
 * Le Fragment de l'écran de la liste des catégories.
 */
@DataBound
@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.error_view)
    TextView errorView;

    @StringRes(R.string.internet_connection_error_msg)
    String internetConnectionErrorMsg;

    // Constructeur vide obligatoire
    public CategoriesFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((LilleCampusApplication)this.getActivity().getApplication()).getCategoriesFromSharedPrefOrAPI();
        List<Category> categoriesList = ((LilleCampusApplication) this.getActivity().getApplication()).getCategoriesList();
        if(categoriesList != null) {
            showCategories(categoriesList);
        } else {
            ((MainActivity_)this.getActivity()).hideProgressBar();
            errorView.setText(internetConnectionErrorMsg);
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @BindingObject
    FragmentCategoriesBinding binding;

    /**
     * Afficher les catégories dans la vue.
     */
    private void showCategories(List<Category> categoriesList) {
        ((MainActivity_)this.getActivity()).hideProgressBar();

        // Afficher cette liste dans le RecyclerView
        CategoryAdapter adapter = new CategoryAdapter(categoriesList, this);

        binding.listCategories.setAdapter(adapter);
        binding.listCategories.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
