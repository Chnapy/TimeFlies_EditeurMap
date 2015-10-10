/*
 * 
 * 
 * 
 */
package vue.proprietes;

import controleur.Controleur;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
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
	private final NumberField fNbrPersos;
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
		fNom.textProperty().addListener(ConditionsInput.NOM.with(fNom));
		gp.addRow(0, lNom);
		gp.addRow(1, fNom);

		Label lDescription = new Label("Description");
		fDescription = new TextArea();
		fDescription.setMaxHeight(40);
		fDescription.textProperty().addListener(ConditionsInput.DESCRIPTION.with(fDescription));
		gp.addRow(2, lDescription);
		gp.addRow(3, fDescription);

		Label lVersion = new Label("Version");
		fVersion = new TextField();
		fVersion.textProperty().addListener(ConditionsInput.VERSION.with(fVersion));
		gp.addRow(4, lVersion);
		gp.addRow(5, fVersion);

		Tooltip lTt = new Tooltip("La taille d'une map ne peut être changée.");
		Label lLargeur = new Label("Largeur");
		lLargeur.setTooltip(lTt);
		fLargeur = new NumberField();
		fLargeur.setDisable(true);
		gp.addRow(6, lLargeur);
		gp.addRow(7, fLargeur);

		Label lLongueur = new Label("Longueur");
		lLongueur.setTooltip(lTt);
		fLongueur = new NumberField();
		fLongueur.setDisable(true);
		gp.addRow(8, lLongueur);
		gp.addRow(9, fLongueur);

		Label lNbrEquipe = new Label("Nombre d'équipes max.");
		fNbrEquipe = new NumberField();
		fNbrEquipe.textProperty().addListener(ConditionsInput.NBR_EQUIPES.with(fNbrEquipe));
		gp.addRow(10, lNbrEquipe);
		gp.addRow(11, fNbrEquipe);

		Label lNbrPersos = new Label("Perso. max. par équipe");
		fNbrPersos = new NumberField();
		fNbrPersos.textProperty().addListener(ConditionsInput.NBR_PERSOS.with(fNbrPersos));
		gp.addRow(12, lNbrPersos);
		gp.addRow(13, fNbrPersos);

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
		bConfirmation.setTooltip(new Tooltip("N'oubliez pas de sauvegarder la carte !"));
		gp.addRow(19, bConfirmation);

		gp.getChildren().forEach((c) -> {
			((Control) c).setPrefWidth(getWidth() * 9 / 10);
			if (c instanceof TextInputControl && c != fLargeur && c != fLongueur) {
				((TextInputControl) c).textProperty().addListener((e, oldV, newV) -> {
					boolean disable = false;
					for (Node n : gp.getChildren()) {
						if (n instanceof TextInputControl && n != fLargeur && n != fLongueur && n.getUserData() != null
								&& !((String) n.getUserData()).trim().isEmpty() && !"0".equals(n.getUserData())) {
							disable = true;
							break;
						}
					}
					bConfirmation.setDisable(disable);
				});
			} else if (c instanceof Slider) {
				((Slider) c).valueChangingProperty().addListener((e) -> bConfirmation.setDisable(false));
			}
		});

		getScene().getRoot().setDisable(true);
	}

	public void setMap(Map map) {
		this.map = map;
		fNom.setText(map.nom);
		fDescription.setText(map.description);
		fVersion.setText(map.version);
		fLargeur.setText(map.tuiles[0].length + "");
		fLongueur.setText(map.tuiles.length + "");
		fNbrEquipe.setText(map.nbrEquipes + "");
		fNbrPersos.setText(map.joueursParEquipe + "");
		sDifficulte.setValue(map.difficulte);
		bConfirmation.setDisable(true);
		getScene().getRoot().setDisable(false);
	}

	private void enregistrer() {
		bConfirmation.setDisable(true);
		map.nom = fNom.getText();
		map.description = fDescription.getText();
		map.version = fVersion.getText();
		map.nbrEquipes = fNbrEquipe.getInt();
		map.joueursParEquipe = fNbrPersos.getInt();
		map.difficulte = (int) sDifficulte.getValue();
	}

}
