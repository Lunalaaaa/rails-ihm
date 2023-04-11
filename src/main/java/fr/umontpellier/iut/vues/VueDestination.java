package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Destination.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Button {

    private IDestination destination;
    private ImageView image;
    private boolean appartientAuJoueur = false;

    public VueDestination(IDestination destination) {
        this.destination = destination;
        this.image = new ImageView();
        String v1 = "";
        int nbSpace = 0;
        for (int i = 0; i < this.destination.getNom().length(); i++) {
            if (nbSpace < 3) {
                if (this.destination.getNom().charAt(i) != ' ') {
                    v1 += this.destination.getNom().charAt(i);
                } else {
                    nbSpace++;
                }
            }
        }
        this.image = new ImageView("images/missions/eu-" + v1.toLowerCase() + ".png");

        image.setFitWidth(200);
        image.setFitHeight(120);
        this.setStyle("-fx-background-color: transparent");
        graphicProperty().setValue(image);

    }

    public IDestination getDestination() {
        return destination;
    }

    public void estDansJoueurCourant() {
        appartientAuJoueur = true;
        image.setFitHeight(50);
        image.setFitWidth(90);
    }
}
