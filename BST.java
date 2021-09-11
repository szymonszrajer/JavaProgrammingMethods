//Szymon Szrajer - 8
import java.util.Scanner;

/*
    Program jest implementacja drzewa BST:

    Modul kolejkowania:
    ENQUE
        szuka poprzednika o najbardziej zblizonym priorytecie i dodaje go po opowiedniej stronie
    DEQUEMAX
        przechodzi maksymalnie w prawo i usuwa wezel (wezel ten moze miec lewe poddrzewo)
    DEQUEMIN
        przechodzi maksymalnie w lewo i usuwa wezel (wezel ten moze miec prawe poddrzewo)
    NEXT
        zwraca wartosc nastepnika zadanego priorytetu (o ile wystepuje i istnieje wezel o zadanym priorytecie) szuka
        priorytetu nastepnie idac jednokrotnie w prawo jesli to mozliwe, potem tak dlugo w lewo jak to mozliwe
    PREV
        zwraca wartosc poprzednika zadanego priorytetu (o ile wystepuje i istnieje wezel o zadanym priorytecie) szuka
        priorytetu nastepnie idac jednokrotnie w lewo jesli to mozliwe, potem tak dlugo w prawo jak to mozliwe

    Modul raportowania:
    PREORDER
        zwraca przeglad w postaci PREORDER (najpierw korzen, lewe poddrzewo korzenia, prawe poddrzewo korzenia)
    INORDER
        zwraca przeglad w postaci INORDER (lewe poddrzewo korzenia, korzen, prawe poddrzewo korzenia)
    POSTORDER
        zwraca przeglad w postaci POSTORDER (lewe poddrzewo korzenia, prawe poddrzewo korzenia, korzen)
    HEIGHT
        rekurencyjnie zwraca maksymalna dlugosc sciezki rozpatrujac wszystkie mozliwe sciezki zaczynajac od wezla

    Modul edycji:
    CREATE
        - opcja PREORDER
            na podstawie przegladu PREORDER tworzy drzewo BST
            zaczynajac od poczatku tablicy przegladowej rekurencyjnie rozpatruje kolejne elementy- jesli dany element
            spelnia warunek znajdowania sie w przedziale wyznaczonym przez pozostale priorytety drzewa (na poczatku
            rekurencji sa to Integer.MIN_VALUE oraz Integer.MAX_VALUE), to z informacji o tym elemencie
            zostaje utworzony nowy wezel. Zwiekszony zostaje indeks elementu oraz wywolana zostaje rekurencja tworzaca
            prawe i lewe poddrzewo z wartoscia nowego indeksu oraz przedzialem:
            dla lewego poddrzewa: [obecny poczatek; obecny priorytet -1],
            dla prawego poddrzewa: [obecny priorytet + 1; obecny koniec].
            Kluczowe jest takze wywolanie tworzenia najpierw lewego, potem prawego poddrzewa- zgodnie z przegladem
            preorder.
        - opcja POSTORDER
            na podstawie przegladu PREORDER tworzy drzewo BST
            zaczynajac od konca tablicy przegladowej rekurencyjnie rozpatruje kolejne elementy- jesli dany element
            spelnia warunek znajdowania sie w przedziale wyznaczonym przez pozostale priorytety drzewa (na poczatku
            rekurencji sa to Integer.MIN_VALUE oraz Integer.MAX_VALUE), to z informacji o tym elemencie
            zostaje utworzony nowy wezel. Zmniejszony zostaje indeks elementu oraz wywolana zostaje rekurencja tworzaca
            prawe i lewe poddrzewo z wartoscia nowego indeksu oraz przedzialem:
            dla prawego poddrzewa: [obecny priorytet + 1; obecny koniec],
            dla lewego poddrzewa: [obecny poczatek; obecny priorytet -1].
            Kluczowe jest takze wywolanie tworzenia najpierw prawego, potem lewego poddrzewa- zgodnie z przegladem
            postorder (poniewaz przegladamy postorder od konca).
    DELETE
        usuwa wezel o zadanym priorytecie (o ile istnieje), 3 przypadki:
        1) wezel nie ma potomkow- wystarczy usunac referencje rodzica na ten wezel
        2) wezel ma jednego potomka - przestawienie referencji rodzica na tego potomka
        3) wezel ma dwoje potomkow- wstawia nastepnika w miejsce usuwanego wezla i przestawia referencje rodzica
        zamienianego nastepnika jesli to konieczne (na null lub prawe poddrzewo potomka)

 */
