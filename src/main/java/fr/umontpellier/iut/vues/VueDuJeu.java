package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends AnchorPane {

    private static IJeu jeu;
    private VuePlateau plateau;
    private VueAutresJoueurs vueAutresJoueurs;

    private Button passer = new Button("Passer");
    private Label instruction;

    private static VBox destinationsInitiale = new VBox();
    private static VueJoueurCourant joueurCourant;

    private static VBox carteWagonVisible = new VBox();

    private Button wagonPioche = new Button();

    private Button destinationPioche = new Button();

    private static ListChangeListener<Destination> choixDestinationInitiale = new ListChangeListener<Destination>() {

        @Override
        public void onChanged(Change<? extends Destination> change) {
            Platform.runLater(() -> {
                change.next();
                if (change.wasAdded()) {

                    //Destination d = new Destination("Barcelona", "Bruxelles", 7);
                    //Label l = trouveLabelDestination(d);
                    //destinations.getChildren().add(l);

                    VueDestination destination = new VueDestination(change.getAddedSubList().get(0));

                    destination.setOnAction(actionEvent -> {
                        VueDuJeu.jeu.uneDestinationAEteChoisie(destination.getDestination().getNom());
                        destinationsInitiale.getChildren().remove(destination);
                    });
                    destinationsInitiale.getChildren().add(destination);
                }
                if (change.wasRemoved()) {
                    if (destinationsInitiale.getChildren().size() == 2) {
                        destinationsInitiale.getChildren().clear();
                    }
                }
            });
        }
    };

    private static ListChangeListener<CouleurWagon> carteWagonVisibleListener = new ListChangeListener<CouleurWagon>() {
        @Override
        public void onChanged(Change<? extends CouleurWagon> change) {
            Platform.runLater(() -> {
                change.next();
                if(change.wasAdded()){
                    VueCarteWagon carteWagon=new VueCarteWagon(change.getAddedSubList().get(0));
                    carteWagon.setOnAction(actionEvent -> {
                        jeu.uneCarteWagonAEteChoisie(carteWagon.getCouleurWagon());
                        carteWagonVisible.getChildren().remove(carteWagon);
                    });

                    carteWagonVisible.getChildren().add(carteWagon);
                }
                if (change.wasRemoved()){
                    VueCarteWagon carteWagon=new VueCarteWagon(change.getRemoved().get(0));
                    carteWagon.creerBinding();
                    carteWagonVisible.getChildren().removeAll(carteWagon);
                }
            });
        }
    };




/*
    public static Label trouveLabelDestination(IDestination d){

        Label l = new Label();

        ArrayList<Destination> liste = ;
        for (int i = 0; i < liste.size();i++) {
            if (d.getNom().equals(liste.get(i).getNom())) {
                l.setText(liste.get(i).getNom());
            }
        }
        return l;
   }

 */

    /*
    @FXML
    public void choixRouteOuVille(){
        String nomChoisi=getId();
        jeu.uneVilleOuUneRouteAEteChoisie(nomChoisi);
    }

     */

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        vueAutresJoueurs=new VueAutresJoueurs();
        joueurCourant = new VueJoueurCourant();
        instruction=new Label();
        Font font = Font.font("Courier New", FontWeight.BOLD, 20);

        passer.setFont(font);
        passer.setStyle("-fx-text-size: 20px;" +
                "-fx-background-color: RGB(172, 156, 156)");
        instruction.setFont(Font.font("Monospaced"));
        instruction.setStyle("-fx-font: normal bold 30px 'serif' ;");
        ImageView wagon=new ImageView("images/wagons.png");
        wagon.setFitWidth(170);
        wagon.setFitHeight(100);
        wagon.setPreserveRatio(true);
        ImageView dest=new ImageView("images/destinations.png");
        dest.setFitHeight(100);
        dest.setFitWidth(170);
        dest.setPreserveRatio(true);
        wagonPioche.graphicProperty().setValue(wagon);
        destinationPioche.graphicProperty().setValue(dest);
        wagonPioche.setStyle("-fx-background-color: transparent");
        destinationPioche.setStyle("-fx-background-color: transparent");
        joueurCourant.prefHeight(50);
        plateau.setMaxSize(200,300);
        destinationsInitiale.setAlignment(Pos.CENTER);
        carteWagonVisible.setAlignment(Pos.CENTER);
        HBox pioche=new HBox(wagonPioche,destinationPioche);
        VBox left=new VBox(destinationsInitiale,carteWagonVisible,pioche);


        AnchorPane.setTopAnchor(passer,0.1);
        AnchorPane.setTopAnchor(instruction,0.1);
        AnchorPane.setLeftAnchor(instruction,400.0);

        AnchorPane.setBottomAnchor(joueurCourant,1.0);

        AnchorPane.setBottomAnchor(plateau,200.0);
        AnchorPane.setTopAnchor(plateau,20.0);
        AnchorPane.setLeftAnchor(plateau,400.0);
        AnchorPane.setRightAnchor(plateau,100.0);

        AnchorPane.setRightAnchor(vueAutresJoueurs,1.0);

        AnchorPane.setLeftAnchor(left,0.1);
        AnchorPane.setTopAnchor(left,30.0);

        this.getChildren().addAll(passer,instruction,left,vueAutresJoueurs,joueurCourant,plateau);

    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {

        passer.setOnAction(actionEvent -> {
            destinationsInitiale.getChildren().clear();
            jeu.passerAEteChoisi();
        });
        wagonPioche.setOnAction(actionEvent -> {
            jeu.uneCarteWagonAEtePiochee();
        });
        destinationPioche.setOnAction(actionEvent -> {
            jeu.uneDestinationAEtePiochee();
        });
        jeu.destinationsInitialesProperty().addListener(choixDestinationInitiale);
        jeu.cartesWagonVisiblesProperty().addListener(carteWagonVisibleListener);
        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                instruction.textProperty().setValue(t1);
            }
        });
        joueurCourant.creerBinding();
        vueAutresJoueurs.creerBindings();
        plateau.creerBindings();

    }

}