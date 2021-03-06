package de.hdm.itProjektSS17.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.appengine.tools.admin.VerificationCodeReceiverRedirectUriException;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itProjektSS17.client.ClientsideSettings;
import de.hdm.itProjektSS17.client.Showcase;
import de.hdm.itProjektSS17.server.ProjektmarktplatzVerwaltungImpl;
import de.hdm.itProjektSS17.shared.ProjektmarktplatzVerwaltung;
import de.hdm.itProjektSS17.shared.ProjektmarktplatzVerwaltungAsync;
import de.hdm.itProjektSS17.shared.bo.Eigenschaft;
import de.hdm.itProjektSS17.shared.bo.Organisationseinheit;
import de.hdm.itProjektSS17.shared.bo.Partnerprofil;
import de.hdm.itProjektSS17.shared.bo.Person;
import de.hdm.itProjektSS17.shared.bo.Projekt;

public class MeinPartnerprofilForm extends Showcase{

	private IdentityMarketChoice identityMarketChoice=null;
	private Navigation navigation=null;
	
	/**
	 * Konstruktor, dem eine Instanz der IdentityMarketChoice und der Navigation übergeben wird.
	 * @param identityMarketChoice
	 * @param navigation
	 */
	public MeinPartnerprofilForm(IdentityMarketChoice identityMarketChoice, Navigation navigation) {
		this.identityMarketChoice=identityMarketChoice;
		this.navigation=navigation;
	}

	/**
	 * Auslesen der ProjektmarktplatzAsync Instanz
	 */
	ProjektmarktplatzVerwaltungAsync projektmarktplatzVerwaltung = ClientsideSettings.getProjektmarktplatzVerwaltung();
	
	private static int partnerprofilId = 0;
	
	
	//Elemente für die GUI
	CellTable<Eigenschaft> dataGrid = new CellTable<Eigenschaft>();
	Button loeschenButton = new Button("Löschen");
	Button eigenschaftHinzufuegenButton = new Button("Eigenschaft hinzufügen");
	HorizontalPanel buttonPanel = new HorizontalPanel();

	

	/**
	 * Setzen des Headline-Text	
	 */	
	protected String getHeadlineText() {
		return "Meine Eigenschaften";
	}

