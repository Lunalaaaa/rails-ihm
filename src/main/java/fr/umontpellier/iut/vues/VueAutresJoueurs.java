package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {


    private List<Joueur> listJoueur;
    private ObjectProperty<IJeu> refJeu;
    private VBox autreJoueurs;
    private ObjectProperty<IJoueur> refJoueurCourant;
    public static HashMap nomCouleur=new HashMap<>();

    public VueAutresJoueurs() {

        autreJoueurs=new VBox();
        autreJoueurs.setSpacing(10);
        this.getChildren().add(autreJoueurs);


    }
    public void definirCouleur(){
        nomCouleur.put("ROUGE","#ff3820");
        nomCouleur.put("JAUNE","#ffff32");
        nomCouleur.put("ROSE","pink");
        nomCouleur.put("BLEU","#19e0fc");
        nomCouleur.put("VERT","#84fc19");
    }

    public void creerBindings() {
        refJoueurCourant=((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty();
        listJoueur = ((VueDuJeu) getScene().getRoot()).getJeu().getJoueurs();
        refJoueurCourant.addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    autreJoueurs.getChildren().clear();
                    for (Joueur j : listJoueur) {
                        if(!j.equals(refJoueurCourant.getValue())){
                            ImageView imageView=new ImageView();
                            Label imageJoueur = new Label();
                            if (j.getCouleur().equals(IJoueur.Couleur.ROSE)) {
                                ImageView a = new ImageView("images/images/AVATAR-ROSE.png");
                                a.setFitHeight(100);
                                a.setFitWidth(80);
                                imageJoueur.setGraphic(a);
                            } else {
                                ImageView a = new ImageView("images/images/avatar-" + j.getCouleur().name() + ".png");
                                a.setFitHeight(100);
                                a.setFitWidth(80);
                                imageJoueur.setGraphic(a);
                            }

                            VBox placement=new VBox();
                            Pane couleurJoueur = new Pane();
                            HBox infoJoueur = new HBox();
                            HBox objetJoueur = new HBox();
                            HBox gareJoueur = new HBox();
                            HBox wagonJoueur = new HBox();
                            Label nomJoueur = new Label();

                            couleurJoueur.getChildren().clear();
                            Label score = new Label("Score: " + j.getScore());
                            Label imageGare = new Label();
                            Label gare = new Label(" "+j.getNbGares() + "");
                            Label imageWagon = new Label();
                            Label wagon = new Label(j.getNbWagons() + "");
                            nomJoueur.setText(j.getNom());
                            infoJoueur.getChildren().addAll(imageJoueur, nomJoueur);
                            ImageView i = new ImageView("images/gares/gare-" + j.getCouleur().name() + ".png");
                            i.setFitHeight(60);
                            i.setFitWidth(50);
                            imageGare.setGraphic(i);
                            gareJoueur.getChildren().addAll(imageGare, gare);
                            gareJoueur.setAlignment(Pos.CENTER_LEFT);
                            ImageView e = new ImageView("images/wagons/image-wagon-" + j.getCouleur().name() + ".png");
                            e.setFitWidth(80);
                            e.setFitHeight(90);
                            imageWagon.setGraphic(e);
                            definirCouleur();
                            couleurJoueur.styleProperty().setValue("-fx-background-color:" + nomCouleur.get(j.getCouleur().name()));
                            wagonJoueur.getChildren().addAll(imageWagon, wagon);
                            wagonJoueur.setAlignment(Pos.CENTER_LEFT);
                            objetJoueur.getChildren().addAll(gareJoueur, wagonJoueur);
                            placement.getChildren().addAll(infoJoueur, score, objetJoueur);
                            couleurJoueur.getChildren().addAll(placement);
                            autreJoueurs.getChildren().addAll(couleurJoueur);
                        }
                    }
                });
            }
        });





    }
}
