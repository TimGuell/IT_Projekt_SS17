package de.hdm.itProjektSS17.client.gui;

import java.util.Vector;

import org.cyberneko.html.HTMLScanner.CurrentEntity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektSS17.client.ClientsideSettings;
import de.hdm.itProjektSS17.client.Showcase;
import de.hdm.itProjektSS17.client.gui.report.AlleAusschreibungenShowcase;
import de.hdm.itProjektSS17.shared.ProjektmarktplatzVerwaltungAsync;
import de.hdm.itProjektSS17.shared.ReportGeneratorAsync;
import de.hdm.itProjektSS17.shared.bo.Ausschreibung;
import de.hdm.itProjektSS17.shared.bo.Organisationseinheit;
import de.hdm.itProjektSS17.shared.bo.Person;
import de.hdm.itProjektSS17.shared.bo.Team;
import de.hdm.itProjektSS17.shared.bo.Unternehmen;


public class Navigation extends StackPanel{
	
	private static ClickHandler currentClickHandler = null;
	private static ClickEvent currentClickEvent = null;
	
	//Anlegen der Panels
	VerticalPanel startseitePanel = new VerticalPanel();
	VerticalPanel projektlocatorPanel = new VerticalPanel();
	VerticalPanel meineaktivitaetenPanel = new VerticalPanel();
	VerticalPanel einstellungenPanel = new VerticalPanel();
	
	//Anlegen der Hyperlinks
	Hyperlink home = new Hyperlink();
	Anchor reportLink = new Anchor("ReportGenerator");
	
	//Anlegen der Buttons
	Button homeButton = new Button("Home");
	Button agbButton = new Button("AGB");
	Button impressumButton = new Button("Impressum");
	
	Button ausschreibungenButton = new Button("Stellenausschreibungen");
	Button projektmarktplaetzeButton = new Button("Projektmarktplätze");
	
	Button meineprojekteButton = new Button("Meine Projekte");
	Button meineausschreibungenButton = new Button("Meine Ausschreibungen");
	Button meinebewerbungenButton = new Button("Meine Bewerbungen");
	Button meineBeteiligungenButton = new Button("Meine Beteiligungen");
	
	Button personaldataButton = new Button("Persönliche Daten");
	Button eigenesprofilButton = new Button("Eigenes Partnerprofil");
	
	Button reportButton = new Button("Report Generator");
	IdentityMarketChoice identityMarketChoice = null;
	
