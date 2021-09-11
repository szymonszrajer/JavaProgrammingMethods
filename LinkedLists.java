//Szymon Szrajer - 8
import java.util.Scanner;



/*
 Program operuje na reprezentacji pociagow jako pojedynczej listy jednokierunkowej, ktorej referencja trains wskazuje na
 pierwszy element listy oraz na reprezentacji listy wagonow poszczegolnych pociagow w postaci listy
 dwukierunkowej cyklicznej, ktorej referencja first wskazuje na pierwszye element.

Dzialanie na powyzszej reprezentacji opiera sie na 9 metodach wywolywanych przez uzytkownika oraz jednej dodatkowej
sluzacej do tworzenia pociagow z poziomu metod:

    1. fNew T1 W - tworzy nowy pociag o nazwie T1 z jednym wagonem o nazwie W
 i wstawia go do listy pociagow.
    2. fInsertFirst T1 W - wstawia wagon o nazwie W na poczatek pociagu o nazwie T1
    3. fInsertLast T1 W - wstawia wagon o nazwie W na koniec pociagu o nazwie T1
    4. fDisplay T1 - wypisuje liste wagonow pociagu o nazwie T1 poczawszy od pierwszego
 wagonu
    5. Trains - wypisuje aktualna liste pociagow, bioracych udzial w zabawie.
    6. Reverse T1 - odwraca kolejnosc wagonow w pociagu o nazwie T1
    7. Union T1 T2 - dolacza pociag o nazwie o nazwie T2 na koniec pociagu o nazwie
 T1 i usuwa pociag T2 z listy pociagow
    8. DelFirst T1 T2 - usuwa pierwszy wagon z pociagu o nazwie T1 i tworzy z niego
 nowy pociag o nazwie T2 i jesli to byl jedyny wagon w T1 to T1
 przestaje istniec (jest usuwany z listy pociagow).
    9. DelLast T1 T2 - usuwa ostatni wagon z pociagu o nazwie T1 i tworzy z niego
 nowy pociagu o nazwie T2, przy czym, jesli to byl
    10. FFNew tworzy nowy pociag o nazwie T1 z jednym wagonem o nazwie W
 i wstawia go do listy pociagow- bez przeszukiwania listy.

    Nie zaimplementowano osobnej metody w celu szukania odpowiednich pociagow- odbywa sie ono bezposrednio wewnatrz
    metody i opatrzone jest komentarzem //Szukanie pociagu/(ow)-----------

    Wszystkie metody z wyjatkiem fDisplay oraz fTrains dzialaja w czasie O(1) z wyjatkiem wyszukiwania pociagow w
    kazdej z metod poza FFNew
 */



class Source {

    // inicjalizacja Scannera
    public static Scanner inp = new Scanner(System.in);

    // klasa elementu listy dwukierunkowej - wagon
    public static class Node2 {

        public String name;  // nazwa wagonu
        public Node2 next;   // referencja do nastepnego wagonu
        public Node2 prev;   // referencja do poprzedniego wagonu
        public Node2(String wagon) { // konstruktor
            this.name = wagon;
        }
    }

    // klasa elementu listy jednokierunkowej - pociagu
    public static class Node {

        public String name;
        public Node next;
        public Node2 first;
        public Node(String train) {
            this.name = train;
        }
    }

    // klasa listy pociagow
    public static class linkedList {

        // referencja do pierwszego elementu
        public Node trains;

        // konstruktor
        public linkedList() {
            trains = null;
        }

        // dodanie nowego pociagu z przeszukaniem listy pociagow
        public void fNew(String Train, String Wagon) {
            if (trains == null) {  // jesli nie ma jeszcze pociagu
                Node ntrain = new Node(Train);
                ntrain.next = trains;
                trains = ntrain;
                Node2 nwagon = new Node2(Wagon);
                ntrain.first = nwagon;
            } else {
                // jesli sa juz pociagi na liscie to sprawdzane jest czy nazwa dodawanego pociagu nie wystepuje
                // juz wsrod istniejacych
                //Szukanie pociagu----------- poczatek
                Node search = trains;
                while (search != null && !(search.name.equals(Train)))
                    search = search.next;
                //Szukanie pociagu----------- koniec
                if (search == null) {
                    Node ntrain = new Node(Train); // konstrukcja nowego pociagu
                    ntrain.next = trains;  // ustawienie refernecji nastepnego elementu dla nowgo pociagu
                    trains = ntrain; // przestawienie referencji trains na nowo powstaly pociag
                    Node2 nwagon = new Node2(Wagon); // utworzenie pierwszego wagonu
                    ntrain.first = nwagon;  // ustawienie referencji first na wagon
                } else {
                    System.out.println("Train " + Train + " already exists"); // gdy pociag juz istnieje
                }
            }
        }

