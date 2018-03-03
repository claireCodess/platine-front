package huntermahroug.com.lille1campus.model;

import huntermahroug.com.lille1campus.viewmodel.TwoWayBoundString;

/**
 * Created by Claire on 04/03/2018.
 */

public class EventToAdd {

    private TwoWayBoundString name;

    public EventToAdd() {
        name = new TwoWayBoundString();
    }

    public TwoWayBoundString getName() {
        return name;
    }

    public void setName(TwoWayBoundString name) {
        this.name = name;
    }
}
