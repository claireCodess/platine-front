package huntermahroug.com.lille1campus.util.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import huntermahroug.com.lille1campus.model.Category;

/**
 * Created by Claire on 14/03/2018.
 *
 * ArrayAdapter customisé qui met en texte gris la première entrée (catégorie)
 * et la cache dès qu'on appuie sur le Spinner.
 */

public class CategorySpinnerArrayAdapter extends ArrayAdapter<Category> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Category> categoryChoicesList;
    private final int mResource;
    private final int mTextViewResourceId;
    private final int mImageViewResourceId;

    public CategorySpinnerArrayAdapter(@NonNull Context context, @LayoutRes int resource,
                                       @NonNull int textViewResourceId, @NonNull int imageViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        mTextViewResourceId = textViewResourceId;
        mImageViewResourceId = imageViewResourceId;
        categoryChoicesList = objects;
    }

    /**
     * Méthode servant à mettre en texte gris la première entrée.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, null, parent);
        ConstraintLayout constraintLayout = (ConstraintLayout)view;
        TextView textView = constraintLayout.findViewById(mTextViewResourceId);
        ImageView imageView = constraintLayout.findViewById(mImageViewResourceId);

        if(position == 0) {
            textView.setTextColor(0xffa8a8a8); // Même couleur que le "hint" d'un EditText
            textView.setPadding(16, 0, 0, 0);
        } else {
            imageView.setImageResource(categoryChoicesList.get(position).getImgResourceId());
            imageView.setPadding(8, 0, 0, 0);
        }

        // Dans tous les cas, également définir le contenu du texte et la
        // taille du texte de la vue à 18sp (pareil que les autres champs)
        textView.setText(categoryChoicesList.get(position).getName());
        textView.setTextSize(18);

        view = constraintLayout;
        return view;
    }

    /**
     * Méthode servant à cacher la première entrée dès qu'on appuie sur le Spinner.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View view;

        if(position == 0) {
            // Cacher la première entrée du Spinner dès sa sélection
            ConstraintLayout constraintLayout = new ConstraintLayout(getContext());
            TextView textView = new TextView(getContext());
            textView.setHeight(0);
            constraintLayout.addView(textView);
            constraintLayout.setVisibility(View.GONE);
            view = constraintLayout;
        } else {
            // Mettre le paramètre convertView à null pour empêcher
            // la réutilisation de vues particulières
            view = super.getDropDownView(position, null, parent);
            // Egalement définir le contenu du texte
            ConstraintLayout constraintLayout = (ConstraintLayout)view;
            ((ImageView)constraintLayout.findViewById(mImageViewResourceId))
                    .setImageResource(categoryChoicesList.get(position).getImgResourceId());
            ((TextView)constraintLayout.findViewById(mTextViewResourceId))
                    .setText(categoryChoicesList.get(position).getName());
        }

        // Cacher le scroll bar car il apparaît parfois inutilement ; il n'empêche pas le scroll
        parent.setVerticalScrollBarEnabled(false);
        return view;
    }

}
