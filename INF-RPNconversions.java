//Szymon Szrajer - 8
import java.util.Scanner;

/*
    Dzialanie programu opiera sie na dwoch konwersjach:
    1) z INF na ONP
    2) z ONP na INF.

    W obu metodach potrzebna byla wlasna implementacja Stacku:
    1) Stacku Stringow
    2) Stacku Stringow i intow

    1) Konwersja z INF na ONP polega na przeksztalceniu danych wejsciowych tak, by zawieraly one tylko obslugiwalne
    znaki. Nastepnie za pomoca implementacj automatu sknczonego bedacego szczegolnym przypadkiem Maszyny Turinga
    posiadajaca 3 mozliwe stany i 5 mozliwych symboli. W tym samym czasie sprawdzana jest rowniez zgodnosc ilosci
    operandow i operatorow i poprawnosc kolejnosci i ilosci nawiasow.

    Dla kazdego znaku przeprowadzony zostaje algorytm:
    I)
        1) jesli znak jest operandem - przepisz na wyjscie.
        2) jesli znak to "(" - przepisywany jest na gore Stacku
        3) jesli znak to ")" - dopoki nie napotka "(" wypsiuje wszystkie operatory na wyjscie
        4) jesli znak jest operatorem
              a) jesli lacznosc prawostronna
                         dopoki hierarchia elementu na szczycie stosu jest wieksza -> wypisywany jest on na wyjscie,
                         sprawdzany operator laduje na gorze stosu
              b) jesli lacznosc jest lewostronna
                   dopoki hierarchia elementu na szczycie stosu jest >= -> wypisywany jest on na wyjscie
                   sprawdzany operator laduje na gorze stosu
     II)
        1) reszta Stacka zostaje wypisana na wyjscie

     2) Konwersja z ONP na INF polega na przeksztalceniu danych wejsciowych tak, by zawieraly one tylko obslugiwalne
    znaki. Nastepnie zostaje sprawdzona zgodnosc ilosci operatorow i operandow.
    Jesli zgodnosc jest zachowana to kazdy znak wejscia poddawany jest dzialaniu algorytmu:
        1) jesli jest operandem - przepisywany zostaje na gore Stacku
        2) jesli jest operatorem lacznym prawostronnie
            I) binarnym
               a)  ktorego hierarchia jest wieksza od obecnego wierzcholka Stacku-
               - to zostaja dodane nawiasy do obecnego wierzcholka Stacku - po wczesniejszym zdjeciu go z gory Stacku
               b)  ktorego hierarchia jest <= od obecnego wierzcholka Stacku- wiercholek Stacku zostaje zdjety
               c)  jesli nowy wierzcholek ma hierarchie mniejsza od obecnie rozpatrywanego operatora binarnego
               - to dodane zostaja do niego nawiasy i polaczony zostaje z poprzednim wierzcholkiem za pomoca
               rozpatrywanego operatora
               d)  w przeciwnym wypadku laczenie nastepuje bez dodania nawiasow;
               e)  tak powstale polaczenie wrzucane jest na gore Stacku
            II) unarnym
               a) ktorego hierarchia jest > od obecnego wierzcholka Stacku to przed dodaniem operatora unarnego do
               wierzcholka zaopatrywany jest on w nawiasy
               b) w przeciwnym wypadku do wierzcholka Stacku zostaje dodany wylacznie operator
               c) tak powstale polaczenie wrzucane jest na gore Stacku

        3) jesli jest operatorem lacznym lewostronnie (tym samym jest binarny)
           a)  ktorego hierarchia jest => od obecnego wierzcholka Stacku-
            - to zostaja dodane nawiasy do obecnego wierzcholka Stacku - po wczesniejszym zdjeciu go z gory Stacku
           b)  ktorego hierarchia jest < od obecnego wierzcholka Stacku- wiercholek Stacku zostaje zdjety
           c)  jesli nowy wierzcholek ma hierarchie mniejsza od obecnie rozpatrywanego operatora binarnego
           - to dodane zostaja do niego nawiasy i polaczony zostaje z poprzednim wierzcholkiem za pomoca
           rozpatrywanego operatora
           d)  w przeciwnym wypadku laczenie nastepuje bez dodania nawiasow;
           e)  tak powstale polaczenie wrzucane jest na gore Stacku


        (Hierarchia zapamietywana jest za pomoca Stacku intow)

 */



class Source {

    // inicjalizacja Scannera
    public static Scanner inp = new Scanner(System.in);

    // metoda zwracajaca typ lacznosci
    public static boolean associativity(char a) {
        String right = "!~^=";
        // jesli prawostronna zwraca true w przeciwnym wypadku zwraca false
        return right.indexOf(a) != -1;
    }

