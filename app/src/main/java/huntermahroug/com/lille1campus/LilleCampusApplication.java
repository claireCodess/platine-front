package huntermahroug.com.lille1campus;

import android.app.Application;
import android.widget.Toast;

import org.androidannotations.annotations.EApplication;

import java.util.List;

import huntermahroug.com.lille1campus.model.Category;
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

    /**
     * Liste des catégories statique. Si la liste des catégories
     * côté back est amenée à être modifiée, alors ça deviendra
     * dynamique et disparaîtra de cette classe.
     */
    private List<Category> categoriesList;

    // Noms des 4 catégories statiques.
    public static String CULTURAL = "Culturel";
    public static String EDUCATIONAL = "Educatif";
    public static String OUTING = "Sortie";
    public static String SPORT = "Sportif";

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
        /*categoriesList = new ArrayList<>();
        categoriesList.add(new Category(CULTURAL, R.drawable.ic_category_cultural));
        categoriesList.add(new Category(EDUCATIONAL, R.drawable.ic_category_educational));
        categoriesList.add(new Category(OUTING, R.drawable.ic_category_outing));
        categoriesList.add(new Category(SPORT, R.drawable.ic_category_sport));*/
    }

}