	public Navigation(){
		
		//Zusammensetzen des startseitePanels
		startseitePanel.add(homeButton);
		homeButton.setWidth("200px");
		homeButton.setStylePrimaryName("menu-btn");
		startseitePanel.add(impressumButton);
		impressumButton.setWidth("200px");
		impressumButton.setStylePrimaryName("menu-btn");
		startseitePanel.add(agbButton);
		agbButton.setWidth("200px");
		agbButton.setStylePrimaryName("menu-btn");
		startseitePanel.setSpacing(5);
		
		//Zusammensetzen des projektlocatorPanels
		projektlocatorPanel.add(ausschreibungenButton);
		ausschreibungenButton.setWidth("200px");
		ausschreibungenButton.setStylePrimaryName("menu-btn");
		projektlocatorPanel.add(projektmarktplaetzeButton);
		projektmarktplaetzeButton.setWidth("200px");
		projektmarktplaetzeButton.setStylePrimaryName("menu-btn");
		projektlocatorPanel.setSpacing(5);
		
		//Zusammensetzung des meineaktivitaetenPanels
		meineaktivitaetenPanel.add(meineprojekteButton);
		meineprojekteButton.setWidth("200px");
		meineprojekteButton.setStylePrimaryName("menu-btn");
		meineaktivitaetenPanel.add(meineausschreibungenButton);
		meineausschreibungenButton.setWidth("200px");
		meineausschreibungenButton.setStylePrimaryName("menu-btn");
		meineaktivitaetenPanel.add(meinebewerbungenButton);
		meinebewerbungenButton.setWidth("200px");
		meinebewerbungenButton.setStylePrimaryName("menu-btn");
		meineaktivitaetenPanel.add(meineBeteiligungenButton);
		meineBeteiligungenButton.setWidth("200px");
		meineBeteiligungenButton.setStylePrimaryName("menu-btn");
		meineaktivitaetenPanel.setSpacing(5);
		
		//Zusammensezuung des einstellungenPanels
		einstellungenPanel.add(personaldataButton);
		personaldataButton.setWidth("200px");
		personaldataButton.setStylePrimaryName("menu-btn");
		einstellungenPanel.add(eigenesprofilButton);
		eigenesprofilButton.setWidth("200px");
		eigenesprofilButton.setStylePrimaryName("menu-btn");
		einstellungenPanel.add(reportButton);
		reportButton.setStylePrimaryName("menu-btn");
		reportButton.setWidth("200px");
		einstellungenPanel.setSpacing(5);
		
		this.setWidth("250px");
		this.addStyleName("gwt-StackPanel");
		this.add(startseitePanel, "Startseite");
		this.add(projektlocatorPanel, "Projekt Locator");
		this.add(meineaktivitaetenPanel, "Meine Aktivitäten");
		this.add(einstellungenPanel, "Einstellungen");
		
		
		//Clickhandler für den Impressum-Button
		impressumButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				identityMarketChoice.deactivateProjectMarkets();
				identityMarketChoice.deactivateOrgUnits();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(new Impressum());
			}
		});	
		
		agbButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				identityMarketChoice.deactivateProjectMarkets();
				identityMarketChoice.deactivateOrgUnits();
				Showcase showcase = new AGB();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
			}
		});	
		
		personaldataButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				currentClickHandler=this;
				currentClickEvent=event;
				identityMarketChoice.deactivateProjectMarkets();
				identityMarketChoice.activateOrgUnits();
				//Auslesen des Index, der in der ListBox der agierenden Organisationseinheit ausgewählt ist
				Organisationseinheit selectedIdentity = identityMarketChoice.getSelectedIdentityAsObject();
				//Window.alert(selectedIdentity.toString());
				//Falls der Index 0 ist, dann ist es eine Person und es wird die PersonProfilAnzeigenForm geladen
				if(selectedIdentity instanceof Person){
					Showcase showcase = new PersonProfilAnzeigenForm(identityMarketChoice, getNavigation());
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showcase);
					
				//Falls der Index 1 ist, dann ist ein Team aktiv und es wird die TeamProfilAnzeigenForm geladen.	
				}else if(selectedIdentity instanceof Team){
					Showcase showcase = new TeamProfilAnzeigenForm(identityMarketChoice, getNavigation());
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showcase);
					
				//Falls der Index 2 ist, dann ist ein Unternehmen aktiv und es wird die UnternehmenProfilAnzeigenForm geladen.
				}else if(selectedIdentity instanceof Unternehmen){
					Showcase showcase = new UnternehmenProfilAnzeigenForm(identityMarketChoice, getNavigation());
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showcase);
				}
			} 
		});
		
		eigenesprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				identityMarketChoice.deactivateProjectMarkets();
				identityMarketChoice.activateOrgUnits();
				Showcase showcase = new MeinPartnerprofilForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		meineprojekteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				identityMarketChoice.activateProjectMarkets();
				identityMarketChoice.setOwnOrgUnitToZero();
				identityMarketChoice.deactivateOrgUnits();
				Showcase showcase = new MeineProjektForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		meinebewerbungenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				identityMarketChoice.activateProjectMarkets();
				identityMarketChoice.activateOrgUnits();
				Showcase showcase = new MeineBewerbungenForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		ausschreibungenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				identityMarketChoice.activateProjectMarkets();
				identityMarketChoice.activateOrgUnits();
				Showcase showcase = new StellenauschreibungForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		projektmarktplaetzeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				identityMarketChoice.setOwnOrgUnitToZero();
				identityMarketChoice.deactivateProjectMarkets();
				identityMarketChoice.deactivateOrgUnits();
				Showcase showcase = new ProjektmarktplatzForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		homeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				identityMarketChoice.deactivateProjectMarkets();
				identityMarketChoice.deactivateOrgUnits();				
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(new StartseiteForm());
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		meineBeteiligungenButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				identityMarketChoice.activateProjectMarkets();
				identityMarketChoice.activateOrgUnits();
				Showcase showcase = new BeteiligungenForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
			}
		});
		
		meineausschreibungenButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				identityMarketChoice.setOwnOrgUnitToZero();
				identityMarketChoice.deactivateOrgUnits();
				identityMarketChoice.deactivateProjectMarkets();
				Showcase showcase = new MeineAusschreibungenForm(identityMarketChoice, getNavigation());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
				currentClickHandler=this;
				currentClickEvent=event;
				
			}
		});
		
		reportButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				reportLink.setHref(GWT.getHostPageBaseURL()+"ProjektmarktplatzReports.html");
				Window.open(reportLink.getHref(), "_self", "");
				
			}
		});

	}
	


	public ClickHandler getCurrentClickHandler() {
		return currentClickHandler;
	}

	public ClickEvent getCurrentClickEvent() {
		return currentClickEvent;
	}

	public void setCurrentClickHandler(ClickHandler c){
		currentClickHandler = c;
	}
	public void setCurrentClickEvent(ClickEvent e){
		currentClickEvent = e;
	}

	public void reload(){
		currentClickHandler.onClick(currentClickEvent);
	}
	
	public Navigation getNavigation(){
		return this;
	}
	public IdentityMarketChoice getIdentityMarketChoice(){
		return identityMarketChoice;
	}
	public void setIdentityMarketChoice(IdentityMarketChoice identityMarketChoice){
		this.identityMarketChoice=identityMarketChoice;
	}

	public Button getProjektmarktplaetzeButton() {
		return projektmarktplaetzeButton;
	}
	
	
	
}
