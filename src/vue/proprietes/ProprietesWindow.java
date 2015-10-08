/*
 * 
 * 
 * 
 */
package vue.proprietes;

import controleur.Controleur;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import modele.Map;
import vue.Module;
import vue.popup.Dialogues.NumberField;

/**
 * ProprietesWindow.java
 *
 */
public class ProprietesWindow extends Module {

	private final TextField fNom;
	private final TextArea fDescription;
	private final TextField fVersion;
	private final NumberField fLargeur;
	private final NumberField fLongueur;
	private final NumberField fNbrEquipe;
	private final NumberField fNbrJoueurs;
	private final Slider sDifficulte;
	private final Button bConnexion;
	private final Button bConfirmation;

	private Map map;

	public ProprietesWindow(Controleur controleur) {
		super(controleur, 200, 570, "Propriétés");
		map = null;
		GridPane gp = new GridPane();
		getScene().setRoot(gp);
		gp.setAlignment(Pos.TOP_CENTER);
		gp.setVgap(5);

		Label lNom = new Label("Nom");
		fNom = new TextField();
		gp.addRow(0, lNom);
		gp.addRow(1, fNom);

		Label lDescription = new Label("Description");
		fDescription = new TextArea();
		fDescription.setMaxHeight(40);
		gp.addRow(2, lDescription);
		gp.addRow(3, fDescription);

		Label lVersion = new Label("Version");
		fVersion = new TextField();
		gp.addRow(4, lVersion);
		gp.addRow(5, fVersion);

		Label lLargeur = new Label("Largeur");
		fLargeur = new NumberField();
		fLargeur.setDisable(true);
		gp.addRow(6, lLargeur);
		gp.addRow(7, fLargeur);

		Label lLongueur = new Label("Longueur");
		fLongueur = new NumberField();
		fLongueur.setDisable(true);
		gp.addRow(8, lLongueur);
		gp.addRow(9, fLongueur);

		Label lNbrEquipe = new Label("Nombre d'équipe max.");
		fNbrEquipe = new NumberField();
		gp.addRow(10, lNbrEquipe);
		gp.addRow(11, fNbrEquipe);

		Label lNbrJoueurs = new Label("Perso. max. par équipe");
		fNbrJoueurs = new NumberField();
		gp.addRow(12, lNbrJoueurs);
		gp.addRow(13, fNbrJoueurs);

		Label lDifficulté = new Label("Difficulté");
		sDifficulte = new Slider(1, 5, 3);
		sDifficulte.setShowTickLabels(true);
		sDifficulte.setShowTickMarks(true);
		sDifficulte.setSnapToTicks(true);
		sDifficulte.setMajorTickUnit(1);
		sDifficulte.setMinorTickCount(0);
		sDifficulte.setBlockIncrement(1);
		gp.addRow(14, lDifficulté);
		gp.addRow(15, sDifficulte);

		Label lConnexion = new Label("Connexion");
		bConnexion = new Button("Se connecter");
		gp.addRow(16, lConnexion);
		gp.addRow(17, bConnexion);

		gp.addRow(18, new Separator());

		bConfirmation = new Button("Appliquer changements");
		bConfirmation.setOnAction((e) -> enregistrer());
		bConfirmation.setDefaultButton(true);
		gp.addRow(19, bConfirmation);

		gp.getChildren().forEach((c) -> {
			((Control) c).setPrefWidth(getWidth() * 9 / 10);
			if (c instanceof TextInputControl) {
				((TextInputControl) c).textProperty().addListener((e) -> bConfirmation.setDisable(false));
			} else if (c instanceof Slider) {
				((Slider)c).valueChangingProperty().addListener((e) -> bConfirmation.setDisable(false));
			}
		});
	}

	public void setMap(Map map) {
		this.map = map;
		fNom.setText(map.nom);
		fDescription.setText(map.description);
		fVersion.setText(map.version);
		fLargeur.setText(map.tuiles[0].length + "");
		fLongueur.setText(map.tuiles.length + "");
		fNbrEquipe.setText(map.nbrEquipes + "");
		fNbrJoueurs.setText(map.joueursParEquipe + "");
		sDifficulte.setValue(map.difficulte);
		bConfirmation.setDisable(true);
	}

	private void enregistrer() {
		bConfirmation.setDisable(true);
		map.nom = fNom.getText();
		map.description = fDescription.getText();
		map.version = fVersion.getText();
		map.nbrEquipes = fNbrEquipe.getInt();
		map.joueursParEquipe = fNbrJoueurs.getInt();
		map.difficulte = (int) sDifficulte.getValue();
	}

}
