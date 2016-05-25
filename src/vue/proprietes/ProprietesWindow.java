/*
 * 
 * 
 * 
 */
package vue.proprietes;

import Serializable.HorsCombat.HorsCombat.TypeCombat;
import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private final NumberField fVersionMaj;
	private final NumberField fVersionMin;
	private final NumberField fLargeur;
	private final NumberField fLongueur;
	private final ComboBox fTypeCombat;
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

		Label lVersionMaj = new Label("Version majeure");
		fVersionMaj = new NumberField();
		fVersionMaj.textProperty().addListener(ConditionsInput.VERSIONMAJ.with(fVersionMaj));
		gp.addRow(4, lVersionMaj);
		gp.addRow(5, fVersionMaj);

		Label lVersionMin = new Label("Version mineure");
		fVersionMin = new NumberField();
		fVersionMin.textProperty().addListener(ConditionsInput.VERSIONMIN.with(fVersionMin));
		gp.addRow(6, lVersionMin);
		gp.addRow(7, fVersionMin);

		Tooltip lTt = new Tooltip("La taille d'une map ne peut être changée.");
		Label lLargeur = new Label("Largeur");
		lLargeur.setTooltip(lTt);
		fLargeur = new NumberField();
		fLargeur.setDisable(true);
		gp.addRow(8, lLargeur);
		gp.addRow(9, fLargeur);

		Label lLongueur = new Label("Longueur");
		lLongueur.setTooltip(lTt);
		fLongueur = new NumberField();
		fLongueur.setDisable(true);
		gp.addRow(10, lLongueur);
		gp.addRow(11, fLongueur);

		Label lTypeCombat = new Label("Type de combats");
		fTypeCombat = new ComboBox(FXCollections.observableArrayList(TypeCombat.values()));
//		for (TypeCombat type : TypeCombat.values()) {
//			fTypeCombat.getItems().add(type.name());
//		}
		gp.addRow(12, lTypeCombat);
		gp.addRow(13, fTypeCombat);

		Label lNbrEquipe = new Label("Nombre d'équipes max.");
		fNbrEquipe = new NumberField();
		fNbrEquipe.textProperty().addListener(ConditionsInput.NBR_EQUIPES.with(fNbrEquipe));
		gp.addRow(14, lNbrEquipe);
		gp.addRow(15, fNbrEquipe);

		Label lNbrPersos = new Label("Perso. max. par équipe");
		fNbrPersos = new NumberField();
		fNbrPersos.textProperty().addListener(ConditionsInput.NBR_PERSOS.with(fNbrPersos));
		gp.addRow(16, lNbrPersos);
		gp.addRow(17, fNbrPersos);

		Label lDifficulté = new Label("Difficulté");
		sDifficulte = new Slider(1, 5, 3);
		sDifficulte.setShowTickLabels(true);
		sDifficulte.setShowTickMarks(true);
		sDifficulte.setSnapToTicks(true);
		sDifficulte.setMajorTickUnit(1);
		sDifficulte.setMinorTickCount(0);
		sDifficulte.setBlockIncrement(1);
		gp.addRow(18, lDifficulté);
		gp.addRow(19, sDifficulte);

		Label lConnexion = new Label("Connexion");
		bConnexion = new Button("Se connecter");
		gp.addRow(20, lConnexion);
		gp.addRow(21, bConnexion);

		gp.addRow(22, new Separator());

		bConfirmation = new Button("Appliquer changements");
		bConfirmation.setOnAction((e) -> enregistrer());
		bConfirmation.setDefaultButton(true);
		bConfirmation.setTooltip(new Tooltip("N'oubliez pas de sauvegarder la carte !"));
		gp.addRow(23, bConfirmation);

//		gp.getChildren().forEach((c) -> {
//			((Control) c).setPrefWidth(getWidth() * 9 / 10);
//			if (c instanceof TextInputControl && c != fLargeur && c != fLongueur) {
//				((TextInputControl) c).textProperty().addListener((e, oldV, newV) -> {
//					boolean disable = false;
//					for (Node n : gp.getChildren()) {
//						if (n instanceof TextInputControl && n != fLargeur && n != fLongueur && n.getUserData() != null
//								&& !((String) n.getUserData()).trim().isEmpty() && !"0".equals(n.getUserData())) {
//							disable = true;
//							break;
//						}
//					}
//					bConfirmation.setDisable(disable);
//				});
//			} else if (c instanceof Slider) {
//				((Slider) c).valueChangingProperty().addListener((e) -> bConfirmation.setDisable(false));
//			}
//		});

		getScene().getRoot().setDisable(true);
	}

	public void setMap(Map map) {
		this.map = map;
		fNom.setText(map.nom);
		fDescription.setText(map.description);
		fVersionMaj.setText(map.versionMajeure + "");
		fVersionMin.setText(map.versionMineure + "");
		fLargeur.setText(map.tuiles[0].length + "");
		fLongueur.setText(map.tuiles.length + "");
		fTypeCombat.setValue(map.typeCombat);
		fNbrEquipe.setText(map.nbrEquipes + "");
		fNbrPersos.setText(map.joueursParEquipe + "");
		sDifficulte.setValue(map.difficulte);
//		bConfirmation.setDisable(true);
		getScene().getRoot().setDisable(false);
	}

	private void enregistrer() {
//		bConfirmation.setDisable(true);
		map.nom = fNom.getText();
		map.description = fDescription.getText();
		map.versionMajeure = fVersionMaj.getInt();
		map.versionMineure = fVersionMin.getInt();
		map.typeCombat = (TypeCombat) fTypeCombat.getValue();
		map.nbrEquipes = fNbrEquipe.getInt();
		map.joueursParEquipe = fNbrPersos.getInt();
		map.difficulte = (int) sDifficulte.getValue();
		controleur.action();
	}

	private TypeCombat getTypeFromName(String name) {
		for (TypeCombat type : TypeCombat.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return null;
	}

	@Override
	protected void key(String key) {
		controleur.key(key);
	}

}