public class Source {

    // stos wezlow
    public static class nodeStack {

        // klasa wezla na stosie
        public class Node {
            public node Data; // dane przekazywane na stos
            public Node Next; // referencja do nastepnego elementu na stosie
            public Node (node Data) { this.Data = Data; } // konstruktor
        }
        public Node head; // wskaznik na gore stosu
        // metoda empty - sprawdza czy na stosie znajduje sie cokolwiek
        public boolean empty(){ return head == null; }

        // metoda top - zwraca wartosc wierzcholka stosu
        public node top() {
            if (head == null) {
                return null;
            }
            else { return head.Data; }
        }

        // metoda pop - usuwa wierzcholek stosu ze zwracaniem wartosci i zmienia wierzcholek na nastepny element
        public node pop() {
            node Data = head.Data;
            head = head.Next;
            return Data;
        }
        // metoda push - wrzuca zadana wartosc na wierzcholek stosu
        public void push(node Data) {
            Node mode = new Node(Data);
            mode.Next = head;
            head = mode;
        }
    }

    static class node { // klasa wezla
        public Person info; // informacje o osobie
        public node left; // referencja do lewego poddrzewa
        public node right; // referencja do prawego poddrzewa
        public node(Person a) {info = a;} // konstruktor
    }

    // klasa drzewa binarnego
    static class Tree {
        private static node root; // referencja
        public Tree() { root = null; } // konstruktor