    // metoda zwracajaca hierarchie operandow i operatorow
    public static int hierarchy(String a) {
        String or = "abcdefghijklmnopqrstuvwxyz";
        if (or.contains(a)) {
            return 10;
        }
        else if (a.equals("!") || a.equals("~")) {
            return 9;
        } else if (a.equals("^")) {
            return 8;
        } else if (a.equals("*") || a.equals("/") || a.equals("%")) {
            return 7;
        } else if (a.equals("+") || a.equals("-")) {
            return 6;
        } else if (a.equals("<") || a.equals(">")) {
            return 5;
        } else if (a.equals("?")) {
            return 4;
        } else if (a.equals("&")) {
            return 3;
        } else if (a.equals("|")) {
            return 2;
        } else if (a.equals("=")) {
            return 1;
        }
        else return 0;
    }

    // implementacja Stacku dla Stringow
    static class strStack {

        // wlasciwosci wezla
        class Node {
            public String Data;
            public Node Next;
            public Node (String Data) { this.Data = Data; }
        }

        // inicjalizacja wierzcholka Stacku
        public Node head;

        // metoda sprawdzajaca czy Stack jest pusty
        public boolean empty(){
            return head == null;
        }

        // metoda zwracajaca wartosc wierzcholka Stacku
        public String top() {
            if (head == null) {
                return "";
            }
            else { return head.Data; }
        }

        // metoda usuwajaca wierzcholek Stacku i zwracajaca jego wartosc
        public String pop() {
            String Data = head.Data;
            head = head.Next;
            return Data;
        }

        // metoda dodajaca nowy wierzcholek Stacku
        public void push(String Data) {
            Node node = new Node(Data);
            node.Next = head;
            head = node;
        }
    }

    // analogicznie - implementacja Stacku dla intow
    public static class intStack {

        public class Node {
            public int Data;
            public Node Next;
            public Node (int Data) { this.Data = Data; }
        }

        public Node head;

        public boolean empty(){
            return head == null;
        }

        public int top() {
            if (head == null) {
                return 0;
            }
            else { return head.Data; }
        }

        public int pop() {
            int Data = head.Data;
            head = head.Next;
            return Data;
        }

        public void push(int Data) {
            Node node = new Node(Data);
            node.Next = head;
            head = node;
        }
    }