        // dodanie nowego pociagu bez przeszukania listy pociagow
        public void ffNew(String Train, String Wagon) {
            Node ntrain = new Node(Train); // konstrukcja nowego pociagu
            ntrain.next = trains;  // ustawienie refernecji nastepnego elementu dla nowgo pociagu
            trains = ntrain; // przestawienie referencji trains na nowo powstaly pociag
            Node2 nwagon = new Node2(Wagon); // utworzenie pierwszego wagonu
            ntrain.first = nwagon;  // ustawienie referencji first na wagon
        }

        // dodanie wagonu w na poczatek listy wagonow pociagu
        public void fInsertFirst(String Train, String Wagon) {
            if (trains == null) { // sprawdzenie czy sa pociagi na liscie
                System.out.println("Train " + Train + " does not exist");
            } else {
                //Szukanie pociagu----------- poczatek
                Node search = trains;
                while (search != null && !(search.name.equals(Train)))
                    search = search.next;
                //Szukanie pociagu----------- koniec
                if (search != null) { // jesli znaleziono pociag o takiej nazwie
                    // przestawienie referencji istniejacych wagonow wzgledem dodawanego
                    if (search.first.next == null) { // jesli pociag ma tylko jeden wagon
                        Node2 nwagon = new Node2(Wagon); // inicjalizaca nowego wagonu
                        nwagon.next = nwagon.prev = search.first;
                        search.first.prev = search.first.next = nwagon;
                        search.first = nwagon;
                    } else { // jesli pociag ma wiecej niz jeden wagon
                        Node2 nwagon = new Node2(Wagon); // inicjalizacja nowego wagonu
                        nwagon.next = search.first;
                        nwagon.prev = search.first.prev;
                        search.first.prev.next = nwagon;
                        search.first.prev = nwagon;
                        search.first = nwagon;
                    }
                } else { // gdy pociag nie istnieje
                    System.out.println("Train " + Train + " does not exist");
                }
            }
        }

        // dodanie wagonu w na koniec listy wagonow pociagu
        public void fInsertLast(String Train, String Wagon) {
            if (trains == null) { // jesli nie ma zadnych pociagow
                System.out.println("Train " + Train + " does not exist");
            } else {
                //Szukanie pociagu----------- poczatek
                Node search = trains;
                while (search != null && !(search.name.equals(Train)))
                    search = search.next;
                //Szukanie pociagow----------- koniec
                if (search != null) { // jesli pociag istnieje
                    // przestawienie referencji istniejacych wagonow wzgledem dodawanego
                    if (search.first.next == null) { // jesli pociag ma tylko jeden wagon
                        Node2 nwagon = new Node2(Wagon);
                        nwagon.next = nwagon.prev = search.first;
                        search.first.prev = search.first.next = nwagon;
                    } else { // jesli pociag ma wiecej niz jeden wagon
                        Node2 nwagon = new Node2(Wagon);
                        nwagon.next = search.first;
                        nwagon.prev = search.first.prev;
                        search.first.prev.next = nwagon;
                        search.first.prev = nwagon;
                    }
                } else { // gdy pociag nie istnieje
                    System.out.println("Train " + Train + " does not exist");
                }

            }
        }