        // dodanie wezla do drzewa
        public static void enque(Person person) {
                node newN = new node(person); // inicjalizacja nowego wezla
                node curr = root, prev = root; // referencje do szukania odpowiedniego miejsca na priorytet
                while (curr != null) { // dopoki nie jestesmy w lisciu
                    prev = curr; // zapamietanie poprzednika
                    if (person.priority < curr.info.priority ) // jesli priorytet mniejszy -> lewo
                        curr = curr.left;
                    else                                       // jesli priorytet wiekszy -> prawo
                        curr = curr.right;
                }
                if (prev == curr) { //jesli drzewo jest puste newN to nowy root
                    root = newN;
                }
                // w przeciwnym wypadku sprawdzamy po ktorej stronie poprzednika dodac nowy lisc
                else if (person.priority < prev.info.priority)
                    prev.left = newN; // po lewej gdy ma mniejszy priorytet
                else
                    prev.right = newN; // po prawej gdy ma wiekszy priorytet
        }
        /*
         usuniecie wezla z drzewa
         algorytm usuwania sprowadza sie do 3 przypadkow:
         1: wezel ktory usuwamy nie ma potomkow (jest lisciem)
         wystarczy przestawic referencje poprzednika na null
         2: wezel ktory usuwamy ma jednego potomka
         nalezy przestawic referencje poprzednika na potomka
         3: wezel ktory usuwamy ma dwoje potomkow
         nalezy wyznaczyc nastepnika, ktory bedzie sie znajdowal w prawym poddrzewie, dokladnie
         wyznaczymy go idac jednokrotnie w prawo a nastepnie do konca w lewo, nastepnik ten moze miec
         potomka, ale zawsze bedzie znajdywal sie po prawej stronie, jesli nastepnik ma potomka, to nalezy
         przestawic referencje jego poprzednika na prawe poddrzewo i zamienic pole info z zamienianym elementem
         skrajnym przypadkiem jest usuwanie korzenia, analogicznie:
         1: ustawiamy korzen na null
         2: przestawiamy root na jedynego potomka
         3: nastepnik z prawego poddrzewa staje sie korzeniem
        */
        public static boolean delete(int prio) {
            node prev = null; // poprzednik
            node curr = root; // wezel szukania ustawiamy na root
            while (curr != null && prio != curr.info.priority) { // az nie dostaniemy sie do liscia/znajdziemy prio
                prev = curr; // zmiana poprzednika
                if (prio < curr.info.priority) // pojscie w prawo lub lewo w zaleznosci od priorytetu
                    curr = curr.left;
                else
                    curr = curr.right;
            }
            if (curr == null) { // nie znaleziono wezla
                return false;
            }
            if (curr.left == null && curr.right == null) { // przypadek 1- brak potomow
                if (curr == root) { // usuwanie wezla bez potomkow
                    root = null;
                }
                else { // usuwanie liscia przez ustawienie na null referencji poprzednika
                    if (prev.left == curr) {
                        prev.left = null;
                    }
                    else {
                        prev.right = null;
                    }
                }
            }
            else if (curr.left != null && curr.right == null) { // przypadek 2a = wylacznie lewy potomek
                if (curr == root) { // zmiana referencji root na lewego potomka
                    root = curr.left;
                } else { // zmiana referencji poprzednika na istniejace dziecko
                    if (curr == prev.left){
                        prev.left = curr.left;
                    } else {
                        prev.right = curr.left;
                    }
                }
            }
            else if (curr.left == null && curr.right != null) { // przypadek 2b = wylacznie prawy potomek
                if (curr == root) { // zmiana referencji root na prawego potomka
                    root = curr.right;
                } else { // zmiana referencji poprzednika na istniejace dziecko
                    if (curr == prev.left){
                        prev.left = curr.right;
                    } else {
                        prev.right = curr.right;
                    }
                }
            }
            else { // if (curr.left != null && curr.right != null) przypadek 3 - dwoje potomkow
                node succ = curr.right; // nastepnik to w skrajnym przypadku prawe dziecko
                node parent = curr; // poprzednik wyznaczanego nastepnika
                while (succ.left != null ) { // szukanie nastepnika w lewym poddrzewie prawego wezla
                    parent = succ; // zmiana referencji poprzednika nastepnika
                    succ = succ.left; // pojscie w lewo
                }
                if (parent == curr) { // jesli nie bylo ruchu w lewo ani razu
                    if (curr == root) { // jesli usuwany wezel jest korzeniem
                        curr.right.left = curr.left; // lewe poddrzewo nastepnika bedzie teraz lewym poddrzewem
                        // usuwanego wezla
                        root = curr.right; // przesuniecie referencji korzenia
                    }
                    else { // jesli usuwany wezel nie jest korzeniem
                        curr.right.left = curr.left; // lewe poddrzewo nastepnika bedzie teraz lewym poddrzewem
                        // usuwanego wezla
                        if (prev.left == curr) prev.left = curr.right; // jesli usuwany wezel jest lewym potomkiem
                        // swojego poprzednika to przestawiamy lewa referencje rodzica na prawa potomka
                        else prev.right = curr.right; // w przeciwnym przypadku symetrycznie prawa referencje
                    }
                } else { // jesli przeszlismy chociaz raz w lewo
                        curr.info = succ.info; // zmieniamy wartosc info usuwanego wezla z nastepnikiem
                        if(succ.right != null) parent.left = succ.right; // jesli nastepnik ma prawe drzewo (moze miec
                        // tylko prawe drzewo) to przestawiamy referencje rodzica na to poddrzewo
                        else parent.left = null; // w przeciwnym wypadku usuwamy referencje
                }

            }
            return true; // znaleziono i usunieto wezel
        }
        // szukanie i usuwanie ze zwracaniem najwyzszej wartosci w drzewie
        public static node dequemax() {
            node curr, prev; // wezly do szukania
            curr = prev = root; // zaczynamy od korzenia
            while( curr.right != null) {
                prev = curr; // zapamientujac poprzednika
                curr = curr.right; // idziemy maksymalnie w prawo
            }
            if (prev == curr) { // jesli nie ruszylismy z korzenia
                if (prev.left != null) root = prev.left; // jesli korzen ma lewe poddrzewo to przestawiamy referencje
                // korzenia na to lewe podddrzewo
                else root = null; // w przeciwnym wypadku nalezy usunac drzewo
            }
            else { // analogicznie nie dla korzenia
                if (curr.left != null) prev.right = curr.left; // jesli usuwany najwiekszy wezel ma lewe poddrzewo to
                // przestawiamy referencje poprzednika na to lewe poddrzewo
                else prev.right  = null; // lub usuwamy wezel nie robiac nic
            }
            return curr; // zwracamy najwyzsza wartosc w drzewie
        }
        // szukanie i usuwanie ze zwracaniem najnizszej wartosci w drzewie
        public static node dequemin() {
            node curr, prev; // wezly do szukania
            curr = prev = root; // zaczynamy korzzenia
            while( curr.left != null) {
                prev = curr; // zapamientujac poprzednika
                curr = curr.left; // idziemy maksymalnie w lewo
            }
            if (prev == curr) { // jesli nie ruszylismy z korzenia
                if (prev.right != null) root = prev.right; // jesli korzen ma prawe poddrzewo to przestawiamy referencje
                    // korzenia na to prawe podddrzewo
                else root = null; // w przeciwnym wypadku nalezy usunac drzewo
            }
            else { // analogicznie nie dla korzenia
                if (curr.right != null) prev.left = curr.right; // jesli usuwany najmniejszy wezel ma prawe poddrzewo to
                    // przestawiamy referencje poprzednika na to prawe poddrzewo
                else prev.left = null; // lub usuwamy wezel nie robiac nic
            }
            return curr; // zwracamy najnizsza wartosc w drzewie
        }
        // tworzenie przegladu preorder dla drzewa (najpierw korzen, lewe poddrzewo, prawe poddrzewo)
        // jesli rozpatrywany wezel ma prawego potomka to zostaje on umieszczony na stosie, nastepnie przechodzimy
        // do lewego poddrzewa, jesli istnieje po czym, jesli rozpatrywalismy w danym momencie wartosc null
        // analzujemy wierzcholek stacku ( jesli istnieje
        public static void preorder () {
            StringBuilder result = new StringBuilder("PREORDER: "); // String wynikowy
            nodeStack Stack = new nodeStack(); // stos pusty
            node p = root; // zaczynamy od korzenia
            while ( p != null || !Stack.empty() ){ // az nie oproznimy stacka i nie mamy do rozpatrzenia juz wartosci
                // wezla
                if ( p != null ) { // jesli obecnie nie ma zadnej wartosci w wezle
                    // dopisanie do Stringa wynikowego
                    result.append(p.info.priority).append(" - ").append(p.info.name).append(" ").append(p.info.surname).append(", ");
                    Stack.push (p.right); // wrzucenie prawego poddrzewa na stos
                    p = p.left; // przejscie w lewo albo przypisanie nulla do wezla przegladajacego
                }
                else { p = Stack.pop(); } // to pobieramy ja ze stacka jesli sie na nim znajduje
            }
            result.setLength(result.length() - 2); // usuwamy ostatni przecinek i spacje
            System.out.println(result); // wypisujemy wynik
        }
        // tworzenie przegladu inorder dla drzewa (najpierw lewe poddrzewo, korzen, prawe poddrzewo)
        // jesli rozpatrujemy isntiejacy wezel to zostaje on umieszczony na stosie, nastepnie przechodzimy
        // do lewego poddrzewa, jesli rozpatrywalismy w danym momencie wartosc null dodajemy do Stringa wynikowego
        // wierzcholek stacku i przechodzimy w prawo
        public static void inorder () {
            StringBuilder result = new StringBuilder("INORDER: "); // String wynikowy
            nodeStack Stack = new nodeStack(); // stos pusty
            node p = root; // zaczynamy od korzenia
            while ( p != null || !Stack.empty() ){ // az nie oproznimy stacka i nie mamy do rozpatrzenia juz wartosci
                if ( p != null ) {
                    Stack.push (p); // umieszczenie rozpatrywanego wezla na stosie
                    p = p.left; // przejscie w lewo
                }
                else {
                    p = Stack.pop(); // dodanie wierzcholka do wyniku
                    result.append(p.info.priority).append(" - ").append(p.info.name).append(" ").append(p.info.surname).append(", ");
                    p = p.right; // przejscie w prawo
                }
            }
            result.setLength(result.length() - 2); // usuwamy ostatni przecinek i spacje
            System.out.println(result); // wypisujemy wynik
        }
        // tworzenie przegladu postorder dla drzewa (najpierw lewe poddrzewo, prawe poddrzewo, korzen)
        // poczawszy od korzenia sprawdzamy czy mozemy isc w lewo, nastepnie czy mozemy isc w prawo wzgledem wezla w
        // ktorym sie aktualnie znajdujemy, gdy wezel ma dwoje potomkow to najpierw rozpatrujemy prawe poddrzewo,
        // nastepnie lewe (odwrotnie niz nakazuje konwencja, ale to dlatego ze wyniki zostaja umieszczone na stacku)
        public static void postorder () {
            StringBuilder result = new StringBuilder("POSTORDER: "); // String wynikowy
            nodeStack stack = new nodeStack(); // stos pusty
            nodeStack stackRes = new nodeStack(); // stos pusty- algorytm sporzadza odwrocona liste postorder, stos
            // sluzy zapamietaniu elementow w odwrotnej kolejnosci, a charakterystyka FILO pozwoli nam na uzyskanie
            // wlasciwej kolejnosci po prostu robiac pop(), az stos bedzie pusty
            node p = root; // zaczynamy od wierzcholka
            stack.push(p); // umieszczamy go na stosie
            while (!stack.empty()) { // dopoki stos nie bedzie pusty (nie rozpatrzymy wszystkich elementow)
                p = stack.pop(); //usuwamy wierzcholek stosu
                stackRes.push(p); // dodajemy do stosu wynikowego
                if (p.left != null) // jesli lewe poddrzewo istnieje to umieszczamy je na stosie
                    stack.push(p.left);
                if (p.right != null) // jesli prawe poddrzewo istnieje to umieszcamy je na stosie
                    // bedzie ono rozparzone pierwsze wzgledem lewego poddrzewa- przeciwnie z idea przegladu postorder,
                    // ale zapisujemy wyniki w odwrotnej kolejnosci - od konca
                    stack.push(p.right);
            } // takie przejscie pozwoli nam na rozpatrzenie wszystkich elementow (poczawszy od korzenia zawsze
            // sprawdzamy czy mozna isc w prawo lub lewo sprawdzajac najpierw prawe poddrzewo)
            while (!stackRes.empty()) { // zapisujemy wynik do StringBuildera wynikowego
                p = stackRes.pop(); // usuwajac wierzcholek stosu az stos nie bedzie on pusty
                result.append(p.info.priority).append(" - ").append(p.info.name).append(" ").append(p.info.surname).append(", ");
            }
            result.setLength(result.length() - 2); // usuwamy ostatni przecinek i spacje
            System.out.println(result); // wypisujemy wynik
        }
        // rekurencyjne wyliczanie wysokosci drzewa. p == null warunek konca rekurencji
        // jesli nie zostal osiagniety to dalej wyliczamy maksymalna droge przechodzac do lewego i prawego poddrzewa
        public static int height(node p){
                return p == null ? 0 : Math.max(height(p.left),height(p.right))+1;
        }
        // funkcja szukajaca poprzednika (mniejszej wartosci w hierarchii
        public static node prev(int prio) {
            node p = root; // zaczynamy od korzenia
            node pred = null; // czy znaleziono dany priorytet
            boolean flag = false; // czy znaleziono dany priorytet
            while (p != null) { // przechodzac az do liscia
                if (p.info.priority == prio) { // jesli w ktoryms momencie zostanie znaleziony priorytet
                    // dla ktorego szukamy prev to jesli to mozliwe idziemy raz w lewo i dopoki to mozliwe
                    // caly czas w prawo
                    flag = true; // znaleziono dany priorytet
                    if (p.left != null) {
                        pred = p.left; // przejscie w lewo
                        while (pred.right != null) pred = pred.right; // do konca w prawo
                    }
                    return pred; // jesli nie moglismy isc w lewo nawet raz to nastepnik = null
                }
                else if (p.info.priority < prio) {  // jesli priotrytet sprawdzanego wezla
                    // jest mniejszy idziemy w prawo zapamietujac obecnego poprzednika
                    pred = p;
                    p = p.right;
                }
                else p = p.left; // jesli priorytet jest >= idziemy w lewo
            }
            if (flag) return pred; // jesli idac do liscia kiedys napotkalismy na zadany priorytet to funkcja zwroci
                // poprzednika o ile taki istnieje
            else return null; // nie znaleziono priorytetu dla ktorego szukamy poprzednika
        }
        // funkcja szukajaca nastepnika
        public static node next(int prio) {
            node p = root; // zaczynamy od korzenia
            node succ = null; // nastepnik
            boolean flag = false; // czy znaleziono dany priorytet
            while (p != null) { // przechodzac az do liscia
                if (p.info.priority == prio) { // jesli w ktoryms momencie zostanie znaleziony priorytet
                    // dla ktorego szukamy next to jesli to mozliwe idziemy raz w prawo i dopoki to mozliwe
                    // caly czas w lewo
                    flag = true; // znaleziono dany priorytet
                    if (p.right != null) {
                        succ = p.right; // przejscie w prawo
                        while (succ.left != null) succ = succ.left; // do konca w lewo
                    }
                    return succ; // jesli nie moglismy isc w prawo nawet raz to nastepnik = null
                }
                else if (p.info.priority < prio)  p = p.right;  // jesli priotrytet sprawdzanego wezla
                    // jest mniejszy idziemy w prawo
                else { // jesli priorytet jest >= idziemy w lewo zapamietujac obecnego nastepnika
                    succ = p;
                    p = p.left;
                }
            }
            if (flag) return succ; // jesli idac do liscia kiedys napotkalismy na zadany priorytet to funkcja zwroci
            // nastepnika o ile taki istnieje
            else  return null; // nie znaleziono priorytetu dla ktorego szukamy nastepnika
        }
        // tworzenie drzewa binarnego na podstawie tabeli PREORDER
        public static node createPRE(Person [] people, int begin, int end){
            if (index > people.length - 1) {return null;} // warunek granicy rozpatrywanej tablicy
            int prio = people[index].priority; // sprawdzany priorytet
            if (prio < begin || prio > end) { // czy znajduje sie w granicach wyznaczonego przedzialu
                return null;
            }
            // priorytet zawiera sie w przedziale
            node p = new node(people[index]); // stworzenie nowego wezla o zadanym priorytecie
            index++; // inkrementacja indeksu dla kolejnej rekurencji
            p.left = createPRE(people, begin, prio - 1); // tworzenie lewego poddrzewa - wszystkie
            // elementy lewego poddrzewa beda nalezec do przedzialu [obecny poczatek; obecny priorytet -1]
            p.right = createPRE(people, prio + 1, end ); // tworzenie prawego poddrzewa - wszystkie
            // elementy prawego poddrzewa beda nalezec do przedzialu [obecny priorytet + 1; obecny koniec]
            return p; // zwrocenie wezla
        }
        // tworzenie drzewa binarnego na podstawie tabeli POSTORDER
        public static node createPOST(Person [] people, int begin, int end){
            if (index < 0) {return null;} // warunek granicy rozpatrywanej tablicy
            int prio = people[index].priority; // sprawdzany priorytet
            if (prio < begin || prio > end) { // czy znajduje sie w granicach wyznaczonego przedzialu
                return null;
            }
            // priorytet zawiera sie w przedziale
            node p = new node(people[index]); // stworzenie nowego wezla o zadanym priorytecie
            index--; // dekrementacja indeksu dla kolejnej rekurencji
            p.right = createPOST( people, prio + 1, end); // tworzenie prawego poddrzewa - wszystkie
            // elementy prawego poddrzewa beda nalezec do przedzialu [obecny priorytet + 1; obecny koniec]
            p.left = createPOST( people, begin, prio - 1); // tworzenie lewego poddrzewa - wszystkie
            // elementy lewego poddrzewa beda nalezec do przedzialu [obecny poczatek; obecny priorytet -1]
            return p; // zwrocenie wezla
        }
    }
    // klasa Person
        static class Person {
            public int priority; // priorytet
            public String name; // imie
            public String surname; // nazwisko

