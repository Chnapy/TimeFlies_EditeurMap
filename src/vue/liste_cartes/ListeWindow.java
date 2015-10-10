/*
 * 
 * 
 * 
 */
package vue.liste_cartes;

import controleur.Controleur;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import modele.Map;
import vue.Module;
import vue.popup.Dialogues;

/**
 * ListeWindow.java
 *
 */
public class ListeWindow extends Module {

	private final ListView<MapString> listView;

	public ListeWindow(Controleur controleur) {
		super(controleur, 200, 500, "Liste des cartes");

		listView = new ListView();
		getScene().setRoot(listView);

		listView.setCellFactory(new Callback<ListView<MapString>, ListCell<MapString>>() {

			@Override
			public ListCell<MapString> call(ListView<MapString> p) {

				ListCell<MapString> cell = new ListCell<MapString>() {

					@Override
					public void updateSelected(boolean bln) {
						if (getItem() != null && getItem().select) {
							super.updateSelected(bln);
						}
					}

					@Override
					protected void updateItem(MapString ms, boolean bln) {
						super.updateItem(ms, bln);
						if (ms != null) {
							setText(ms.toString());
							MenuItem ret = new MenuItem("Retirer de la liste");
							ret.setOnAction((ActionEvent e) -> remove(ms));
							setContextMenu(new ContextMenu(ret));
							setOnMouseClicked((e) -> {
								if (e.getButton().equals(MouseButton.PRIMARY)) {
									controleur.setMap(ms.map);
								}
							});
							setTooltip(new Tooltip(ms.path));
						}
					}

				};
				return cell;
			}
		});
		getScene().getRoot().setDisable(true);
	}

	public void nouvelleMap(Map map) {
		MapString m = addMap(map);
		getScene().getRoot().setDisable(false);
		setSelected(m);
	}

	public MapString addMap(Map map) {
		MapString m = getMapString(map);
		if (m == null) {
			m = new MapString(map, listView.getItems().size(), null);
			listView.getItems().add(m);
		}
		return m;
	}

	public void modif(Map map) {
		MapString m = getMapString(map);
		if (!m.modif) {
			m.modif = true;
			listView.getItems().set(m.index, m);
			listView.getSelectionModel().select(m.index);
		}
	}

	public void save(Map map) {
		MapString m = getMapString(map);
		if (m.modif) {
			m.modif = false;
			listView.getItems().set(m.index, m);
		}
	}

	private MapString getMapString(Map map) {
		for (MapString m : listView.getItems()) {
			if (m.map.equals(map)) {
				return m;
			}
		}
		return null;
	}

	public String getPath(Map map) {
		return getMapString(map).path;
	}

	public void setPath(Map map, String path) {
		getMapString(map).path = path;
	}

	public boolean isset(Map map) {
		return !listView.getItems().isEmpty() && getMapString(map) != null;
	}

	public void remove(MapString ms) {
		Dialogues.alert("Coming soon tavu", "Flemme de faire ca donc voila chut", Alert.AlertType.INFORMATION);
	}

	private void setSelected(MapString ms) {
		listView.getItems().stream().forEach((m) -> {
			if (m.select) {
				m.select = false;
				listView.getItems().set(m.index, m);
			}
		});
		ms.select = true;
		listView.getItems().set(ms.index, ms);
		listView.getSelectionModel().select(ms.index);
	}

	public List<Map> notSaved() {
		List<Map> liste = new ArrayList();
		listView.getItems().stream().filter((m) -> (m.modif || !new File(m.path).exists())).forEach((m) -> {
			liste.add(m.map);
		});
		return liste;
	}

	public class MapString {

		public final Map map;
		public int index;
		public boolean modif;
		public boolean select;
		public String path;

		public MapString(Map map, int index, String path) {
			this.map = map;
			this.index = index;
			this.path = path;
			modif = false;
		}

		@Override
		public String toString() {
			return (select ? "> " : "") + (modif ? "*" : "") + map.nom + "  [" + map.version + "]";
		}
	}

}
