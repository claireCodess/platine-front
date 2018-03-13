package huntermahroug.com.lille1campus.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Claire on 12/03/2018.
 *
 * Classe regroupant les méthodes utilitaires.
 */

public class Util {

    /**
     * Méthode permettant de savoir si l'appareil est connecté ou non à Internet.
     *
     * @param context Le contexte du Fragment/Activity appelant la méthode.
     * @return Booléen à true si l'appareil est connecté, false sinon.
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
