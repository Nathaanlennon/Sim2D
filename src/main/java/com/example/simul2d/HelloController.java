// Lien entre fichier xml et le java, donc en gros faut mettre les trucs dedans

package com.example.simul2d;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label infoText;

    // On crée une variable pour stocker notre personnage
    // Elle est utilisable partout dans ce contrôleur
    private Personnage monPersonnage;

    private int clickCount = 0;

    @FXML
    private void initialize() {
        // Cette méthode est appelée automatiquement après le chargement du FXML.
        
        // On crée un nouveau personnage appelé "Héros"
        monPersonnage = new Personnage("Héros");
        
        // On affiche le nom du personnage avec un message de bienvenue
        welcomeText.setText("Bienvenue " + monPersonnage.getNom() + " !");
        
        // On affiche d'autres infos du personnage
        infoText.setText("Vie: " + monPersonnage.getVie() + " HP | Position: (" + 
                         monPersonnage.getX() + ", " + monPersonnage.getY() + ")");
    }

    @FXML
    protected void onHelloButtonClick() {
        // Chaque clic fait perdre 10 points de vie au personnage
        clickCount++;
        
        // Le personnage prend 10 dégâts
        monPersonnage.prenderDegats(10);
        
        // On met à jour l'interface avec les nouvelles infos
        welcomeText.setText(monPersonnage.getNom() + " a été touché " + clickCount + " fois !");
        infoText.setText("Vie restante: " + monPersonnage.getVie() + " HP | " + monPersonnage.toString());
    }
}