    public static void main(String[] args) {

        // wczytanie ilosci zestawow danych
        int loop = inp.nextInt();
        // operandy
        String or = "abcdefghijklmnopqrstuvwxyz";
        // operatory binarne
        String bop = "^*/%+-<>?&|=";
        // operatory unarne
        String uop = "!~";
        // nawiasy
        String lb  = "(";
        String rb  = ")";

        for (int i = 0; i < loop; i ++) {
            // wczytanie ONP:/INF:
            String inp1 = inp.next();
            // wczytanie zestawu danych
            String inp2 = inp.nextLine();

            // konwersja INF -> ONP
            if (inp1.charAt(0) == 'I') {
                String coInp = "";
                // wyluskanie poprawnych znakow z zestawu
                for (int k = 1; k < inp2.length(); k ++) {
                    if ( or.indexOf(inp2.charAt(k)) != -1 || bop.indexOf(inp2.charAt(k)) != -1 ||
                            uop.indexOf(inp2.charAt(k)) != -1 || lb.indexOf(inp2.charAt(k)) != -1
                            || rb.indexOf(inp2.charAt(k)) != -1 ) {
                        coInp += inp2.charAt(k);
                    }
                }
                String conversion = "";

                // stan maszyny
                int stage = 0;
                // sprawdzanie czy wystapil blad
                boolean error = false;
                // ilosc podwyrazen
                int subequations = 0;
                // ilosc otwartych nawiasow
                int open = 0;
                int length = coInp.length();

                // implementacja automatu skonczonego bedacego szczegolnym przypadkiem maszyny Turinga
                for (int k = 0; k < length; k++) {

                    // stan 0, blad dla operatorow binarnych
                    if ( stage == 0 ) {
                        if (or.indexOf(coInp.charAt(k)) != -1) {
                            stage = 1;
                            subequations++;
                        }
                        else if (bop.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                            subequations--;
                        }
                        else if (uop.indexOf(coInp.charAt(k)) != -1) {
                            stage = 2;
                        }
                        else if (lb.indexOf(coInp.charAt(k)) != -1) {
                            open++;
                        }
                        else if (rb.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                            open--;
                        }
                    }
                    // stan 1, blad dla operandow, operatorow unarnych i otwarcia nawiasu
                    else if ( stage == 1 ) {
                        if (or.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                            subequations++;
                        }
                        else if (bop.indexOf(coInp.charAt(k)) != -1) {
                            stage = 0;
                            subequations--;
                        }
                        else if (uop.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                        }
                        else if (lb.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                            open++;
                        }
                        else if (rb.indexOf(coInp.charAt(k)) != -1) {
                            open--;
                        }
                    }
                    // stan 2, blad dla operatorow binarnych i zamkniecia nawiasu
                    else {
                        if (or.indexOf(coInp.charAt(k)) != -1) {
                            stage = 1;
                            subequations++;
                        }
                        else if (bop.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                            subequations--;
                        }
                        else if (uop.indexOf(coInp.charAt(k)) != -1) {
                        }
                        else if (lb.indexOf(coInp.charAt(k)) != -1) {
                            stage = 0;
                            open++;
                        }
                        else if (rb.indexOf(coInp.charAt(k)) != -1) {
                            error = true;
                            open--;
                        }
                    }
                    // jezeli w danym momencie wiecej jest zamknietych niz ptwartych nawiasow -> blad
                    if (open < 0) error = true;
                }

                // sprawdzenie zgodnosci ilosci operandow i operatorow
                if (subequations != 1) error = true;
                // sprawdzenie czy wszystkie nawiasy zostaly zamkniete
                if (open != 0) error = true;
                if (error == true) {
                    System.out.println("ONP: error");
                } else {

                    strStack Postfix = new strStack();
                    // konwersja INF -> ONP
                    for (int k = 0; k < length; k++) {

                        // wypisz operatory bezposrednio na wyjscie
                        if (or.indexOf(coInp.charAt(k)) != -1) {
                            conversion += " " + coInp.charAt(k);
                        }
                        // operatory
                        else if ((bop.indexOf(coInp.charAt(k)) != -1) || (uop.indexOf(coInp.charAt(k)) != -1)) {

                            // o lacznosci prawostronnej
                            if (associativity(coInp.charAt(k))) {
                                // dopoki hierarchia elementu na szczycie stosu jest wieksza -> wypisywany jest on
                                // na wyjscie
                               while (( hierarchy(Postfix.top()) > hierarchy(String.valueOf(coInp.charAt(k))) )
                                && (!Postfix.top().equals("(")) && (!Postfix.empty())){
                                   conversion += " " + Postfix.pop();
                               }
                               // sprawdzany operator laduje na gorze stosu
                               Postfix.push(String.valueOf(coInp.charAt(k)));

                               // o lacznosci lewostronnej
                            } else {
                                // dopoki hierarchia elementu na szczycie stosu jest >= -> wypisywany jest on
                                // na wyjscie
                                while (( hierarchy(Postfix.top()) >= hierarchy(String.valueOf(coInp.charAt(k))) )
                                        && (!Postfix.top().equals("(")) && (!Postfix.empty())){
                                    conversion += " " + Postfix.pop();
                                }
                                // sprawdzany operator laduje na gorze stosu
                                Postfix.push(String.valueOf(coInp.charAt(k)));
                            }
                        }
                        // otwarcie nawiasu laduje bezposrednio na stosie
                        else if (lb.indexOf(coInp.charAt(k)) != -1) {
                            Postfix.push(String.valueOf(coInp.charAt(k)));
                        }
                        // zamkniecie nawiasu
                        else if (rb.indexOf(coInp.charAt(k)) != -1) {
                            // wszystkie elementy w danym nawiasie wypisywane na wyjscie
                            while (!Postfix.top().equals("(")) {
                                conversion += " " + Postfix.pop();
                            }
                            // otwarcie nawiasu pomijane
                            Postfix.pop();
                        }
                    }

                    // dopisanie do wyjscia pozostalych elementow
                    while (!Postfix.empty()) {
                        conversion += " " + Postfix.pop();
                    }

                    System.out.println("ONP:" + conversion);

                }
            } else {
                // konwersja ONP -> INF
                String coInp = "";
                // zmienna przechowujaca ilosc operandow pomniejszona o ilosc operatorow binarnych
                int subequations = 0;
                // zmienna potrzebna do zaznaczania bledu
                boolean error = false;
                for (int k = 1; k < inp2.length(); k ++) {
                    if ( or.indexOf(inp2.charAt(k)) != -1 || bop.indexOf(inp2.charAt(k)) != -1 || uop.indexOf(inp2.charAt(k)) != -1 ) {
                        coInp += inp2.charAt(k);
                        if (or.indexOf(inp2.charAt(k)) != -1 ) subequations++;
                        else if (bop.indexOf(inp2.charAt(k)) != -1) subequations--;
                        if (subequations <= 0) {
                            error = true; // jesli w danym momencie jest tyle samo operatorow i operandow -> blad
                        }
                    }
                }

                if (subequations != 1) error = true; // jesli ilosc operandow - operatorow binarnych != 1 -> blad
                if (error) {
                    System.out.println("INF: error");
                } else {
                    // konwersja ONP -> INF
                    strStack Infix = new strStack(); // stos rozpatrywanych symboli
                    intStack InfixInt = new intStack(); // stos priorytetow

                    for ( int k = 0; k < coInp.length(); k++) {

                        // operatory bezposrednio na stos, dodanie priorytetu
                        if (or.indexOf(coInp.charAt(k)) != -1) {
                            Infix.push(String.valueOf(coInp.charAt(k)));
                            InfixInt.push(hierarchy(String.valueOf(coInp.charAt(k))));
                        }
                        else if (associativity(coInp.charAt(k))) {
                            String back = null;
                            // prawostronne operatory binarne
                            if (bop.indexOf(coInp.charAt(k)) != -1) {
                                // jesli priorytet pierwszego zdjetego operatora mniejszy od sprawdzanego
                                // -> dodanie nawiasow
                                if (InfixInt.top() < hierarchy(String.valueOf(coInp.charAt(k))) ) {
                                    back = "( " + Infix.pop() + " )";
                                } else {
                                    back = Infix.pop();
                                }
                                // usuniecie priorytetu
                                InfixInt.pop();
                                // jesli priorytet drugiego zdjetego operatora mniejszy od sprawdzanego
                                // -> dodanie nawiasow
                                if (InfixInt.top() < hierarchy(String.valueOf(coInp.charAt(k)))) {
                                    back = "( " + Infix.pop() + " ) " +  coInp.charAt(k) + " " + back;
                                } else {
                                    back = Infix.pop() + " " + coInp.charAt(k) + " " + back;
                                }
                                // usuniecie priorytetu
                                InfixInt.pop();
                                // wrzucenie wyrazenia po dodaniu operatora na stos
                                Infix.push(back);
                                // dodanie priorytetu
                                InfixInt.push(hierarchy(String.valueOf(coInp.charAt(k))));
                            } else {
                                // prawostronne operatory unarne
                                //  jesli hierarchia sprawdzanego operatora wieksza niz tego na gorze stosu
                                // -> dodane zostaja nawiasy
                                if (InfixInt.top() < hierarchy(String.valueOf(coInp.charAt(k)))) {
                                    back = coInp.charAt(k) + " ( " + Infix.pop() + " )";
                                } else {
                                    back = coInp.charAt(k) + " " + Infix.pop();
                                }
                                // usuniecie priorytetu
                                InfixInt.pop();
                                // wrzucenie wyrazenia po dodaniu operatora na stos
                                Infix.push(back);
                                // dodanie priorytetu
                                InfixInt.push(hierarchy(String.valueOf(coInp.charAt(k))));
                            }
                        } else {
                            // lewostronne operatory binarne
                            String back = null;
                            // jesli priorytet pierwszego zdjetego operatora <= od sprawdzanego
                            // -> dodanie nawiasow
                            if (InfixInt.top() <= hierarchy(String.valueOf(coInp.charAt(k)))){
                                back = "( " + Infix.pop() + " )";
                            } else {
                                back = Infix.pop();
                            }
                            // usuniecie priorytetu
                            InfixInt.pop();
                            // jesli priorytet drugiego zdjetego operatora mniejszy od sprawdzanego
                            // -> dodanie nawiasow
                            if (InfixInt.top() < hierarchy(String.valueOf(coInp.charAt(k)))) {
                                back = "( " + Infix.pop() + " ) " + coInp.charAt(k) + " " + back;
                            } else {
                                back = Infix.pop() + " " + coInp.charAt(k) + " " + back;
                            }
                            // usuniecie priorytetu
                            InfixInt.pop();
                            // wrzucenie wyrazenia po dodaniu operatora na gore stosu
                            Infix.push(back);
                            // dodanie priorytetu
                            InfixInt.push(hierarchy(String.valueOf(coInp.charAt(k))));

                        }
                    }
                    System.out.println("INF: " + Infix.top());
                }
            }
        }
    }
}


/*

 test0.in
10
ONP: abcde^=^=
ONP: xyz~~~++
ONP: abc-d++
ONP: a!
ONP: abcd++++
INF: ~(!(~((~e+!~f)/~g)*~h)^~i)
INF: !(a+b)*1245466(f+t)
INF: !a*b*c
INF: (((((a+b`123415)*(r-p)=(a)))))
INF: )(a+b+v)

 test0.out
INF: a = b ^ ( c = d ^ e )
INF: x + ( y + ~ ~ ~ z )
INF: a + ( b - c + d )
INF: ! a
INF: error
ONP: e ~ f ~ ! + g ~ / ~ h ~ * ! i ~ ^ ~
ONP: a b + ! f t + *
ONP: a ! b * c *
ONP: a b + r p - * a =
ONP: error

 */