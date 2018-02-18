package huntermahroug.com.lille1campus;

import android.app.Application;
import android.widget.Toast;

import org.androidannotations.annotations.EApplication;

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

    public LilleCampusAPI getLilleCampusAPI() {
        return lilleCampusAPI;
    }

    public void setLilleCampusAPI(LilleCampusAPI lilleCampusAPI) {
        this.lilleCampusAPI = lilleCampusAPI;
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
    }

}
