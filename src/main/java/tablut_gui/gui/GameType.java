package tablut_gui.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum GameType {
	TABLUT("Tablut", 390, 435),
	CLASSIC_TABLUT("ClassicTablut", 415, 415),
	MODERN_TABLUT("ModernTablut", 415, 415),
	BRANDUB("Brandub", 415, 415);

	private String name;
	private int width;
	private int height;

}
