//Szymon Szrajer - 8
import java.util.Scanner;

/*
    Ponizszy program stanowi implementacje wyscigu ciagow:
    Dla n ciagow, kazdego o dlugosci m przeprowadzamy wyscig porownujac elementy rozpoczynajac od pierwszego elementu
    w kazdym z ciagow. Najwzsza wartosc elementu wygrywa, a dany ciag wygrywajacy runde w nastepnej rundzie konkuruje
    kolejnym elementem. Jesli podczas porownania nastapi remis - dwa ciagi konkuruja rowna wartoscia, to indeks
    kazdego konkurujacego elementu przesuwa sie o 1 i zaden z ciagow nie dostaje punktu.
    Wyscig trwa m rund, na koncu zostaje wypisana ilosc punktow dla kazdego ciagu.
    Sam algorytm programu przedstawia sie nastepujaco:
    - pierwsze z elementow ciagow zostaja umieszczone w tablicy;
    - dla tablicy zostaje uruchomiona metoda construct(), ktora jest efektywnym sposobem tworzenia kopca- pomijajac
    liscie, dla kazdego elementu przeprowadzana jest metoda downheap() (dla lisci metoda downheap nie moze nic zrobic).
    Takie operowanie na elementach innych niz liscie kolejno sortuje kopiec malejaco "w dol", sortujac poddrzewo kazdego
    elementu i tym samym dochodzac do korzenia i przeprowadzajac dla niego downheap() otrzymujemy posortowany kopiec.
    - po sporzadzeniu kopca dla pierwszych elementow nastepuje wylonienie dwoch najwyzszych elementow w kopcu
    a) jesli elementy sa rownej wartosci oznacza to remis:
        indeksy wszystkich konkurujacych elementow zostaja przesuniete o 1, o ile nie jest to ostatnia runda zostaje
        sporzadzony nowy kopiec.
    b) jesli elementy nie sa rownej wartosci oznacza to wygrana najwyzszego elementu
        zostaje zwiekszony indeks wygrywajacego elementu, wygrywajacy ciag dostaje punkt. O ile nie jest to ostatnia
        runda to do kopca zostaje dodany nowy element z wygrywajacego ciagu.
    - proces zostaje powtorzony dla kolejnych konkurujacych elementow w kazdej z rund.

    metody:
    construct()- podobnie jak opisane wyzej- dla kazdego elementu innego niz liscie zostaje wywolana metoda downheap
    insert()- dopisuje element na koniec kopca i wywoluje metode upheap()
    downheap()- na sciezce od danego elementu w dol zostaja zamieniane kolejne pary poprzednikow i nastepnikow,
    tak dlugo az nie beda sie one znajdowac w poprawnej kolejnosci - poprzednik wiekszy od nastepnika
    upheap()- na sciezce od danego elementu do korzenia (w gore) zostaja zamieniane kolejne pary
    poprzednikow i nastepnikow, tak dlugo az nie beda sie one znajdowac w poprawnej kolejnosci-
    poprzednik wiekszy od nastepnika
    deleteMax()- usuwa najwiekszy element- korzen zamieniajac jego wartosc z ostatnim elementem kopca, a nastepnie
    naprawia warunek kopca wywolujac metode downheap na (nowym) korzeniu
 */

public class Source {
        // klasa elementu kopca
        public static class node {
            public int prio; // priorytet
            public int ix; // indeks w ciagu
            public int series; // ciag z ktorego pochodzi element

            public node(int p, int i, int s) { // konstruktor
                prio = p;
                ix = i;
                series = s;

            }
        }

        // konstrukcja kopca na bazie nieposortowanej tablicy
        public static void construct() {
            for (int i = (n - 1) / 2; i >= 0; i--)
                downheap(i); // metoda downheap dla kazdego elementu niebedacego lisciem
        }

        // wstawianie elemntu na kopiec
        public static void insert(node x) {
            array[n] = x; // dopisanie elementu na koncu
            upheap(n); // sortowanie w gore
            n++; // zwiekszenie ilosci elementow w tablicy
        }

        // przesiewanie w dol
        public static void downheap(int i) {
            node temp = array[i]; // tymczasowe przechowywanie wartosci aktualnego elementu
            int k;
            while (i < n / 2) {
                k = 2 * i + 1; // indeks lewego z nastepnikow
                // wybranie wiekszego nastepnika- jesli prawy nastepnik 1) istnieje 2) jest wiekszy zostanie wybrany
                if (k < n - 1 && array[k].prio < array[k + 1].prio) k++;
                if (temp.prio >= array[k].prio) break; // warunek kopca spelniony
                array[i] = array[k]; // jesli nie jest spelniony to zamiana elementow
                i = k; // i bedzie w kolejnej iteracji nastepnikiem
            }
            array[i] = temp; // gdy petla sie skonczy nalezy jeszcze wstawic element na pozycje i, temp >= array[k] lub
            // i >= n/2
        }

