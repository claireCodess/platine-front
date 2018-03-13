package huntermahroug.com.lille1campus;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidannotations.annotations.EApplication;

import java.util.Arrays;
import java.util.List;

import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.util.Util;
import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Claire on 18/02/2018.
 *
 * Classe servant à stocker les variables globales (notamment
 * la variable de l'API), communes à toutes les activités et
 * fragments de l'application.
 */
@EApplication
public class LilleCampusApplication extends Application {

    private LilleCampusAPI lilleCampusAPI;

    /*
     * Liste des catégories, dynamique, récupérée à partir d'une
     * requête au démarrage de l'appli
     */
    private List<Category> categoriesList;

    public LilleCampusAPI getLilleCampusAPI() {
        return lilleCampusAPI;
    }

    public void setLilleCampusAPI(LilleCampusAPI lilleCampusAPI) {
        this.lilleCampusAPI = lilleCampusAPI;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lilleCampusAPI = new RestAdapter.Builder()
            .setEndpoint(LilleCampusAPI.ENDPOINT)
            .setErrorHandler(new ErrorHandler() {
                @Override
                public Throwable handleError(RetrofitError cause) {

                    Response r = cause.getResponse();
                    if (r != null && r.getStatus() == 405) {
                        Toast.makeText(LilleCampusApplication.this, "Impossible d'effectuer cette action", Toast.LENGTH_SHORT).show();
                    }
                    return cause;
                }
            })
            .build().create(LilleCampusAPI.class);
        getCategoriesFromSharedPrefOrAPI();
    }

    /**
     * Appel des préférences de l'appli pour récupérer les catégories.
     * Si ces préférences sont vides (si l'appli vient d'être lancée
     * pour la première fois après installation et qu'on récupère une
     * connexion Internet), alors on appelle la méthode de l'API pour
     * récupérer les catégories.
     *
     * @return La liste des catégories de l'application.
     */
    public void getCategoriesFromSharedPrefOrAPI() {
        // Récupérer les shared preferences
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Récupérer la préférence "catégories"
        String jsonText = sharedPref.getString(getString(R.string.preference_file_key), null);
        // Si rien n'a été récupéré, alors cela veut dire que l'application n'a pas encore
        // récupéré les catégories de l'appli
        if(jsonText == null) {
            if(Util.isConnected(this)) {
                lilleCampusAPI.getAllCategories(new Callback<List<Category>>() {
                    @Override
                    public void success(List<Category> categories, Response response) {
                        categoriesList = categories;

                        // Stocker dans les shared preferences
                        SharedPreferences.Editor prefsEditor = sharedPref.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(categories);
                        prefsEditor.putString(getString(R.string.preference_file_key), json);
                        prefsEditor.apply();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error.getMessage());
                    }
                });
            }
        }
        // Sinon, on récupère la liste des catégories déjà stockée
        else {
            Gson gson = new Gson();
            categoriesList = Arrays.asList(gson.fromJson(jsonText, Category[].class));
        }

        if(categoriesList != null) {
            /*
             * Dans cette liste de catégories récupérée, on attribue à chaque
             * catégorie une icône déjà stockée dans l'appli (ce qui fait que
             * la liste des catégories est finalement mi-dynamique, mi-statique).
             */
            for (Category category : categoriesList) {
                switch (category.getName()) {
                    case "Culturel":
                        category.setImgResourceId(R.drawable.ic_category_cultural);
                        break;
                    case "Éducatif":
                        category.setImgResourceId(R.drawable.ic_category_educational);
                        break;
                    case "Sortie":
                        category.setImgResourceId(R.drawable.ic_category_outing);
                        break;
                    case "Sportif":
                        category.setImgResourceId(R.drawable.ic_category_sport);
                        break;
                    /*
                     * En cas de problème avec le nom d'une catégorie,
                     * on met une icône par défaut.
                     */
                    default:
                        category.setImgResourceId(R.drawable.ic_event);
                }
            }
        }
    }

}
