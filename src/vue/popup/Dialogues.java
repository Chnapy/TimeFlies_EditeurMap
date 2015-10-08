/*
 * 
 * 
 * 
 */
package vue.popup;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
import modele.Connexion;

/**
 * nouvelleMap.java
 *
 */
public class Dialogues {

	private static final int NOM_MAXWIDTH = 32;
	private static final int DESCRIPTION_MAXWIDTH = 256;
	private static final int TAILLE_MAX = 32;
	private static final int NBR_EQUIPE_MAX = 8;
	private static final int NBR_PERSOS_MAX = 64;

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

	private final static Dialog dialogue = new Dialog();

	public static MapData nouvelleMap() {
		dialogue.getDialogPane().getButtonTypes().clear();
		String[] aides = {
			"Donnez un nom attrayant, qui résume votre carte en un ou deux mots.",
			"Soyez bref et explicite. Indiquez les particularités de votre carte.",
			"Attention à éviter les cartes trop petites ou trop grandes.",
			"Combien d'équipe (au maximum) pourra contenir la carte ? Indiquez 0 pour un 'chacun pour soi'.\nChaque équipe pourra contenir (au maximum) combien de personnages ? Dans le cas d'un 'chacun pour soi' ce nombre représente le nombre total de personnages.",
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

		TextArea description = new TextArea();
		description.setPromptText("Description de la carte");
		description.setMaxHeight(100);

		NumberField width = new NumberField(), height = new NumberField();
		width.setPromptText("0");
		height.setPromptText("0");

		NumberField nbrEquipe = new NumberField();
		nbrEquipe.setPromptText("2");

		NumberField persosParEquipe = new NumberField();
		persosParEquipe.setPromptText("3");

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

		PasswordField mdp = new PasswordField();
		mdp.setPromptText("Mot de passe");

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

		login.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				logButton.setDisable(true);
			} else if (!mdp.getText().isEmpty()) {
				logButton.setDisable(false);
			}
		});
		mdp.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				logButton.setDisable(true);
			} else if (!login.getText().isEmpty()) {
				logButton.setDisable(false);
			}
		});
		login.setOnAction((e) -> {
			logButton.fire();
		});
		mdp.setOnAction((e) -> {
			logButton.fire();
		});

		Label erreursLab = new Label();
		erreursLab.setStyle("-fx-text-fill: red");

		grid.addRow(0, new Label("Nom:"));
		grid.add(nom, 1, 0, 4, 1);
		grid.addRow(1, new Label("Description:"));
		grid.add(description, 1, 1, 4, 1);
		grid.addRow(2, new Label("Taille de la carte:"), new Label("Largeur:"), width, new Label("Longueur:"), height);
		grid.addRow(3, new Label("Équipes:"), new Label("Nombre d'équipes:"), nbrEquipe, new Label("Personnages par équipe:"), persosParEquipe);
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
			} else if (node instanceof TextInputControl && node != login && node != mdp) {
				node.setUserData("0");
				((TextInputControl) node).textProperty().addListener((observable, oldValue, newValue) -> {
					erreursLab.setText("");
					if (node == nom) {
						if (!newValue.isEmpty() && newValue.length() <= NOM_MAXWIDTH) {
							node.setUserData("");
						} else {
							node.setUserData("Le champ nom doit être rempli avec au max " + NOM_MAXWIDTH + " caractères.");
						}
					} else if (node == description) {
						if (!newValue.isEmpty() && newValue.length() <= DESCRIPTION_MAXWIDTH) {
							node.setUserData("");
						} else {
							node.setUserData("Le champ description doit être rempli avec au max " + DESCRIPTION_MAXWIDTH + " caractères.");
						}
					} else if (node == width || node == height) {
						if (!newValue.isEmpty() && Integer.parseInt(newValue) <= TAILLE_MAX) {
							node.setUserData("");
						} else {
							node.setUserData("Le champ " + ((node == width) ? "largeur" : "longueur") + " doit être inférieur ou égal à " + TAILLE_MAX + ".");
						}
					} else if (node == nbrEquipe) {
						if (!newValue.isEmpty() && Integer.parseInt(newValue) <= NBR_EQUIPE_MAX && Integer.parseInt(newValue) != 1) {
							node.setUserData("");
						} else {
							node.setUserData("Le champ nombre d'équipes doit être inférieur ou égal à " + NBR_EQUIPE_MAX + " et différent de 1.");
						}
					} else if (node == persosParEquipe) {
						if (!newValue.isEmpty() && Integer.parseInt(newValue) <= NBR_PERSOS_MAX) {
							node.setUserData("");
						} else {
							node.setUserData("Le champ personnages par équipe doit être inférieur ou égal à " + NBR_PERSOS_MAX + ".");
						}
					}
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
			}
		});

		dialogue.getDialogPane().setContent(grid);

		Platform.runLater(() -> nom.requestFocus());

		dialogue.setResultConverter(dialogButton -> {
			if (dialogButton == createButtonType) {
				return new MapData(nom.getText(), description.getText(), Connexion.getLogin(), nbrEquipe.getInt(), persosParEquipe.getInt(), (int) difficulte.getValue(), width.getInt(), height.getInt());
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
		public final String auteur;
		public final int nbrEquipes;
		public final int joueursParEquipe;
		public final int difficulte;
		public final int width;
		public final int height;

		public MapData(String nom, String description, String auteur, int nbrEquipes, int joueursParEquipe, int difficulte,
				int width, int height) {
			this.nom = nom;
			this.description = description;
			this.auteur = auteur;
			this.nbrEquipes = nbrEquipes;
			this.joueursParEquipe = joueursParEquipe;
			this.difficulte = difficulte;
			this.width = width;
			this.height = height;
		}
	}

}