            public Person(int priority, String name, String surname) { // konstruktor
                this.priority = priority;
                this.name = name;
                this.surname = surname;
            }

        }
    public static int index; // indeks rozpatrywanego elementu w metodach createPOST i createPRE
    public static Scanner inp = new Scanner(System.in);
    public static void main(String[] args) {
        int loop = inp.nextInt(); // ilosc zestawow danych
        for (int i = 0; i < loop; i ++ ) {
            System.out.println("ZESTAW" + " " + (i + 1) );
            int length = inp.nextInt(); // dlugosc tablicy elementow
            Tree BST = new Tree(); // inicjalizacja drzewa
            for (int k = 0; k < length; k++) {
                String query = inp.next(); // wczytanie zapytania
                if (query.equals("ENQUE")) {
                    // wczytanie parametrow
                    int priority = inp.nextInt();
                    String name = inp.next();
                    String surname = inp.next();
                    Person person = new Person(priority,name,surname);
                    BST.enque(person);
                } else if (query.equals("DEQUEMAX")) {
                    Person back = BST.dequemax().info;
                    System.out.println(query+ ": " + back.priority + " - " + back.name + " " + back.surname);
                } else if (query.equals("DEQUEMIN")) {
                    Person back = BST.dequemin().info;
                    System.out.println(query + ": " + back.priority + " - " + back.name + " " + back.surname);
                } else if (query.equals("NEXT")) {
                    int prio = inp.nextInt(); // wczytanie parametru
                    node back = BST.next(prio);
                    if (back == null) System.out.println("NEXT " + prio + ": BRAK");
                    else System.out.println(query + " " + prio + ": " + back.info.priority + " - " + back.info.name + " " + back.info.surname);
                } else if (query.equals("PREV")) {
                    int prio = inp.nextInt(); // wczytanie parametru
                    node back = BST.prev(prio);
                    if (back == null) System.out.println("PREV " + prio + ": BRAK");
                    else System.out.println(query + " " + prio + ": " + back.info.priority + " - " + back.info.name + " " + back.info.surname);
                } else if (query.equals("CREATE")) {
                    // wczytanie parametrow
                    String order = inp.next();
                    int quant = inp.nextInt();
                    Person[] people = new Person[quant]; // inicjalizacja tabeli wczytywania osob
                    for (int j = 0; j < quant; j++) { // wczytanie danych o osobach
                        int priority = inp.nextInt();
                        String name = inp.next();
                        String surname = inp.next();
                        Person a = new Person(priority, name, surname);
                        people[j] = a;
                    }
                    // tworzenie z przeszukania preorder
                    if (order.equals("PREORDER")) {
                        index = 0; // tworzenie na podstawie preorder zaczyna sie od poczatku tablicy
                        BST.root = BST.createPRE( people, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                    // tworzenie z przeszukania postorder
                    else {
                        index = people.length - 1; // tworzenie na podstawie postorder zaczyna sie od konca tablicy
                        BST.root = BST.createPOST(people, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                } else if (query.equals("DELETE")) {
                    int priority = inp.nextInt(); // wczytanie parametru
                    boolean check = BST.delete(priority);
                    if (check); // jesli wezel o takim priorytecie istnial
                    else System.out.println(query + " " +  priority + ": BRAK"); // jesli nie bylo takiego wezla
                } else if (query.equals("PREORDER")) {
                    BST.preorder();
                } else if (query.equals("INORDER")) {
                    BST.inorder();
                } else if (query.equals("POSTORDER")) {
                    BST.postorder();
                } else { // height
                    System.out.println(query + ": " + (BST.height(BST.root)-1));
                }
            }
        }
    }
}

/*
test0.in
3
25
CREATE POSTORDER 10 10 a a 9 b b 8 c c 7 d d 6 e e 5 f f 4 g g 3 h h 2 i i 1 j j
INORDER
PREORDER
POSTORDER
DEQUEMAX
INORDER
DEQUEMIN
INORDER
DEQUEMAX
INORDER
DEQUEMIN
INORDER
DEQUEMAX
INORDER
DEQUEMIN
INORDER
DEQUEMAX
INORDER
DEQUEMIN
INORDER
DEQUEMAX
INORDER
DEQUEMIN
ENQUE 100 z z
HEIGHT
19
CREATE POSTORDER 10 10 a a 9 b b 8 c c 7 d d 6 e e 5 f f 4 g g 3 h h 2 i i 1 j j
INORDER
NEXT 0
NEXT 11
PREV 0
PREV 11
NEXT 1
NEXT 5
NEXT 10
PREV 1
PREV 5
PREV 10
DELETE 0
DELETE 11
DELETE 1
DELETE 5
DELETE 10
INORDER
HEIGHT
8
CREATE PREORDER 5 10 a a 9 b b 8 c c 7 d d 6 e e
ENQUE 5 f f
ENQUE 4 g g
ENQUE 3 h h
ENQUE 2 i i
ENQUE 1 j j
INORDER
HEIGHT

test0.out
ZESTAW 1
INORDER: 1 - j j, 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c, 9 - b b, 10 - a a
PREORDER: 1 - j j, 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c, 9 - b b, 10 - a a
POSTORDER: 10 - a a, 9 - b b, 8 - c c, 7 - d d, 6 - e e, 5 - f f, 4 - g g, 3 - h h, 2 - i i, 1 - j j
DEQUEMAX: 10 - a a
INORDER: 1 - j j, 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c, 9 - b b
DEQUEMIN: 1 - j j
INORDER: 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c, 9 - b b
DEQUEMAX: 9 - b b
INORDER: 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c
DEQUEMIN: 2 - i i
INORDER: 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c
DEQUEMAX: 8 - c c
INORDER: 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d
DEQUEMIN: 3 - h h
INORDER: 4 - g g, 5 - f f, 6 - e e, 7 - d d
DEQUEMAX: 7 - d d
INORDER: 4 - g g, 5 - f f, 6 - e e
DEQUEMIN: 4 - g g
INORDER: 5 - f f, 6 - e e
DEQUEMAX: 6 - e e
INORDER: 5 - f f
DEQUEMIN: 5 - f f
HEIGHT: 0
ZESTAW 2
INORDER: 1 - j j, 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c, 9 - b b, 10 - a a
NEXT 0: BRAK
NEXT 11: BRAK
PREV 0: BRAK
PREV 11: BRAK
NEXT 1: 2 - i i
NEXT 5: 6 - e e
NEXT 10: BRAK
PREV 1: BRAK
PREV 5: 4 - g g
PREV 10: 9 - b b
DELETE 0: BRAK
DELETE 11: BRAK
INORDER: 2 - i i, 3 - h h, 4 - g g, 6 - e e, 7 - d d, 8 - c c, 9 - b b
HEIGHT: 6
ZESTAW 3
INORDER: 1 - j j, 2 - i i, 3 - h h, 4 - g g, 5 - f f, 6 - e e, 7 - d d, 8 - c c, 9 - b b, 10 - a a
HEIGHT: 9

 */