        // wypisanie wagonow w odpowiedniej kolejnosci po wczesniejszym upewnieniu sie ze sa one odwrocone poprawnie
        public void fDisplay(String Train) {

            if (trains == null) { // jesli nie ma zadnych pociagow
                System.out.println("Train " + Train + " does not exist");
            } else {
                //Szukanie pociagu----------- poczatek
                Node search = trains;
                while (search != null && !(search.name.equals(Train)))
                    search = search.next;
                //Szukanie pociagu----------- koniec
                if (search != null) { // jesli pociag istnieje
                    if (search.first.next == null) { // gdy pociag ma tylko jeden wagon
                        StringBuilder result = new StringBuilder(search.name + ": " + search.first.name);
                        System.out.println(result);
                    } else { // gdy pociag ma wiecej niz jeden wagon
                        Node2 dispo = search.first; // wezel potrzebny do przejscia po kolejnych elementach
                        StringBuilder result = new StringBuilder(search.name + ": "); // inicjalizacja wyniku
                        if (dispo.next.prev != dispo) { // jesli wagon jest zle podpiety
                            // tymczasowo zapisz referencje do elementu po nastepnym wzgledem sprawdzanego
                            Node2 temp = dispo.next.prev;
                            dispo.next.prev = dispo; // zamien prev nastepnego elementu na obecnie sprawdzany element
                            dispo.next.next = temp; // zamien next nastepnego elementu na zapisany wczesniej temp
                        }
                        result.append(dispo.name); // dopisz nazwe wagonu do wyniku
                        dispo = dispo.next; // przejdz dalej

                        // sprawdzanie wszystkich elementow tak jak pierwszego - szczegolnego
                        while (dispo != search.first) {
                            result.append(" " + dispo.name);
                            if (dispo.next.prev != dispo) {
                                Node2 temp = dispo.next.prev;
                                dispo.next.prev = dispo;
                                dispo.next.next = temp;
                            }
                            dispo = dispo.next;
                        }
                        System.out.println(result); // wypisanie wyniku
                    }
                } else { // gdy pociag nie istnieje
                    System.out.println("Train " + Train + " does not exist");
                }
            }
        }

        // wypisanie listy pociagow
        public void Trains() {
            if (trains == null) { // jesli nie ma zadnych pociagow
                System.out.println("Trains: ");
            } else { // jesli przynajmniej jeden pociag istnieja
                Node checker = trains;
                StringBuilder result = new StringBuilder("Trains: ");
                while (checker != null) { // dopisywanie kolejnych elementow az do dojscia do konca listy
                    result.append(checker.name);
                    result.append(" ");
                    checker = checker.next;
                }
                System.out.println(result); // wypisanie wyniku
            }
        }

        // odwrocenie pociagu- w praktyce odwrocenie pierwszego i ostatniego elementu tak jakby pociag w calosci byl
        // odwrocony i przesuniecie referencji first na pierwotnie ostatni element
        public void Reverse(String Train) {
            //Szukanie pociagu----------- poczatek
            Node search = trains;
            while (search != null && !(search.name.equals(Train)))
                search = search.next;
            //Szukanie pociagu----------- koniec
            if (search != null) { // jesli pociag istnieje
                if (search.first.next == null) ; // gdy ma tylko jeden wagon - nic
                else if (search.first.prev.prev == search.first) { // gdy ma dwa wagony - zmien first
                    search.first = search.first.next;
                } else { // gdy ma wiecej wagonow odwroc first i prev pierwszego i ostatniego elementu
                    // oraz zmien first na ostani element
                    search.first.prev.next = search.first.prev.prev;
                    search.first.prev.prev = search.first;
                    Node2 temp = search.first.prev;
                    search.first.prev = search.first.next;
                    search.first.next = temp;
                    search.first = temp;
                }
            } else { // gdy pociag nie istnieje
                System.out.println("Train " + Train + " does not exist");
            }
        }

