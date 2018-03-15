package huntermahroug.com.lille1campus;

import android.app.Fragment;

import junit.framework.Assert;

import org.junit.Test;

import huntermahroug.com.lille1campus.model.EventPost;
import huntermahroug.com.lille1campus.model.EventToAdd;
import huntermahroug.com.lille1campus.viewmodel.AddEventViewModel;
import huntermahroug.com.lille1campus.viewmodel.TwoWayBoundDouble;
import huntermahroug.com.lille1campus.viewmodel.TwoWayBoundInteger;
import huntermahroug.com.lille1campus.viewmodel.TwoWayBoundString;

/**
 * Created by Claire on 15/03/2018.
 */

public class AddEventViewModelTest {
    /**
     * Ce test unitaire sert à vérifier que les informations saisies dans le formulaire
     * d'un objet EventPost qu'on va envoyer à l'API ont bien été interprétées correctement
     * pour l'affichage.
     */
    @Test
    public void testPostEvent() {
        // On crée un fragment vide, juste pour pouvoir remplir le constructeur de l'AddEventViewModel correspondant. (Il ne sert à rien pour ce test.)
        Fragment fragment = new Fragment();

        // On crée un AddEventViewModel qui représente le formulaire pour proposer un événement.
        AddEventViewModel addEventViewModel = new AddEventViewModel(fragment);

        // On modifie le model de l'événement "bindé" (on imagine que l'utilisateur a saisi toutes les informations).
        EventToAdd event = addEventViewModel.getEvent();
        event.setName(new TwoWayBoundString("Conférence"));
        event.setCategoryId(3);
        event.setDate(new TwoWayBoundString("mardi 27 mars 2018"));
        event.setTime(new TwoWayBoundString("11:00"));
        event.setLocation(new TwoWayBoundString("Villeneuve-d'Ascq"));
        event.setDescription(new TwoWayBoundString("Description test"));
        event.setEmail(new TwoWayBoundString("jean.dupont@bidule.com"));
        event.setPrice(new TwoWayBoundDouble(2.35));
        event.setNbPlaces(new TwoWayBoundInteger(10));

        // On crée un événement de test. On imagine qu'on va envoyer l'objet représentant cet événement via une requête postEvent().
        EventPost eventPost = addEventViewModel.createEventPost();

        // On fait les tests, champ par champ.
        Assert.assertEquals("Conférence", eventPost.getName());
        Assert.assertEquals(3, eventPost.getCategoryId());
        Assert.assertEquals("2018-03-27T11:00:00", eventPost.getDate());
        Assert.assertEquals(235, eventPost.getPrice());
        Assert.assertEquals("Description test", eventPost.getDescription());
        Assert.assertEquals("jean.dupont@bidule.com", eventPost.getEmail());
        Assert.assertEquals("Villeneuve-d'Ascq", eventPost.getLocation());
        Assert.assertEquals(10, eventPost.getTotalPlaces());
    }
}
