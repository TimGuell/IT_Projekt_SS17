package de.hdm.itProjektSS17.client.gui.report;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itProjektSS17.client.ClientsideSettings;
import de.hdm.itProjektSS17.client.Showcase;
import de.hdm.itProjektSS17.client.gui.IdentityMarketChoice;
import de.hdm.itProjektSS17.shared.ProjektmarktplatzVerwaltungAsync;
import de.hdm.itProjektSS17.shared.ReportGeneratorAsync;
import de.hdm.itProjektSS17.shared.bo.Ausschreibung;
import de.hdm.itProjektSS17.shared.bo.Bewerbung;
import de.hdm.itProjektSS17.shared.bo.Organisationseinheit;
import de.hdm.itProjektSS17.shared.report.AlleBewerbungenAufEigeneAusschreibungenReport;
import de.hdm.itProjektSS17.shared.report.HTMLReportWriter;

/**
 * Klasse die einen Report mit allen Bewerbungen auf eigene (User) Ausschreibungen zurückgibt.
 * @author Tim
 *
 */
public class AlleBewerungenAufEigeneAusschreibungenShowcase extends Showcase{

	private IdentityChoiceReport identityChoiceReport = null;
	
	/**
	 * Konstruktor, dem eine Instanz der IdentityChoiceReport und der Navigation übergeben wird.
	 * @param identityChoiceReport
	 */
	public AlleBewerungenAufEigeneAusschreibungenShowcase(IdentityChoiceReport identityChoiceReport) {
		this.identityChoiceReport=identityChoiceReport;
	}
	
	/**
	 * Setzen des Headline Textes
	 */
	@Override
	protected String getHeadlineText() {
		return "Report für alle Bewerbungen auf eigene Ausschreibungen";
	}

	/**
	 * Methode die startet, sobald diese Klasse aufgerufen wird
	 */
	@Override
	protected void run() {
		
		final Showcase showcase = this;
		
		/**
		 * Auslesen der ReportGeneratorAsync Instanz
		 */		
		ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
		
		
		/**
		 * Bei erfolgreichem Callback werden alle Bewerbungen die auf eigene Ausschreibungen eingingen als
		 * Report ausgegeben.
		 */
		reportGenerator.createAlleBewerbungenAufEigeneAusschreibungenReport(identityChoiceReport.getSelectedIdentityAsObject(),
				new AsyncCallback<AlleBewerbungenAufEigeneAusschreibungenReport>() {

					@Override
					public void onFailure(Throwable caught) {
						showcase.append("Fehler: "+ caught.getMessage());
						
					}

					@Override
					public void onSuccess(AlleBewerbungenAufEigeneAusschreibungenReport result) {
						if(result!= null){
							
							HTMLReportWriter writer = new HTMLReportWriter();
						
							writer.process(result);
							
							showcase.append(writer.getReportText());
							}
						
					}
				});
		
	}

}
