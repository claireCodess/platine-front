package huntermahroug.com.lille1campus;

import java.util.List;

import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.model.Event;
import huntermahroug.com.lille1campus.model.EventLight;
import huntermahroug.com.lille1campus.model.EventPost;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Claire on 04/02/2018.
 */

public interface LilleCampusAPI {

    String ENDPOINT = "http://54.37.23.94/platine-back/web/app_dev.php/";

    @GET("/events")
    void getAllEvents(Callback<List<EventLight>> callback);

    @GET("/events/{eventid}")
    void getOneEvent(@Path("eventid") int id, Callback<Event> callback);

    @POST("/events")
    void postEvent(@Body EventPost event, Callback<EventPost> callback);

    @GET("/categories")
    void getAllCategories(Callback<List<Category>> callback);

    @GET("/eventsbyname/{name}")
    void getEventsbyname(@Path("name") String name, Callback<List<EventLight>> callback);

    @GET("/eventsbycat/{catid}")
    void getEventsbycatid(@Path("catid") int catid, Callback<List<EventLight>> callback);

}
