/*
 * 
 * 
 * 
 */
package vue.liste_cartes;

import controleur.Controleur;
import java.util.HashSet;
import javafx.scene.control.ListView;
import modele.Map;
import vue.Module;

/**
 * ListeWindow.java
 *
 */
public class ListeWindow extends Module {

	private final ListView<String> list;
	private final HashSet<MapString> listMap;

	public ListeWindow(Controleur controleur) {
		super(controleur, 200, 500, "Liste des cartes");

		list = new ListView();
		listMap = new HashSet();
		getScene().setRoot(list);
	}

	public void nouvelleMap(Map map) {
		listMap.add(new MapString(map, list.getItems().size()));
		list.getItems().add(map.nom + " [" + map.version + "]");
		list.getSelectionModel().select(map.nom);
	}

	public void modif(Map map) {
		listMap.forEach((m) -> {
			if (m.map.equals(map) && !m.modif) {
				m.modif = true;
				list.getItems().set(m.index, "*" + map.nom + " [" + map.version + "]");
			}
		});
	}

	public void save(Map map) {
		listMap.forEach((m) -> {
			if (m.map.equals(map) && m.modif) {
				m.modif = false;
				list.getItems().set(m.index, map.nom + " [" + map.version + "]");
			}
		});

	}

	class MapString {

		public final Map map;
		public int index;
		public boolean modif;

		public MapString(Map map, int index) {
			this.map = map;
			this.index = index;
		}
	}

}
