/*
 * 
 * 
 * 
 */
package vue.popup;

import Serializable.HorsCombat.HorsCombat.TypeCombat;
import java.io.File;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import modele.Connexion;
import vue.ExceptionHandler;
import vue.proprietes.ConditionsInput;

/**
 * nouvelleMap.java
 *
 */
public class Dialogues {

	private final static Dialog dialogue = new Dialog();
	private final static Alert alert = new Alert(AlertType.NONE);
	private final static FileChooser fileChooser = new FileChooser();

	static {
		dialogue.getDialogPane().getStylesheets().add("vue/style.css");
		alert.getDialogPane().getStylesheets().add("vue/style.css");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("TimeFlies Map", "*.tfmap")
		);
	}

	public static List<File> getFiles() {
		fileChooser.setTitle("Charger un ou plusieurs fichiers");
		return fileChooser.showOpenMultipleDialog(dialogue.getOwner());
	}

	public static File saveFile(String nom) {
		fileChooser.setTitle(nom + " - Sauvegarder");
		return fileChooser.showSaveDialog(dialogue.getOwner());
	}

	public static void alert(String head, String text, AlertType type) {
		alert.getButtonTypes().clear();
		alert.setAlertType(type);
		alert.setTitle("Information");
		alert.setHeaderText(head);
		alert.getDialogPane().setContentText(text);
		if (alert.getButtonTypes().isEmpty()) {
			ButtonType bt1 = new ButtonType("Ok", ButtonData.OK_DONE);
			alert.getButtonTypes().addAll(bt1);
		}

		alert.showAndWait();
	}

	public static void mapChargee(String nom) {
		alert(nom + "\nCarte déjà chargée !",
				"La carte que vous tentez de charger se trouvé déjà dans la liste des cartes.",
				Alert.AlertType.WARNING);
	}

	public static void openFail(String path, Throwable e) {
		ExceptionHandler.handle(e, path, path + "\nChargement de fichier impossible !",
				"Le fichier n'existe pas, est peut-être endommagé, ou a été conçu avec une ancienne version de l'éditeur.");
	}

	public static ButtonData wantToClose(int size) {
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.getButtonTypes().clear();
		alert.setTitle("Fermeture de l'application");
		alert.setHeaderText(size + " cartes n'ont pas été sauvegardées.");
		alert.getDialogPane().setContentText("Les changements non sauvegardés seront perdus.\nVoulez-vous vraiment quitter ?");
		ButtonType bt1 = new ButtonType("Sauvegarder", ButtonData.OK_DONE);
		ButtonType bt2 = new ButtonType("Quitter", ButtonData.NO);
		ButtonType bt3 = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().addAll(bt1, bt2, bt3);

		alert.setResultConverter((p) -> p);
		Optional result = alert.showAndWait();

		return result.isPresent() ? ((ButtonType) result.get()).getButtonData() : ButtonData.CANCEL_CLOSE;
	}

	public static class NumberField extends TextField {

		public int getInt() {
			try {
				return Integer.parseInt(getText());
			} catch (NumberFormatException e) {
				return -1;
			}
		}

		@Override
		public void replaceText(int start, int end, String text) {
			if (validate(text)) {
				super.replaceText(start, end, text);
			}
		}

		@Override
		public void replaceSelection(String text) {
			if (validate(text)) {
				super.replaceSelection(text);
			}
		}

		private boolean validate(String text) {
			return ("".equals(text) || text.matches("[0-9]"));
		}
	}

	public static MapData nouvelleMap() {
		dialogue.getDialogPane().getButtonTypes().clear();
		String[] aides = {
			"Donnez un nom attrayant, qui résume votre carte en un ou deux mots.",
			"Soyez bref et explicite. Indiquez les particularités de votre carte.",
			"Attention à éviter les cartes trop petites ou trop grandes.",
			"Combien d'équipe (au maximum) pourra contenir la carte ?\nChaque équipe pourra contenir (au maximum) combien de personnages ? Dans le cas d'un 'solo' ce nombre représente le nombre total de personnages.",
			"Sur une échelle de 1 à 5, à combien estimez-vous la difficulté de la carte ?",
			"La connexion n'est pas obligatoire. Elle vous permet d'inclure votre pseudo à la carte, et de partager votre carte sur le site officiel."
		};

		dialogue.setTitle("Nouvelle carte");
		dialogue.setHeaderText("Création d'une nouvelle carte. \nLes propriétés de la carte pourront être modifiées à tout moment.");

		ButtonType createButtonType = new ButtonType("Créer", ButtonData.OK_DONE);
		dialogue.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(15);
		Insets inset = new Insets(20);
		grid.setPadding(inset);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.RIGHT);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);

		grid.getColumnConstraints().addAll(column1, column2);

		TextField nom = new TextField();
		nom.setPromptText("Nom de la carte");
		nom.textProperty().addListener(ConditionsInput.NOM.with(nom));

		TextArea description = new TextArea();
		description.setPromptText("Description de la carte");
		description.setMaxHeight(100);
		description.textProperty().addListener(ConditionsInput.DESCRIPTION.with(description));

		NumberField width = new NumberField(), height = new NumberField();
		width.setPromptText("0");
		width.textProperty().addListener(ConditionsInput.LARGEUR.with(width));
		height.setPromptText("0");
		height.textProperty().addListener(ConditionsInput.LONGUEUR.with(height));

		ComboBox typeCombat = new ComboBox(FXCollections.observableArrayList(TypeCombat.values()));
		typeCombat.setValue(TypeCombat.values()[0]);

		NumberField nbrEquipe = new NumberField();
		nbrEquipe.setPromptText("Nombre d'équipes");
		nbrEquipe.textProperty().addListener(ConditionsInput.NBR_EQUIPES.with(nbrEquipe));
		nbrEquipe.setDisable(true);

		typeCombat.setOnAction((e) -> {
			nbrEquipe.setDisable(typeCombat.getValue().equals(TypeCombat.SOLO));
		});

		NumberField persosParEquipe = new NumberField();
		persosParEquipe.setPromptText("3");
		persosParEquipe.textProperty().addListener(ConditionsInput.NBR_PERSOS.with(persosParEquipe));

		Slider difficulte = new Slider(1, 5, 3);
		difficulte.setShowTickLabels(true);
		difficulte.setShowTickMarks(true);
		difficulte.setSnapToTicks(true);
		difficulte.setMajorTickUnit(1);
		difficulte.setMinorTickCount(0);
		difficulte.setBlockIncrement(1);

		Separator separateur = new Separator();

		Label connexion = new Label("Connexion");
		connexion.setAlignment(Pos.CENTER);
		connexion.setMaxWidth(Double.MAX_VALUE);

		TextField login = new TextField();
		login.setPromptText("Pseudo");
		login.textProperty().addListener(ConditionsInput.LOGIN.with(login));

		PasswordField mdp = new PasswordField();
		mdp.setPromptText("Mot de passe");
		mdp.textProperty().addListener(ConditionsInput.MDP.with(mdp));

		Button logButton = new Button("Se connecter");
		logButton.setDisable(true);
		logButton.setOnAction((e) -> {
			Button b = (Button) e.getSource();
			b.setDisable(true);
			login.setDisable(true);
			mdp.setDisable(true);
			b.setText("Connexion ...");

			Connexion.connecter(login.getText(), mdp.getText());

			if (Connexion.isConnecte()) {
				b.setText("Connexion réussie !");
			} else {
				b.setDisable(false);
				login.setDisable(false);
				mdp.setDisable(false);
				b.setText("Réessayer");
			}

		});

		Label erreursLab = new Label();
		erreursLab.setStyle("-fx-text-fill: red");

		grid.addRow(0, new Label("Nom:"));
		grid.add(nom, 1, 0, 4, 1);
		grid.addRow(1, new Label("Description:"));
		grid.add(description, 1, 1, 4, 1);
		grid.addRow(2, new Label("Taille de la carte:"), new Label("Largeur:"), width, new Label("Longueur:"), height);
		grid.addRow(3, new Label("Type de combats:"), typeCombat, nbrEquipe, new Label("Personnages par équipe:"), persosParEquipe);
		grid.addRow(4, new Label("Difficulté estimée:"));
		grid.add(difficulte, 2, 4, 2, 1);
		grid.add(separateur, 0, 5, 5, 1);
		grid.add(connexion, 0, 6, 5, 1);
		grid.addRow(7, new Label("Pseudo:"), login, new Label("Mot de passe:"), mdp, logButton);

		Label lab;
		for (int i = 0; i < aides.length - 1; i++) {
			lab = new Label(aides[i]);
			lab.setMaxWidth(300);
			lab.setWrapText(true);
			grid.add(lab, 5, i);
		}
		lab = new Label(aides[5]);
		lab.setMaxWidth(Double.MAX_VALUE);
		lab.setAlignment(Pos.CENTER);
		grid.add(lab, 0, 8, 5, 1);

		erreursLab.setMaxWidth(Double.MAX_VALUE);
		erreursLab.setAlignment(Pos.CENTER);
		grid.add(erreursLab, 0, 9, 5, 1);

		Node createButton = dialogue.getDialogPane().lookupButton(createButtonType);
		createButton.setDisable(true);

		grid.getChildren().forEach((node) -> {
			if (node instanceof Labeled) {
				if (((Labeled) node).getMaxWidth() <= 0) {
					((Labeled) node).setMaxWidth(Double.MAX_VALUE);
					if (node instanceof Label) {
						((Label) node).setAlignment(Pos.CENTER_RIGHT);
					}
				}
			} else if (node instanceof TextInputControl) {
				TextInputControl input = (TextInputControl) node;
				if (input != login && input != mdp) {
					input.textProperty().addListener((observable, oldValue, newValue) -> {
						erreursLab.setText("");
						boolean disable = false;
						for (Node n : grid.getChildren()) {
							if (n instanceof TextInputControl && n != login && n != mdp
									&& !((String) n.getUserData()).trim().isEmpty()) {
								disable = true;
								if (!"0".equals((String) n.getUserData())) {
									if (erreursLab.getText().isEmpty()) {
										erreursLab.setText(erreursLab.getText() + (String) n.getUserData());
									} else {
										erreursLab.setText(erreursLab.getText() + " (...)");
										break;
									}
								}
							}
						}
						createButton.setDisable(disable);
					});
				} else {
					input.textProperty().addListener((observable, oldValue, newValue) -> {
						logButton.setDisable(!((String) login.getUserData()).isEmpty() || !((String) mdp.getUserData()).isEmpty());
					});
					((TextField) input).setOnAction((e) -> {
						logButton.fire();
					});
				}
			}
		});

		dialogue.getDialogPane().setContent(grid);

		Platform.runLater(() -> nom.requestFocus());

		dialogue.setResultConverter(dialogButton -> {
			if (dialogButton == createButtonType) {
				return new MapData(nom.getText(), description.getText(), Connexion.getID(), (TypeCombat) typeCombat.getValue(),
						nbrEquipe.getInt(), persosParEquipe.getInt(), (int) difficulte.getValue(), width.getInt(), height.getInt());
			}
			return null;
		});

		Optional result = dialogue.showAndWait();

		if (result.isPresent()) {
			return (MapData) result.get();
		}
		return null;
	}

	public static class MapData {

		public final String nom;
		public final String description;
		public final long idCreateur;
		public final TypeCombat typeCombat;
		public final int nbrEquipes;
		public final int joueursParEquipe;
		public final int difficulte;
		public final int width;
		public final int height;

		public MapData(String nom, String description, long idCreateur, TypeCombat typeCombat, int nbrEquipes, int joueursParEquipe, int difficulte,
				int width, int height) {
			this.nom = nom;
			this.description = description;
			this.idCreateur = idCreateur;
			this.typeCombat = typeCombat;
			this.nbrEquipes = nbrEquipes;
			this.joueursParEquipe = joueursParEquipe;
			this.difficulte = difficulte;
			this.width = width;
			this.height = height;
		}
	}

}
