package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Wagon.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Button {

    private ICouleurWagon couleurWagon;
    private ImageView imageView;
    private boolean CarteJoueurCourant = false;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
        imageView = new ImageView("images/cartesWagons/carte-wagon-" + couleurWagon.toString().toUpperCase() + ".png");

        imageView.setFitHeight(110);
        imageView.setFitWidth(180);
        this.setStyle("-fx-background-color: transparent");
        graphicProperty().setValue(imageView);
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

    public boolean isCarteJoueurCourant() {
        return CarteJoueurCourant;
    }

    public void estCarteJoueurCourant() {
        CarteJoueurCourant = true;
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

    }

    public void creerBinding(){
        this.setOnAction(actionEvent -> {
            ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie(couleurWagon);
        });
    }



}