        // przylaczenie pociagu2 na koniec pociagu1 i usuniecie pociagu 2 z listy pociagow
        public void Union(String Train, String Train2) {
            // gdy nazwa pociagu1 == nazwa pociagu2 - pociag juz istnieje
            if (Train.equals(Train2)) {
                System.out.println("Train " + Train2 + " already exists");
            } else {
                if (trains.name.equals(Train)) { // jesli pociag od ktorego przylaczany jest drugi jest 1 na liscie
                    //Szukanie pociagu----------- poczatek
                    Node replaced = trains;
                    while (replaced.next != null && !(replaced.next.name.equals(Train2))) {
                        replaced = replaced.next;
                    }
                    //Szukanie pociagu----------- koniec
                    // jesli pociag2 nie istnieje
                    if (replaced.next == null) {
                        System.out.println("Train " + Train2 + " does not exist");
                    } else {
                        // jesli oba pociagi maja tylko jeden wagon- proste przypisanie wzajemnej referencji
                            if (trains.first.next == null && replaced.next.first.next == null) {
                            trains.first.next = replaced.next.first;
                            trains.first.prev = replaced.next.first;
                            replaced.next.first.next = trains.first;
                            replaced.next.first.prev = trains.first;
                        } // jesli pierwszy pociag ma tylko jeden wagon - podobnie
                        else if (trains.first.next == null) {
                            trains.first.next = replaced.next.first;
                            trains.first.prev = replaced.next.first.prev;
                            replaced.next.first.prev.next = trains.first;
                            replaced.next.first.prev = trains.first;
                        } // jesli drugi pociag ma tylko jeden wagon - podobnie
                        else if (replaced.next.first.next == null) {
                            replaced.next.first.next = trains.first;
                            replaced.next.first.prev = trains.first.prev;
                            trains.first.prev.next = replaced.next.first;
                            trains.first.prev = replaced.next.first;
                        } // jesli oba pociagi maja przynajmniej po dwa wagony
                        else {
                            Node2 temp = replaced.next.first.prev; // zapisanie referencji do ostatniego wagonu pociagu2
                            trains.first.prev.next = replaced.next.first;
                            replaced.next.first.prev.next = trains.first;
                            replaced.next.first.prev = trains.first.prev;
                            trains.first.prev = temp;
                        }
                        replaced.next = replaced.next.next; // usuniecie pociagu 2 z listy pociagow
                    }
                } else if (trains.name.equals(Train2)) { // jesli drugi z pociagow jest pierwszym na liscie
                    //Szukanie pociagu----------- poczatek
                    Node merge = trains;
                    while (merge.next != null && !(merge.next.name.equals(Train))) {
                        merge = merge.next;
                    }
                    //Szukanie pociagu----------- koniec
                    if (merge.next == null) { // jesli pociag1 nie istnieje
                        System.out.println("Train " + Train + " does not exist");
                    } else { // analogicznie jak wyzej
                        if (merge.next.first.next == null && trains.first.next == null) {
                            merge.next.first.next = trains.first;
                            merge.next.first.prev = trains.first;
                            trains.first.next = merge.next.first;
                            trains.first.prev = merge.next.first;
                        } else if (merge.next.first.next == null) {
                            merge.next.first.next = trains.first;
                            merge.next.first.prev = trains.first.prev;
                            trains.first.prev.next = merge.next.first;
                            trains.first.prev = merge.next.first;
                        } else if (trains.first.next == null) {
                            trains.first.next = merge.next.first;
                            trains.first.prev = merge.next.first.prev;
                            merge.next.first.prev.next = trains.first;
                            merge.next.first.prev = trains.first;
                        } else {
                            Node2 temp = trains.first.prev;
                            merge.next.first.prev.next = trains.first;
                            trains.first.prev.next = merge.next.first;
                            trains.first.prev = merge.next.first.prev;
                            merge.next.first.prev = temp;
                        }
                        trains = trains.next; // usuniecie pociagu2 z listy pociagow gdy jest on pierwszym pociagiem
                    }
                } else { // gdy zaden z pociagow nie jest pierwszym na liscie
                    //Szukanie pociagu----------- poczatek
                    Node search = trains; // referencja do szukania pociagow
                    Node merge = null;  // referencja do zapisania pierwszego pociagu
                    Node replaced = null; // referencja do zapisania ostatniego pociagu
                    while (search.next != null) {
                        if (search.next.name.equals(Train)) {
                            merge = search; // przypisanie 1 pociagu gdy zostal znaleziony
                        } else if (search.next.name.equals(Train2)) {
                            replaced = search; // przypisanie 2 pociagu gdy zostal znaleziony
                        }
                        search = search.next;
                    }
                    //Szukanie pociagu----------- koniec
                    if (merge == null) { // gdy pierwszy z pociagow nie zostal znaleziony
                        System.out.println("Train " + Train + " does not exist");
                    }
                    else if (replaced == null) { // gdy drugi z pociagow nie zostal znaleziony
                        System.out.println("Train " + Train2 + " does not exist");
                    } else { // jesli oba pociagi maja tylko jeden wagon- proste przypisanie wzajemnej referencji
                        if (merge.next.first.next == null && replaced.next.first.next == null) {
                            merge.next.first.next = replaced.next.first;
                            merge.next.first.prev = replaced.next.first;
                            replaced.next.first.next = merge.next.first;
                            replaced.next.first.prev = merge.next.first;
                        }  // jesli pociag1 ma tylko jeden wagon- zmiana referencji tego wagonu i wagonu first oraz
                        // first.prev pociagu2
                        else if (merge.next.first.next == null) {
                            merge.next.first.next = replaced.next.first;
                            merge.next.first.prev = replaced.next.first.prev;
                            replaced.next.first.prev.next = merge.next.first;
                            replaced.next.first.prev = merge.next.first;
                        } // jesli pociag2 ma tylko jeden wagon- zmiana referencji tego wagonu i wagonu first oraz
                        // first.prev pociagu1
                        else if (replaced.next.first.next == null) {
                            replaced.next.first.next = merge.next.first;
                            replaced.next.first.prev = merge.next.first.prev;
                            merge.next.first.prev.next = replaced.next.first;
                            merge.next.first.prev = replaced.next.first;
                        } else { // jesli oba pociagi maja przynajmniej po dwa wagony- zmiana referencji wagonow
                            //first oraz first.prev pociagu1 i pociagu2
                            Node2 temp = replaced.next.first.prev;
                            merge.next.first.prev.next = replaced.next.first;
                            replaced.next.first.prev.next = merge.next.first;
                            replaced.next.first.prev = merge.next.first.prev;
                            merge.next.first.prev = temp;
                        }
                        replaced.next = replaced.next.next; // usuniecie pociagu2 z listy
                    }
                }
            }
        }