	/**
	 * Methode die aufgerufen wird, sobald diese Form aufgerufen wird.
	 */
	protected void run() {
		
		RootPanel.get("Details").setWidth("75%");
		dataGrid.setWidth("100%", true);
		
		//CallBack um die Eigenschaften der gewünschten Person zu laden
		projektmarktplatzVerwaltung.getOrganisationseinheitById(identityMarketChoice.getSelectedIdentityId(), new OrganisationseinheitCallback());
		
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		//Erstellen der TextColumns der CellTable
		TextColumn<Eigenschaft> nameColumn = new TextColumn<Eigenschaft>(){

			@Override
			public String getValue(Eigenschaft object) {
				return object.getName();
			}
		};
		
		TextColumn<Eigenschaft> wertColumn = new TextColumn<Eigenschaft>() {

			@Override
			public String getValue(Eigenschaft object) {
				return object.getWert();
			}
		};
		
		//Hinzufügen der TextColumns zur CellTable		
		dataGrid.addColumn(nameColumn, "Beschreibung");
		dataGrid.addColumn(wertColumn, "Wert");
		
		
		//
		final SingleSelectionModel<Eigenschaft> selectionModel = new SingleSelectionModel<>();
		dataGrid.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				
				
			}
		});
		
		//Anpassen der CellTable
		dataGrid.setWidth("100%");
		
		//Hinzufügen der Buttons zum ButtonPanel
		buttonPanel.add(eigenschaftHinzufuegenButton);
		buttonPanel.add(loeschenButton);
		
		//Stylen der Buttons
		eigenschaftHinzufuegenButton.setStylePrimaryName("cell-btn");
		loeschenButton.setStylePrimaryName("cell-btn");
	
		this.setSpacing(8);
		this.add(buttonPanel);
		this.add(dataGrid);
		
		
		/**
		 * Click-Handler um Eigenschaften zum Partnerprofil hinzufügen zu können.
		 * Hierzu wird eine neue DialogBox aufgerufen der die <code>partnerprofilId</code> übergeben wird
		 * @see de.hdm.itProjektSS17.client.gui.DialogBoxEigenschaftHinzuefuegen
		 */
		
		eigenschaftHinzufuegenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DialogBoxEigenschaftHinzufuegen gg = new DialogBoxEigenschaftHinzufuegen(partnerprofilId, false, navigation, identityMarketChoice);
				gg.center();
				gg.show();
			}
		});
		
		/**
		 * Click-Handler zum loeschen einer Eigenschaft.
		 * Hierzu wird die ausgewählte Eigenschaft übergeben.
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final Eigenschaft selectedEigenschaft = selectionModel.getSelectedObject();
				projektmarktplatzVerwaltung.deleteEigenschaft(selectedEigenschaft, new AsyncCallback<Void>() {
					
					public void onFailure(Throwable caught) {
						Window.alert("Fehler: Die Eigenschaft konnte nicht gelöscht werden.");
					}
					public void onSuccess(Void result) {
						Window.alert("Die Eigenschaft wurde erfolgreich gelöscht.");
						projektmarktplatzVerwaltung.getPartnerprofilById(selectedEigenschaft.getPartnerprofilId(), new AsyncCallback<Partnerprofil>() {
							
							/**
							 * Neuer CallBack der das <coder>Aenderungsdatum</code> neu setzt.
							 */
							@Override
							public void onSuccess(Partnerprofil result) {
								
							result.setAenderungdatum(new Date());
							navigation.reload();
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Fehler: Die Eigenschaft konnte nicht gelöscht werden.");
								
							}
						});
						
					}
				});
				
			}
		});
		
		

	}
	
	
	/**
	 * ASYNC-CALLBACK der einen Vector mit Eigenschaft-Objekten als <code>result</code> zurückliefert.
	 */
	public class GetEigenschaftenCallback implements AsyncCallback<Vector<Eigenschaft>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: Die Eigenschaften konnten nicht ausgelesen werden" );
		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			final ListDataProvider dataProvider = new ListDataProvider();
			SimplePager pager;
			SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
			pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			pager.setDisplay(dataGrid);
			dataProvider.addDataDisplay(dataGrid);
			dataProvider.setList(new ArrayList<Eigenschaft>(result));
			pager.setPageSize(10);
			
			HorizontalPanel hp_pager = new HorizontalPanel();
			hp_pager.setWidth("100%");
			hp_pager.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			hp_pager.add(pager);
			add(hp_pager);
			dataGrid.setRowCount(result.size(), true);
			dataGrid.setRowData(0, result);
		}	
	}
	
	
	/**
	 * Callback der ein Partnerprofil zurückgibt.
	 * Bei erfolreichem Callback wird dieses Partnerprofil übergeben um die dazugehörigen Eigenschaften zu erhalten.
	 * @author Tim
	 *
	 */
	public class GetPartnerProfilCallback implements AsyncCallback<Partnerprofil>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: Das Partnerprofil konnte nicht ausgelesen werden");
		}

		@Override
		public void onSuccess(Partnerprofil result) {
			partnerprofilId = result.getId();
			projektmarktplatzVerwaltung.getEigenschaftByForeignPartnerprofil(result, new GetEigenschaftenCallback());
		}
	}
	
	
	/**
	 * Callbacl der eine Organisationseinheit-Objekt als <code>result</code> zurückgibt.
	 * Bei erfolgreichem Callback wird das <code>result</code> übergeben, um das
	 * dazugehörige Partnerprofil zu erhalten.
	 * @author Tim
	 *
	 */
	private class OrganisationseinheitCallback implements AsyncCallback<Organisationseinheit> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Das Anzeigen der Person ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Organisationseinheit result) {		
			if (result != null) {
				projektmarktplatzVerwaltung.getPartnerprofilByForeignOrganisationseinheit(result, new GetPartnerProfilCallback());
			}			
		}
	
	}
	
}
