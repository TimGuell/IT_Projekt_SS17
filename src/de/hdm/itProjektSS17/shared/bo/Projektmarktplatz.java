package de.hdm.itProjektSS17.shared.bo;
/**
 * Realisierung eines Projektmarktplazes. Ein Projektmarktplatz besitzt eine Bezeichnung
 * 
 * @author Tom Alender
 * @version 1.0
 */
public class Projektmarktplatz extends BusinessObject {

	private static final long serialVersionUID = 1L;
	/**
	   * Bezeichnung des Projektmarktplatzes
	   */
	private String bezeichnung = "";
	/**
	   * Auslesen der Bezeichnung
	   */
	public String getBezeichnung() {
		return bezeichnung;
	}
	/**
	   * Setzen der Bezeichnung
	   */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
	
}