        // usuniecie pierwszego wagonu z pociagu1 i stworzenie pociagu2 z tego wagonu
        public void DelFirst(String Train, String Train2) {
            if (Train.equals(Train2)) { // jesli nazwy sa tozsame
                System.out.println("Train " + Train2 + " already exists");
            } else {
                    if (trains.name.equals(Train)) { // jesli pociag z ktorego usuwamy jest pierwszym pociagiem
                        //Szukanie pociagu----------- poczatek
                        Node search = trains;
                        boolean check = false; // stworzenie booleanu do sprawdzania czy pociag2 istnieje--
                        // oszczednosc pamieci
                        while (search.next != null) {
                            if (search.next.name.equals(Train2)) {
                                check = true;
                            }
                            search = search.next;
                        }
                        //Szukanie pociagu----------- koniec
                        if (check) { // jesli pociag 2 juz istnieje
                            System.out.println("Train " + Train2 + " already exists");
                        } else { // jesli pociag ma tylko jeden wagon - bedzie usuniety z listy pociagow
                            if (trains.first.next == null) { // jesli ma tylko jeden wagon
                                if (trains.next == null) { // jesli jest jedynym pociagiem na liscie
                                    ffNew(Train2, trains.first.name);
                                    trains.next = null; // przesuniecie trains.next na null
                                } else { // jesli jest wiecej pociagow na liscie
                                    ffNew(Train2, trains.first.name);
                                    trains.next = trains.next.next; // prezesuniecie trains.next na o jeden dalej
                                    // niz usuwany pociag
                                } //  jesli pociag ma tylko dwa wagony - pozostawienie jednego z referencjami null
                            } else if (trains.first.prev.prev == trains.first) {
                                String temp = trains.first.name;
                                trains.first = trains.first.next;
                                trains.first.next = null;
                                trains.first.prev = null;
                                ffNew(Train2, temp);
                            } else { // jesli pociag ma wiecej wagonow
                                String tempo = trains.first.name; // nazwa sluzaca do wstawienia nowego wagonu
                                // po tym jak referencje zostana przestawione
                                Node2 temp = trains.first.prev;
                                if (trains.first.next.next == trains.first) { // jesli pociag po usuwanym ma zle
                                    // ustawione referencje - zostaja one naprawiona
                                    trains.first.next.next = trains.first.next.prev;
                                }
                                trains.first.prev.next = trains.first.next;
                                trains.first = trains.first.next;
                                trains.first.prev = temp;
                                ffNew(Train2, tempo); // nowy pociag
                            }
                        }
                    } else {
                        //Szukanie pociagow----------- poczatek
                    Node search = trains;
                    Node temp2 = null; // zapisywanie referencji do pociagu
                    boolean check = false; // stworzenie booleanu do sprawdzania czy pociag2 juzistnieje--
                    while (search.next != null) {
                        if (search.next.name.equals(Train)) {
                            temp2 = search;
                        }
                        if (search.next.name.equals(Train2)) {
                            check = true;
                        }
                        search = search.next;
                    }
                    search = temp2;
                        //Szukanie pociagow----------- koniec
                    if (search == null) {
                        System.out.println("Train " + Train + " does not exist");
                    } else if (check == true) {
                        System.out.println("Train " + Train2 + " already exists");
                    }
                    // jesli pociag istnieje
                    else {
                        // analogicznie jak wyzej tylko nie operujac na pierwszym pociagu
                        if (search.next.first.next == null) { // jesli pociag ma jeden wagon
                            String temp = search.next.first.name;
                            search.next = search.next.next;
                            ffNew(Train2, temp);
                        } // jesli pociag ma dwa wagony
                        else if (search.next.first.prev.prev == search.next.first) {
                            String temp = search.next.first.name;
                            search.next.first = search.next.first.next;
                            search.next.first.next = null;
                            search.next.first.prev = null;
                            ffNew(Train2, temp);
                        } else { // jesli pociag ma wiecej wagonow
                            String tempo = search.next.first.name;
                            Node2 temp = search.next.first.prev;
                            if (search.next.first.next.next == trains.first) {
                                search.next.first.next.next = search.next.first.next.prev;
                            }
                            search.next.first.prev.next = search.next.first.next;
                            search.next.first = search.next.first.next;
                            search.next.first.prev = temp;
                            ffNew(Train2, tempo);
                        }
                    }
                }
            }
        }

