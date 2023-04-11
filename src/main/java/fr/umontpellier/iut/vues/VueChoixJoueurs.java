package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.RailsIHM;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import javax.swing.text.Style;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 * <p>
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;
    private ArrayList<String> liste;
    private ComboBox<Integer> listeDeroulante;


    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();
        BorderPane b = new BorderPane();
        listeDeroulante = new ComboBox<>();
        listeDeroulante.getItems().setAll(2, 3, 4, 5);
        Label l = new Label("Les Aventuriers du rails");
        Button but = new Button("Valider");
        Button regles = new Button("?");
        regles.setStyle("-fx-background-radius: 5em; " +
                "-fx-background-color: RGB(255, 255, 255);"
                );
        but.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        HBox nbJoueurs = new HBox();
        nbJoueurs.getChildren().addAll(new Label("Nombre de joueurs : "), listeDeroulante);
        nbJoueurs.setAlignment(Pos.CENTER);
        box.getChildren().add(l);
        box.getChildren().add(nbJoueurs);
        box.getChildren().add(but);
        box.getChildren().add(regles);
        box.setAlignment(Pos.CENTER);
        b.setStyle("-fx-background-color: RGB(148, 157, 171)");
        b.setCenter(box);
        //b.getChildren().add(box);
        BorderPane p = new BorderPane();
        Button left = new Button("<");
        Button right = new Button(">");
        Button bu = new Button("Retour");
        p.setCenter(new ImageView("images/images/0.png"));
        p.setStyle("-fx-background-color: RGB(148, 157, 171)");
        left.setStyle("-fx-background-radius: 5em; " +
                "-fx-background-color: RGB(255, 255, 255);"
        );
        right.setStyle("-fx-background-radius: 5em; " +
                "-fx-background-color: RGB(255, 255, 255);"
        );
        p.setTop(bu);
        p.setLeft(left);
        p.setRight(right);
        this.setTitle("Les Aventuriers du Rails");
        this.setHeight(150);
        this.setWidth(200);
        Scene s = new Scene(b);
        this.setScene(s);
        but.setOnAction(actionEvent -> {
            if (listeDeroulante.getValue() != null) {
                VBox b1 = new VBox();
                b1.getChildren().add(new Label("Choisir les noms des joueurs."));
                Button button = new Button("Valider");
                liste = new ArrayList<>();
                liste.add("");
                for (int i = 0; i < listeDeroulante.getValue(); i++) {
                    int a = i + 1;
                    TextField t = new TextField();
                    liste.add(t.getText());
                    b1.getChildren().addAll(new Label("Nom du joueur n°" + a), t);
                    t.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                            boolean space = false;
                            for (int i = 0; i < t.getText().length(); i++){
                                if(t.getText().charAt(i) != ' '){
                                    space = true;
                                }
                            }
                            if(space){
                                liste.set(a, t.getText());
                            }
                            else{
                                liste.set(a, "");
                                t.setText("");
                            }
                        }
                    });
                }
                b1.getChildren().add(button);
                b1.setStyle("-fx-background-color: RGB(148, 157, 171)");
                Scene s1 = new Scene(b1);
                this.setHeight(70-10*listeDeroulante.getValue() + 60 * listeDeroulante.getValue());
                this.setScene(s1);
                button.setOnAction(actionEvent1 -> {
                    if(liste.size()-1 == listeDeroulante.getValue()){
                        setListeDesNomsDeJoueurs();
                        //setNomsDesJoueursDefinisListener(nomsJoueurs);
                    }
                });
            }
        });
        regles.setOnAction(actionEvent -> {
            p.setStyle("-fx-background-color: RGB(148, 157, 171)");
            s.setRoot(p);
            bu.setOnAction(actionEvent1 -> {
                s.setRoot(b);
                this.setHeight(150);
                this.setWidth(200);
            });
            p.setTop(bu);
            p.setLeft(left);
            p.setRight(right);
            this.setHeight(1000);
            this.setWidth(780);
            AtomicInteger a = new AtomicInteger();
            left.setOnAction(actionEvent1 -> {
                a.getAndDecrement();
                if (a.get() >= 0) {
                    ImageView i = new ImageView("images/images/" + a + ".png");
                    p.setCenter(i);
                } else {
                    a.getAndIncrement();
                }
            });
            right.setOnAction(actionEvent1 -> {
                a.getAndIncrement();
                if (a.get() < 8) {
                    ImageView i = new ImageView("images/images/" + a + ".png");
                    p.setCenter(i);
                } else {
                    a.getAndDecrement();
                }
            });
        });
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    /*
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange)
        
    }
    */

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs(); i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            } else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return listeDeroulante.getValue();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     *
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return liste.get(playerNumber);
    }

}
