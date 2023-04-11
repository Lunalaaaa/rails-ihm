package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IRoute;
import fr.umontpellier.iut.IVille;
import fr.umontpellier.iut.rails.Joueur;
import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.Ville;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.awt.Color.BLACK;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {


    private String nomChoisi;
    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void choixRouteOuVille(MouseEvent event) {
        nomChoisi=((Node)event.getSource()).getId();
        ((VueDuJeu) getScene().getRoot()).getJeu().uneVilleOuUneRouteAEteChoisie(nomChoisi);
    }

    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    public static HashMap nomCouleur=new HashMap<>();

    public void definirCouleur() {
        nomCouleur.put("ROUGE", "#ff3820");
        nomCouleur.put("JAUNE", "#ffff32");
        nomCouleur.put("ROSE", "pink");
        nomCouleur.put("BLEU", "#19e0fc");
        nomCouleur.put("VERT", "#84fc19");

    }
    public void creerBindings() {
        definirCouleur();
        List<? super Ville> villeDuJeu=((VueDuJeu) getScene().getRoot()).getJeu().getVilles();
        List<? super Route> routeDuJeu=((VueDuJeu) getScene().getRoot()).getJeu().getRoutes();
        //bindRedimensionPlateau();
        for(Node nodeVille:villes.getChildren()){
            for(Object v:villeDuJeu){
                IVille ville1=(IVille) v;
                if (ville1.getNom().equals(nodeVille.getId())){
                    StringBinding bindingCouleurVille = new StringBinding() {
                        {
                            super.bind((ville1).proprietaireProperty());
                        }

                        @Override
                        protected String computeValue() {
                            String stringRemplissageCercle;
                            if(ville1.proprietaireProperty().getValue()!=null){
                                stringRemplissageCercle = "-fx-fill:"+nomCouleur.get(ville1.proprietaireProperty().getValue().getCouleur().name());
                            }
                            else{
                                stringRemplissageCercle=null;
                            }

                            return stringRemplissageCercle;
                        }
                    };
                    nodeVille.styleProperty().bind(bindingCouleurVille);
                }
            }
        }
        for(Node nodeRoute:routes.getChildren()){
            Group groupRoute=(Group) nodeRoute;
            for (Object route:routeDuJeu){
                IRoute route1=(IRoute) route;
                if (route1.getNom().equals(nodeRoute.getId())){
                    StringBinding bindingCouleurRoute=new StringBinding() {
                        {
                            super.bind(route1.proprietaireProperty());
                        }
                        @Override
                        protected String computeValue() {
                            String stringRemplissageRect;
                            if(route1.proprietaireProperty().getValue()!=null){
                                stringRemplissageRect="-fx-fill:"+nomCouleur.get(route1.proprietaireProperty().getValue().getCouleur().name());
                            }
                            else {
                                stringRemplissageRect=null;
                            }
                            return stringRemplissageRect;
                        }
                    };
                    for (Node rectRoute:groupRoute.getChildren()){
                        rectRoute.styleProperty().bind(bindingCouleurRoute);
                    }
                }
            }
        }
    }
            /*if(c.getId().equals(nomChoisi)){
                Ville ville=new Ville(c.getId());
                System.out.println(ville.getNom());
                ville.proprietaireProperty().addListener(new ChangeListener<Joueur>() {
                    @Override
                    public void changed(ObservableValue<? extends Joueur> observableValue, Joueur joueur, Joueur t1) {
                        Platform.runLater(() -> {
                            String couleur = t1.getCouleur().name();
                            ImageView image = new ImageView("images/gares/gare-" + couleur + ".png");
                            image.setLayoutX(c.getLayoutX());
                            image.setLayoutY(c.getLayoutY());
                        });
                    }
                });
            } */




    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();
//        Les dimensions de l'image varient avec celle de la scène
        image.fitWidthProperty().bind(getScene().widthProperty());
        image.fitHeightProperty().bind(getScene().heightProperty());
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {

        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });

        rect.widthProperty().bind((new DoubleBinding() {
            @Override
            protected double computeValue() {
                return DonneesPlateau.largeurRectangle;
            }
        }));
        rect.heightProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurRectangle;
            }
        });
        rect.xProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return DonneesPlateau.xInitial;
            }
        });
        rect.yProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return DonneesPlateau.yInitial;
            }
        });

//        Liste des propriétés à lier
//        rect.widthProperty()
//        rect.heightProperty()
//        rect.layoutXProperty()
//        rect.xProperty()
//        rect.layoutYProperty()
//        rect.yProperty()
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }

}