        public void DelLast(String Train, String Train2) {
            if (Train.equals(Train2)) { // jesli nazwy sa tozsame
                System.out.println("Train " + Train2 + " already exists");
            } else {
                if (trains.name.equals(Train)) { // jesli pociag z ktorego usuwamy jest pierwszym pociagiem
                    //Szukanie pociagu----------- poczatek
                    Node search = trains;
                    boolean check = false;   // stworzenie booleanu do sprawdzania czy pociag2 istnieje--
                    // oszczednosc pamieci
                    while (search.next != null) {
                        if (search.next.name.equals(Train2)) {
                            check = true;
                        }
                        search = search.next;
                    }
                    //Szukanie pociagu----------- koniec
                    if (check) { // jezeli pociag2 juz istnieje
                        System.out.println("Train " + Train2 + " already exists");
                    } else { // jesli pociag ma tylko jeden wagon - bedzie usuniety z listy pociagow
                        if (trains.first.next == null) { // jesli ma tylko jeden wagon
                            if (trains.next == null) { // jesli jest jedynym pociagiem na liscie
                                ffNew(Train2, trains.first.name);
                                trains.next = null; // przesuniecie wartosci next dodanego pociagu
                            } else { // jezeli jest wiecej pociagow
                                ffNew(Train2, trains.first.name);
                                trains.next = trains.next.next; // przesuniecie wartosci next dodanego pociagu
                            }
                        } else if (trains.first.prev.prev == trains.first) { // jesli ma dwa agony
                            String temp = trains.first.next.name;
                            trains.first.next = null;
                            trains.first.prev = null;
                            ffNew(Train2, temp);
                        } else { // jesli jest wiecej wagonow
                            String tempo = trains.first.prev.name; // zapisanie nazwy wagonu ktory bedzie stworzony
                            Node2 temp = trains.first.prev.prev;
                            if (temp.prev == trains.first.prev) { // jesli pociag przed usuwanym ma zle
                                // ustawione referencje - zostaja one naprawione
                                temp.prev = temp.next;
                            }
                            trains.first.prev = trains.first.prev.prev;
                            temp.next = trains.first;
                            ffNew(Train2, tempo);
                        }
                    }
                } else {
                    //Szukanie pociagow----------- poczatek
                    Node search = trains;
                    Node temp2 = null;
                    boolean check = false; // stworzenie booleanu do sprawdzania czy pociag2 istnieje--
                    // oszczednosc pamieci
                    while (search.next != null) {
                        if (search.next.name.equals(Train)) {
                            temp2 = search;
                        }
                        if (search.next.name.equals(Train2)) {
                            check = true;
                        }
                        search = search.next;
                    }
                    search = temp2;
                    //Szukanie pociagow----------- koniec
                    if (search == null) { // jesli pociag1 nie istnieje
                        System.out.println("Train " + Train + " does not exist");
                    } else if (check == true) { // jesli pociag2 juz istnieje
                        System.out.println("Train " + Train2 + " already exists");
                    } else { // analogicznie jak wyzej tylko nie operujac na pierwszym pociagu
                        if (search.next.first.next == null) { // jesli ma tylko jeden wagon
                            String temp = search.next.first.name;
                            search.next = search.next.next;
                            ffNew(Train2, temp);
                        } else if (search.next.first.prev.prev == search.next.first) { // jesli ma dwa agony
                            String temp = search.next.first.prev.name;
                            search.next.first.next = null;
                            search.next.first.prev = null;
                            ffNew(Train2, temp);
                        } else { // jesli jest wiecej wagonow
                            String tempo = search.next.first.prev.name;
                            Node2 temp = search.next.first.prev.prev;
                            if (temp.prev == search.next.first.prev) {
                                temp.prev = temp.next;
                            }
                            search.next.first.prev = temp;
                            temp.next = search.next.first;
                            ffNew(Train2, tempo);
                        }
                    }

                }
            }
        }
    }

