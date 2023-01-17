import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Fahrkartenautomat {

    public static void main(String[] args) {

        HashMap<String, Double> Fahrkarten = new LinkedHashMap<String, Double>();
        Fahrkarten.put("(1) Einzelfahrschein Berlin AB", 2.90);
        Fahrkarten.put("(2) Einzelfahrschein Berlin BC", 3.30);
        Fahrkarten.put("(3) Einzelfahrschein Berlin ABC", 3.60);
        Fahrkarten.put("(4) Kurzstrecke", 1.90);
        Fahrkarten.put("(5) Tageskarte Berlin AB", 8.60);
        Fahrkarten.put("(6) Tageskarte Berlin BC", 9.00);
        Fahrkarten.put("(7) Tageskarte Berlin ABC", 9.60);
        Fahrkarten.put("(8) Kleingruppen-Tageskarte Berlin AB", 23.50);
        Fahrkarten.put("(9) Kleingruppen-Tageskarte Berlin BC", 24.30);
        Fahrkarten.put("(10) Kleingruppen-Tageskarte Berlin ABC", 24.90);

        boolean abortMain = false;

        // loop until stopped manually
        while (!abortMain) {

            double toPay = getPrice(Fahrkarten);

            double payed = makePayment(toPay);

            getChange(payed, toPay);

            // listen to keyboard input
            Scanner tastatur = new Scanner(System.in);

            System.out.print("x eingeben, um das Script zu stoppen.\n" +
                    "Etwas anderes eingeben, um fortzufahren. ");
            String quest = tastatur.next();

            if (quest.equals("x")) {
                abortMain = true;
            }
        }
    }

    public static double getPrice(HashMap<String, Double> Fahrkarten) {


        System.out.print("Fahrkartenbestellvorgang:\n" +
                "=========================\n\n" +
                "Wählen Sie ihre Wunschfahrkarte für Berlin AB aus:\n" +
                "(0) Bezahlen\n");

        for (HashMap.Entry<String, Double> e : Fahrkarten.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }

        // listen to keyboard input
        Scanner tastatur = new Scanner(System.in);

        // initialize variables
        double zuZahlenderBetragTemp = 0.0;
        double zuZahlenderBetrag = 0.0;
        double anzahlTickets = 0;
        boolean tarifChecker = false;
        boolean comboChecker = false;
        int tarif;

        while (!comboChecker) {

            zuZahlenderBetragTemp = 0.0;

            while (!tarifChecker) {

                // ask user for the ticket type
                System.out.print("Ihre Wahl (0-" + (Fahrkarten.size()) + "): ");

                // save ticket type from keyboard input in variable
                tarif = tastatur.nextInt();

                if (tarif == 0) {
                    comboChecker = true;
                    tarifChecker = true;
                } else if (tarif > 0 && tarif < Fahrkarten.size()) {
                    for (HashMap.Entry<String, Double> e : Fahrkarten.entrySet()) {
                        if (e.getKey().indexOf(String.valueOf(tarif)) != -1) {
                            zuZahlenderBetragTemp = e.getValue();
                            break;
                        }
                    }
                    tarifChecker = true;
                    System.out.println("Ihre Wahl: Tarif " + tarif);
                } else {
                    System.out.println("Ungültige Wahl, bitte wiederholen!");
                }
            }

            tarifChecker = false;

            // ask user for amount of tickets
            if (!comboChecker) {
                System.out.print("Anzahl der Tickets: ");

                // save amount of tickets from keyboard input in variable
                anzahlTickets = tastatur.nextDouble();

                // check for ticket amount greater than 10
                while (anzahlTickets > 10 || anzahlTickets < 1) {
                    System.out.println("Wählen Sie bitte eine Anzahl von 1 bis 10 Tickets aus. ");

                    // save amount of tickets from keyboard input in variable
                    anzahlTickets = tastatur.nextDouble();
                }
            }

            // calculate final price
            zuZahlenderBetragTemp *= anzahlTickets;
            zuZahlenderBetrag += zuZahlenderBetragTemp;

        }

        // return calculated price
        return zuZahlenderBetrag;
    }

    public static double makePayment(double zuZahlenderBetrag) {

        double eingezahlterGesamtbetrag = 0.0;
        double eingeworfeneMuenze;

        Scanner tastatur = new Scanner(System.in);

        // Geldeinwurf
        // -----------

        while(eingezahlterGesamtbetrag < zuZahlenderBetrag)
        {
            System.out.format("Noch zu zahlen: %4.2f €%n" , (zuZahlenderBetrag - eingezahlterGesamtbetrag));
            System.out.print("Eingabe (mind. 5Ct, höchstens 2 Euro): ");
            eingeworfeneMuenze = tastatur.nextDouble();
            eingezahlterGesamtbetrag += eingeworfeneMuenze;
        }
        return eingezahlterGesamtbetrag;
    }


    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void printTicket(){
        // Fahrscheinausgabe
        // -----------------
        System.out.println("\nFahrschein wird ausgegeben");
        for (int i = 0; i < 8; i++)
        {
            System.out.print("=");
            wait(100);
        }
        System.out.println("\n\n");
    }

    public static void giveChange(int amount, String currency){
        System.out.println(amount + " " + currency);
    }

    public static void getChange(double eingezahlterGesamtbetrag, double zuZahlenderBetrag) {

        if (zuZahlenderBetrag != 0) {
            printTicket();

            double rueckgabebetrag;

            // Rueckgeldberechnung und -Ausgabe
            // -------------------------------
            rueckgabebetrag = eingezahlterGesamtbetrag - zuZahlenderBetrag;
            if (rueckgabebetrag > 0.0) {
                System.out.format("Der Rueckgabebetrag in Höhe von %4.2f € %n", rueckgabebetrag);
                System.out.println("wird in folgenden Muenzen ausgezahlt:");

                while (rueckgabebetrag >= 2.0) // 2 EURO-Muenzen
                {
                    giveChange(2, "EURO");
                    rueckgabebetrag -= 2;
                }
                while (rueckgabebetrag >= 1.0) // 1 EURO-Muenzen
                {
                    giveChange(1, "EURO");
                    rueckgabebetrag -= 1;
                }
                while (rueckgabebetrag >= 0.5) // 50 CENT-Muenzen
                {
                    giveChange(50, "CENT");
                    rueckgabebetrag -= 0.5;
                }
                while (rueckgabebetrag >= 0.2) // 20 CENT-Muenzen
                {
                    giveChange(20, "CENT");
                    rueckgabebetrag -= 0.2;
                }
                while (rueckgabebetrag >= 0.1) // 10 CENT-Muenzen
                {
                    giveChange(10, "CENT");
                    rueckgabebetrag -= 0.1;
                }
                while (rueckgabebetrag >= 0.05)// 5 CENT-Muenzen
                {
                    giveChange(5, "CENT");
                    rueckgabebetrag -= 0.05;
                }
            }

            System.out.println("Vergessen Sie nicht, den Fahrschein\n" +
                    "vor Fahrtantritt entwerten zu lassen!\n" +
                    "Wir wuenschen Ihnen eine gute Fahrt.\n");
        } else {
            System.out.println("Sie haben keine Tickets bestellt.");
        }
    }
}