        // przesiewanie w gore
        public static void upheap(int i) {
            node temp = array[i]; // tymczasowe przechowywanie wartosci elementu i
            int k = (i - 1) / 2; // przechowywanie indeksu poprzednika
            while (i > 0 && temp.prio > array[k].prio) { // tak dlugo jak warunek kopca nie jest spelniony
                array[i] = array[k]; // nastepnik przyjmuje wartosc poprzednika
                i = k; // przejscie w gore
                k = (k - 1) / 2; // wyznaczenie poprzednika
            }
            array[i] = temp; // po wyjsciu z petli nalezy jeszcze wstawic temp w miejsce i temp <= array[i] lub jest
            // i jest korzeniem
        }

        // usuwanie najwiekszej wartosci kopca
        public static node deleteMax() {
            node max = array[0]; // wartosc maksymalna znajduje sie w korzeniu
            n--; // ilosc elementow zostaje zmniejszona
            array[0] = array[n]; // zamiana wartosci ostatniego elementu z korzeniem
            downheap(0); // przesiewanie nowego korzenia w dol zeby naprawic warunek kopca jesli zostal zaburzony
            return max;
        }




        public static Scanner inp = new Scanner(System.in);
        public static node[] array; // kopiec
        public static int n; // wielkosc kopca

        public static void main(String[] args) {
            int loop = inp.nextInt(); // ilosc zestawow danych
            for (int i = 0; i < loop; i++) {
                int quant = inp.nextInt(); // ilosc ciagow
                int length = inp.nextInt(); // dlugosc ciagow
                String[] names = new String[quant]; // nazwy ciagow
                int[][] data = new int[quant][length]; // tablica danych
                for (int j = 0; j < quant; j++) { // wczytanie do tablicy danych
                    names[j] = inp.next(); // nazwa ciagu
                    for (int k = 0; k < length; k++) {
                        data[j][k] = inp.nextInt();
                    }
                }
                int[] points = new int[quant]; // tablica rozmiaru jednej kolumny- ilosc punktow
                array = new node[quant]; // inicjalizacja tabeli do konstrukcji kopca
                n = 0; // ilosc elementow kopca
                for (int r = 0; r < quant; r++) {
                    array[r] = new node(data[r][0], 0, r); // wpisanie danych z pierwszej kolumny do
                    // tablicy rywalizujacych elementow
                }
                n = quant; // ilosc elementow to dlugosc kolumny
                construct(); // konstrukcja kopca na bazie tablicy
                // kolejne rundy wyscigow
                for (int d = 0; d < length; d++) {
                    int winner = array[0].prio; // najwyzsza wartosc kopca
                    // druga najwyzsza wartosc
                    int nextWinner = -1; // inicjalizacja na wartosc z poza zakresu
                    if (n == 1); // nie ma nastepnika
                    else if (n == 2)  nextWinner = array[1].prio; // nastepnik to jedyny pozostaly element
                    else nextWinner = Math.max(array[1].prio, array[2].prio); // druga najwyzsza wartosc to najwiekszy
                    // z nastepnikow
                    // jesli powyzsze wartosci sa rowne to wystapil remis
                    if (winner == nextWinner) {
                        if (d != length - 1) { // jesli nie jest to ostatnia runda
                            for (int x = 0; x < quant; x++) {// wpisanie elementow konkurujacych w nastepnej rundzie
                                // do tablicy
                                array[x].ix++; // przesuniecie indeksow
                                array[x].prio = data[array[x].series][array[x].ix]; // przesuniecie wartosci
                            }
                            construct(); // konstrukcja kopca
                        }
                    } else { // brak remisu
                        node winning = deleteMax(); // wygrywajacy element sciagniety ze stosu
                        points[winning.series]++; // zwiekszenie punktow dla wygrywajacego ciagu
                        if (d != length - 1) { // jesli to ostatnia runda to kolejny element
                            // wygranego ciagu nie zostaje umieszczony na kopcu
                            winning.ix++; // zwiekszenie indeksu (kolejny element)
                            winning.prio = data[winning.series][winning.ix]; // przepisanie wartosci kolejnego elementu
                            insert(winning); // wstawienie elementu na kopiec
                        }
                    }
                }
                int p = 0;
                for (String s : names) { // dla kazdego ciagu
                    System.out.println(s + " - " + points[p] + " pkt."); // wypisanie wyniku
                    p++;
                }
            }
        }
    }

/*
test0.in
3
5 5
a 1 2 3 4 5
b 5 4 3 2 1
c 1 1 1 1 1
d 3 3 3 3 3
e 2 2 5 3 4
2 1
a 1
b 2
3 6
a 1 2 3 4 5 6
b 7 8 9 0 11 12
c 1 8 9 4 5 12

test0.out
a - 0 pkt.
b - 2 pkt.
c - 0 pkt.
d - 2 pkt.
e - 0 pkt.
a - 0 pkt.
b - 1 pkt.
a - 0 pkt.
b - 5 pkt.
c - 0 pkt.
 */