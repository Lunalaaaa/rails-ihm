package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.HashMap;


/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends AnchorPane {

    /*HashMap static pour les couleurs*/

    private Label nomJoueur;
    private Label gare;
    private Label garesJoueur;
    private Label wagonsJoueur;
    private Label wagons;
    private Label imageJoueur;
    private Pane fondCouleurJoueur;

    private Pane fondCouleurDestinationJoueur;
    private Label score;
    private HBox infosJoueur;
    private HBox gareJoueur;
    private HBox boxJou = new HBox();
    private VBox boxCarte = new VBox();
    private HBox wagonJoueur = new HBox();
    private GridPane joueurCourant;
    private HBox carteJoueur = new HBox();
    private TilePane destinationJoueur = new TilePane(Orientation.VERTICAL);
    private ObjectProperty<IJoueur> refJoueurCourant;

    public static HashMap nomCouleur = new HashMap<>();

    public void definirCouleur() {
        nomCouleur.put("ROUGE", "#ff3820");
        nomCouleur.put("JAUNE", "#ffff32");
        nomCouleur.put("ROSE", "pink");
        nomCouleur.put("BLEU", "#19e0fc");
        nomCouleur.put("VERT", "#84fc19");
    }

    public VueJoueurCourant() {
        nomJoueur = new Label();
        gare = new Label();
        garesJoueur = new Label();
        wagonsJoueur = new Label();
        wagons = new Label();
        score = new Label();
        imageJoueur = new Label();
        infosJoueur = new HBox();
        gareJoueur = new HBox();
        fondCouleurJoueur = new Pane();
        fondCouleurDestinationJoueur=new Pane();
        joueurCourant = new GridPane();
        destinationJoueur.setPrefRows(6);

        fondCouleurJoueur.setStyle("-fx-background-color:#9F9E9D");
        definirCouleur();

        nomJoueur.setStyle("-fx-font: normal bold 20px 'serif' ");
        //nomJoueur.setAlignment(Pos.CENTER_LEFT);

        infosJoueur.getChildren().addAll(imageJoueur, nomJoueur);

        wagonJoueur.getChildren().addAll(wagons, wagonsJoueur);

        gareJoueur.getChildren().addAll(gare, garesJoueur);

        fondCouleurDestinationJoueur.getChildren().add(destinationJoueur);
        joueurCourant.add(imageJoueur, 0, 0);
        joueurCourant.add(nomJoueur, 1, 0);
        joueurCourant.add(new Label("            "), 0, 1);
        joueurCourant.add(score, 0,2);
        joueurCourant.add(wagons, 0, 3);
        joueurCourant.add(wagonsJoueur, 1, 3);
        joueurCourant.add(gare, 0, 4);
        joueurCourant.add(garesJoueur, 1, 4);
        joueurCourant.add(new Label("            "),  2,0);


        fondCouleurJoueur.getChildren().add(joueurCourant);
        boxJou.getChildren().add(fondCouleurJoueur);
        boxCarte.getChildren().add(carteJoueur);
        carteJoueur.setSpacing(30);

        AnchorPane.setBottomAnchor(destinationJoueur,20.0);
        AnchorPane.setLeftAnchor(destinationJoueur,1720.0);

        AnchorPane.setLeftAnchor(boxJou,100.0);
        AnchorPane.setBottomAnchor(boxJou,0.1);

        AnchorPane.setBottomAnchor(boxCarte,0.1);
        AnchorPane.setLeftAnchor(boxCarte,425.0);

        this.getChildren().addAll(boxJou,boxCarte,destinationJoueur);
    }

    public void creerBinding() {
        refJoueurCourant = ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty();

        refJoueurCourant.addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    carteJoueur.getChildren().clear();
                    destinationJoueur.getChildren().clear();
                    nomJoueur.textProperty().setValue(t1.getNom());
                    if (t1.getCouleur().equals(IJoueur.Couleur.ROSE)) {
                        ImageView a = new ImageView("images/images/AVATAR-ROSE.png");
                        a.setFitHeight(100);
                        a.setFitWidth(80);
                        a.setPreserveRatio(true);
                        imageJoueur.setGraphic(a);
                    } else {
                        ImageView a = new ImageView("images/images/avatar-" + t1.getCouleur().name() + ".png");
                        a.setFitHeight(100);
                        a.setFitWidth(80);
                        a.setPreserveRatio(true);
                        imageJoueur.setGraphic(a);
                    }
                    ImageView i = new ImageView("images/gares/gare-" + t1.getCouleur().name() + ".png");
                    i.setFitHeight(60);
                    i.setFitWidth(50);
                    gare.setGraphic(i);
                    garesJoueur.textProperty().setValue(t1.getNbGares() + "");
                    ImageView e = new ImageView("images/wagons/image-wagon-" + t1.getCouleur().name() + ".png");
                    e.setFitWidth(80);
                    e.setFitHeight(90);
                    wagons.setGraphic(e);
                    wagonsJoueur.textProperty().setValue(t1.getNbWagons() + "");
                    score.textProperty().setValue("      Score : " + t1.getScore());
                    for (int n = 0; n < ICouleurWagon.values().length; n++) {
                        int nb = 0;
                        for (CouleurWagon cw : refJoueurCourant.getValue().getCartesWagon()) {
                            if (cw.equals(ICouleurWagon.values()[n])) {
                                nb++;
                            }
                        }
                        if (nb > 0) {
                            VueCarteWagon wagon = new VueCarteWagon(ICouleurWagon.values()[n]);
                            Label nbCarteWagon = new Label("x" + nb);
                            nbCarteWagon.setAlignment(Pos.BOTTOM_CENTER);
                            VBox Carte = new VBox();
                            Carte.setAlignment(Pos.TOP_CENTER);
                            Carte.getChildren().addAll(wagon, nbCarteWagon);
                            wagon.creerBinding();
                            wagon.estCarteJoueurCourant();
                            carteJoueur.getChildren().add(Carte);
                        }

                    }
                    fondCouleurJoueur.styleProperty().setValue("-fx-background-color:" + nomCouleur.get(t1.getCouleur().name()));
                    for (Destination d : refJoueurCourant.getValue().getDestinations()) {
                        VueDestination destination = new VueDestination(d);
                        destination.estDansJoueurCourant();
                        destinationJoueur.getChildren().add(destination);
                    }
                });
            }
        });
    }


}





