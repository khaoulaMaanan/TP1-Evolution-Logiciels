package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static java.lang.Integer.parseInt;

public class Main {
    public static String TEST_PROJECT_PATH;

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("****** Bienvenue dans l'application d'analyse statique d'une application orientée objet :");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez entrer le chemin du projet que vous voulez analyser : ");
        TEST_PROJECT_PATH = reader.readLine();
        Processor processor = new Processor(TEST_PROJECT_PATH);
        processor.process();
        printMenu(processor);


    }

    public static void printMenu(Processor processor) throws IOException, URISyntaxException {
        System.out.println("******Menu******");
        System.out.println("Pour afficher les statistiques issues de l'analyse statique, cliquez sur 1 : ");
        System.out.println("Pour afficher le graphe d'appel, cliquez sur 2 : ");
        System.out.println("Pour arrêter l'application, cliquez sur 0 :");
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
        switch (parseInt(reader1.readLine())) {
            case 1:
                System.out.println("******Statistiques******");
                processor.AfficherStatistiques();
                printMenu(processor);
                break;
            case 2:
                System.out.println("******Graphe d'appel******");
                processor.printMethodsInvForEachMethod();
                printMenu(processor);
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Veuillez entrer un chiffre valide (1 ou 2 ou 0)");
                printMenu(processor);
                break;
        }

    }
}