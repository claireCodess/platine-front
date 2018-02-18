package huntermahroug.com.lille1campus;

import java.util.List;

import huntermahroug.com.lille1campus.model.EventLight;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Claire on 04/02/2018.
 */

public interface LilleCampusAPI {

    String ENDPOINT = "http://54.37.23.94/platine-back/web/app_dev.php/";

    @GET("/events")
    void getAllEvents(Callback<List<EventLight>> callback);

    @GET("/events/{eventid}")
    void getOneEvent(@Path("eventid") int id, Callback<List<EventLight>> callback);

}
