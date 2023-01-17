import java.util.Scanner;

public class Fahrkartenautomat {

    public static void main(String[] args) {

        boolean abortMain = false;

        // loop until stopped manually
        while (!abortMain) {

            double toPay = getPrice();

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

    public static double getPrice() {

        String[] tickets = new String[11];
        tickets[0] = "bezahlen";
        tickets[1] = "Einzelfahrschein Berlin AB";
        tickets[2] = "Einzelfahrschein Berlin BC";
        tickets[3] = "Einzelfahrschein Berlin ABC";
        tickets[4] = "Kurzstrecke";
        tickets[5] = "Tageskarte Berlin AB";
        tickets[6] = "Tageskarte Berlin BC";
        tickets[7] = "Tageskarte Berlin ABC";
        tickets[8] = "Kleingruppen-Tageskarte Berlin AB";
        tickets[9] = "Kleingruppen-Tageskarte Berlin BC";
        tickets[10] = "Kleingruppen-Tageskarte Berlin ABC";

        double[] preis = new double[11];
        preis[0] = 0;
        preis[1] = 2.90;
        preis[2] = 3.30;
        preis[3] = 3.60;
        preis[4] = 1.90;
        preis[5] = 8.60;
        preis[6] = 9.00;
        preis[7] = 9.60;
        preis[8] = 23.50;
        preis[9] = 24.30;
        preis[10] = 24.90;

        System.out.print("Fahrkartenbestellvorgang:\n" +
                "=========================\n\n" +
                "Wählen Sie ihre Wunschfahrkarte für Berlin AB aus:\n" +
                "(0) Bezahlen\n");

        for (int i = 1; i < tickets.length; i++) {
            System.out.printf("(" + i + ") " + tickets[i] + " [%4.2f]\n", preis[i]);
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
                System.out.print("Ihre Wahl (0-" + (tickets.length - 1) + "): ");

                // save ticket type from keyboard input in variable
                tarif = tastatur.nextInt();

                if (tarif == 0) {
                    comboChecker = true;
                    tarifChecker = true;
                } else if (tarif > 0 && tarif < tickets.length) {
                    zuZahlenderBetragTemp = preis[tarif];
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