    public static void main(String[] args) {

        // wczytanie ilosci zestawow danych
        int loop = inp.nextInt();
        int commands = 0;

        for (int i = 0; i < loop; i++) {
            // wczytanie ilosci polecen
            commands = inp.nextInt();
            linkedList tr = new linkedList();

            for (int k = 0; k < commands; k++) {
                String command = inp.next();

                // odeslanie polecen do metod o wczytanych nazwach
                if (command.equals("New")) {
                    String train = inp.next();
                    String wagon = inp.next();
                    tr.fNew(train,wagon);
                } else if (command.equals("InsertFirst")) {
                    String train = inp.next();
                    String wagon = inp.next();
                    tr.fInsertFirst(train,wagon);
                } else if (command.equals("InsertLast")) {
                    String train = inp.next();
                    String wagon = inp.next();
                    tr.fInsertLast(train,wagon);
                } else if (command.equals("Display")) {
                    String train = inp.next();
                    tr.fDisplay(train);
                } else if (command.equals("Trains")) {
                    tr.Trains();
                } else if (command.equals("Reverse")) {
                    String train = inp.next();
                    tr.Reverse(train);
                } else if (command.equals("Union")) {
                    String train = inp.next();
                    String train2 = inp.next();
                    tr.Union(train, train2);
                } else if (command.equals("DelFirst")) {
                    String train = inp.next();
                    String train2 = inp.next();
                    tr.DelFirst(train, train2);
                } else if (command.equals("DelLast")) {
                    String train = inp.next();
                    String train2 = inp.next();
                    tr.DelLast(train, train2);
                }
            }
        }
    }
}

/*
test0.in
3
6
New T1 W1
InsertLast T1 W2
Display T1
Trains
DelFirst T1 T3
Display T1
11
New T1 W1
Display T1
New T1 W1
Display T1
InsertLast T1 W2
Display T1
DelFirst T1 T2
Display T3
Display T2
Display T1
Trains
14
New T2 W2
InsertFirst T2 W2
InsertFirst T2 W3
InsertFirst T2 W4
InsertFirst T2 W5
Reverse T2
Display T2
InsertFirst T2 W6
InsertFirst T3 W3
InsertFirst T2 W7
InsertFirst T2 W8
Reverse T2
Reverse T2
Display T2
test0.out
T1: W1 W2
Trains: T1
T1: W2
T1: W1
Train T1 already exists
T1: W1
T1: W1 W2
Train T3 does not exist
T2: W1
T1: W2
Trains: T2 T1
T2: W2 W2 W3 W4 W5
Train T3 does not exist
T2: W8 W7 W6 W2 W2 W3 W4 W5
 */