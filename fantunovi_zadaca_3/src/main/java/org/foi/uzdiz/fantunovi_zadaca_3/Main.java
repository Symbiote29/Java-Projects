package org.foi.uzdiz.fantunovi_zadaca_3;

import java.util.Scanner;

import org.foi.uzdiz.builderstanica.StanicaBuilder;
import org.foi.uzdiz.builderstanica.StanicaDirector;
import org.foi.uzdiz.chainofresponsibility.CVPKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.ContextKomandi;
import org.foi.uzdiz.chainofresponsibility.DKKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.DPKKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.HelpKomanda;
import org.foi.uzdiz.chainofresponsibility.IEVDKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IEVKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IKKPVKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IKKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IPKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IRPSKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.ISI2SKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.ISPKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IVI2SKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IVKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.IVRVKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.KKPV2SKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.PKKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.PSP2SKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.ProcesorKomandi;
import org.foi.uzdiz.chainofresponsibility.STATKomandaHandler;
import org.foi.uzdiz.chainofresponsibility.SVVKomandaHandler;
import org.foi.uzdiz.commandVracanjeKarte.VracanjeKupljeneKarte;
import org.foi.uzdiz.compositevoznired.Raspored;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZkDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZodDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZpsDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZvrDatoteka;
import org.foi.uzdiz.managers.PrugaManager;
import org.foi.uzdiz.mediatorvoznired.ConcreteVozniRedMediator;
import org.foi.uzdiz.observerkorisnik.RegistarKorisnika;
import org.foi.uzdiz.singleton.TvrtkaZeljeznickogPrometa;
import org.foi.uzdiz.singleton.UpravljacGresaka;

public class Main {
	public static void main(String[] args) {
		TvrtkaZeljeznickogPrometa tvrtka = TvrtkaZeljeznickogPrometa.getInstanca();
		UpravljacGresaka upravljacGresaka = UpravljacGresaka.getInstanca();
		
        UcitavanjeDatoteka ucitavanje = new UcitavanjeDatoteka(upravljacGresaka);
        ucitavanje.obradiArgumente(args);
       
        StanicaBuilder stanicaBuilder = new StanicaBuilder();
        StanicaDirector stanicaDirector = new StanicaDirector(stanicaBuilder);
        ZkDatoteka zkDatoteka = ucitavanje.getZkDatoteka();
        ZpsDatoteka zpsDatoteka = ucitavanje.getZpsDatoteka();
        PrugaManager prugaManager = new PrugaManager(stanicaDirector, ucitavanje.getSvePruge(), ucitavanje.getZsDatoteka());
        ZvrDatoteka zvrDatoteka = ucitavanje.getZvrDatoteka();
        ZodDatoteka zodDatoteka = ucitavanje.getZodDatoteka();
        Raspored raspored = new Raspored();
        RegistarKorisnika registar = new RegistarKorisnika();
        ConcreteVozniRedMediator mediator = new ConcreteVozniRedMediator();
        
        zvrDatoteka.setPrugaManager(prugaManager);
        zvrDatoteka.setZsDatoteka(prugaManager.getZsDatoteka());
        zvrDatoteka.setRaspored(raspored);
        zvrDatoteka.setZodDatoteka(zodDatoteka);
        zvrDatoteka.setMediator(mediator);
        raspored.setZsDatoteka(prugaManager.getZsDatoteka());
        
        ContextKomandi context = new ContextKomandi(ucitavanje, prugaManager, zkDatoteka, zpsDatoteka, zvrDatoteka, raspored, registar);
        
        ProcesorKomandi procesor = new ProcesorKomandi(context);
        procesor.dodajHandlera(new HelpKomanda());
        procesor.dodajHandlera(new IPKomandaHandler());
        procesor.dodajHandlera(new ISPKomandaHandler());
        procesor.dodajHandlera(new ISI2SKomandaHandler());
        procesor.dodajHandlera(new IKKomandaHandler());
        procesor.dodajHandlera(new IVKomandaHandler());
        procesor.dodajHandlera(new IEVKomandaHandler());
        procesor.dodajHandlera(new IEVDKomandaHandler());
        procesor.dodajHandlera(new IVRVKomandaHandler());
        procesor.dodajHandlera(new IVI2SKomandaHandler());
        procesor.dodajHandlera(new DKKomandaHandler());
        procesor.dodajHandlera(new PKKomandaHandler());
        procesor.dodajHandlera(new DPKKomandaHandler());
        procesor.dodajHandlera(new SVVKomandaHandler());
        procesor.dodajHandlera(new STATKomandaHandler(mediator));
        procesor.dodajHandlera(new CVPKomandaHandler());
        procesor.dodajHandlera(new KKPV2SKomandaHandler());
        procesor.dodajHandlera(new IKKPVKomandaHandler());
        procesor.dodajHandlera(new PSP2SKomandaHandler());
        procesor.dodajHandlera(new IRPSKomandaHandler());
        procesor.dodajHandlera(new VracanjeKupljeneKarte(context.getPovijestKupovina()));
        
        Scanner scanner = new Scanner(System.in);
        String command;
       
        System.out.println(upravljacGresaka.prikaziGreske());
        
        System.out.println("Program je pokrenut. Za izlaz pritisnite 'q' ili 'Q'.");
        
        while (true) {
            System.out.print("Unesite komandu: ");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("q")) {
                System.out.println("Izlaz iz programa...");
                break;
            }

            String[] commandParts = command.split(" ");
            
            procesor.procesirajKomandu(command);
        }

        scanner.close();
    }
	
	public static String cleanString(String input) {
        return input.replaceAll("[\\x00-\\x1F\\x7F]", "").trim();
    }
}

