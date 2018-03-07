package huntermahroug.com.lille1campus;

import android.app.Application;
import android.widget.Toast;

import org.androidannotations.annotations.EApplication;

import java.util.List;

import huntermahroug.com.lille1campus.model.Category;
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

        lilleCampusAPI.getAllCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                /*
                 * Dans cette liste de catégories récupérée, on attribue à chaque
                 * catégorie une icône déjà stockée dans l'appli (ce qui fait que
                 * la liste des catégories est finalement mi-dynamique, mi-statique).
                 */
                for(Category category : categories) {
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
                setCategoriesList(categories);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
            }
        });

    }

}
