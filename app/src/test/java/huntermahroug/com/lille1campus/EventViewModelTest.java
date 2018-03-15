package huntermahroug.com.lille1campus;

import android.app.Fragment;

import junit.framework.Assert;

import org.junit.Test;

import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.model.Event;
import huntermahroug.com.lille1campus.viewmodel.EventViewModel;

/**
 * Created by Claire on 15/03/2018.
 */

public class EventViewModelTest {
    /**
     * Ce test unitaire sert à vérifier que les informations d'un objet Event qu'on a récupéré via l'API
     * ont bien été interprétées correctement pour l'affichage.
     */
    @Test
    public void testGetEvent() {
        // On crée un événement de test. On imagine qu'on a récupéré cet événement via une requête getOneEvent().
        Category category = new Category("Éducatif", R.drawable.ic_category_educational);
        Event testEvent = new Event(1, "Conférence", "2018-03-27T11:00:00+00:00",
                "Villeneuve-d'Ascq", category, 3, 0, "jean.dupont@bidule.com", "Description test");

        // On crée un fragment vide, juste pour pouvoir remplir le constructeur de l'EventViewModel correspondant. (Il ne sert à rien pour ce test.)
        Fragment fragment = new Fragment();

        // Enfin, on crée un EventViewModel qui représente l'événement dont les champs sont tels qu'ils sont affichés sur l'écran.
        EventViewModel displayedEvent = new EventViewModel(testEvent, fragment);

        // On fait les tests, champ par champ.
        Assert.assertEquals("Conférence", displayedEvent.getName());
        Assert.assertEquals("mardi 27 mars 2018 à 11h00", displayedEvent.getDate());
        Assert.assertEquals("Villeneuve-d'Ascq", displayedEvent.getLocation());
        Assert.assertNotNull(displayedEvent.getCategory());
        Assert.assertEquals("Éducatif", displayedEvent.getCategory().getName());
        Assert.assertEquals(R.drawable.ic_category_educational, displayedEvent.getCategory().getImgResourceId());
        Assert.assertEquals("Gratuit", displayedEvent.getPrice());
        Assert.assertEquals("jean.dupont@bidule.com", displayedEvent.getEmail());
        Assert.assertEquals("Description test", displayedEvent.getDescription());
    }
}
