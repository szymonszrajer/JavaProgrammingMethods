//Szymon Szrajer - 8
import java.util.Scanner;

/*
    Poszukiwana jest ilosc mozliwych trojkatow stworzonych z wczytanych odcinkow o okreslonej dlugosci.
    Po posortowaniu rosnaco tablicy mozna ustawic indeks pierwszego z odcinkow na poczatku tabeli- i,
    indeks drugiego z odcinkow na przedostatniej wartosci aktualnie rozpatrywanej czesci wlasciwej tabeli- c = n - 1,
    oraz indeks trzeciego z odcinkow na ostatnim elemencie takiej podtabeli- n.
    Nastepnie mozna skorzystac z ponizszych faktow:
    1) jesli idac indeksem drugiego z odcinkow w strone poczatku tabeli dana trojka odcinkow spelnia warunek trojkata,
    to spelniaja go tez wszystkie wartosci indeksow pierwszego odcinka z przedzialu [i:c - 1],
    2) jesli zmniejszenie wartosci drugiego indeksu nie pozwala uformowac trojkata z rozpatrywanych odcinkow, to
    mozna poszukac kolejnych trojkatow zwiekszajac wartosc i, o ile spelnionny jest dalej warunek ( c > i ),
    3) zmniejszanie indesku trzeciego z rozpatrywanych elementow w petli zewnetrznej pozwala na rozpatrzenie
    wszystkich mozliwych trojkatow (wartosci trzeciego odcinka).


    Przedstawiony algorytm opiera sie na przeprowadzonych kolejno czynnosciach:
    1. Wczytywane sa dane wejsciowe, przy czym najwazniejsza z nich jest jednowymiarowa
    tablica danych wczytywana jako ciag liczb naturalnych.
    2. Tablica danych poddawana jest sortowaniu - zastosowano sortowanie babelkowe.
    3. Posortowana tablica danych ulega dzialaniu wlasciwej czesci algorytmu - dwie petle zagniezdzone,
    z ktorych zewnetrzna iteruje po indeksie trzeciego odcinka, a wewnetrzna zmieniajac, odpowiednio wartosci
    indeksow pierwszego i drugiego odcinka tak, by znalezc wszystkie mozliwe trojkaty i przejsc po wartosciach
    efektowna ilosc razy.
    4. Wypisywana jest wlasciwa ilosc mozliwych trojkatow.

    Zlozonosc czasowa wynosi O(n^2), chociaz w praktyce algorytm dziala szybciej niz podobne algorytmy sluzace do
    rozwiazania przedstawionego w zadaniu problemu (ze wzgledu na zmniejszenie wlasciwych iteracji w wewnetrznej
    petli for ===> while).
    Zlozonosc pamieciowa to O(1)- nie jest wymagane dodatkowe miejsce.
 */


class Source {
    // deklaracja Scannera
    public static Scanner inp = new Scanner(System.in);

    // implementacja metody liczacej ilosc mozliwych trojkatow za pomoca algorytmu opisanego powyzej
    public static void TriangleCounter(int[] inpArray, int elem) {

        // inicjalizacja licznika mozliwych trojkatow
        int counter = 0;
        // inicjacja indeksu pierwszego odcinka
        int i = 0;
        // inicjacja indeksu drugiego odcinka
        int c = 0;

        // petla iterujaca po wartosci indeksu trzeciego odcinka- od konca tabeli
        for (int n = elem; n >= 1; n--) {
            // wyzerowanie wartosci indeksu pierwszego odcinka
            i = 0;
            // ustawienie indeksu drugiego odcinka na przedostatni z obecnie rozpatrywannych elementow
            c = n - 1;
            // petla rozpatrujaca trojkaty, tak dlugo jak wartosc indeksu drugiego odcinka jest wieksza od pierwszego
            while (c > i) {
                // sprawdzenie warunku trojkata dla rozpatrywanych odcinkow
                if (inpArray[i] + inpArray[c] > inpArray[n]) {
                    // jesli inpArray[i] + inpArray[c] spelnia warunek trojkata z inpArray[n] to spelniaja
                    // go takze wszystkie indeksy pierwszego odcinka z przedzialu [i:c - 1]
                    counter += c - i;
                    // sprawdzanie czy dalej mozna spelnic warunki dla mniejszego indeksu drugiego odcinka
                    c--;
                }
                // jesli warunek nie jest spelniony dla obecnie rozpatrywanych powyzej indeksow odcinkow
                // to nie bedzie spelniony takze dla mniejszej wartosci indeksu drugiego odcinka-
                // szukanie rozwiazan przez zwiekszenie indeksu pierwszego odcinka
                else i++;
            }
        }
        // wypisanie liczby mozliwych trojkatow
        System.out.println("Num_triangles= " + counter);
    }

    public static void main(String [] args ) {

        // wczytanie ilosci zestawow danych
        short loop = inp.nextShort();
        // zmienna przechwujaca ilosc odcinkow w zestawie danych
        int elem = 0;
        // zmienna potrzebna do tymczasowego zapamietania wartosci w sortowaniu bombelkowym
        int temp = 0;

        for (int i = 0; i < loop; i++) {
            // wczytanie ilosci elementow w zestawie
            elem = inp.nextInt();
            // deklaracja tablicy dla wczytywanego zestawu danych
            int[] inpArray = new int[elem];

            // wczytanie tablicy
            for (int j = 0; j < elem; j++) {
                inpArray[j] = inp.nextInt();
            }

            // implementacja klasycznego sortowania bombelkowego- o zlozonosci czasowej O(n^2)
            // dzialanie algorytmu opiera sie na kolejnym zmienianiu elementow ktore nie sa w rosnacej
            // kolejnosci idac od lewej do prawej strony lewym porownywanym elementem tabeli (n-1) razy
            // oraz wykonujac porownanie z wszystkimi elementami znajdujacymi sie po jego prawej stronie
            // (n - 1 - k ) razy - zmieniajac elementy w zlej kolejnosci.
            for (int k = 0; k < elem - 1; k++) {
                for (int l = 0; l < elem - k - 1; l++) {
                    // sprawdzanie czy porownywane elementy sa w porzadku rosnacym
                    if (inpArray[l] > inpArray[l + 1]) {
                        // przypisanie wartosci l + 1 do zmiennej tymczasowej
                        temp = inpArray[l + 1];
                        // zamaiana kolejnosci elementow
                        inpArray[l + 1] = inpArray[l];
                        inpArray[l] = temp;
                    }
                }
            }

            // odwolanie do metody liczacej ilosc mozliwych trojaktow
            // zmiana wartosci elem tak, by zgadzala sie ona z algorytmem w przedstawionej metodzie
            TriangleCounter(inpArray, elem -1);
        }
    }
}


/*

 test0.in
    10
    4
    1 2 3 4
    5
    1 1 1 1 2
    10
    4 4 5 2 5 4 4 5 4 5
    5
    2 2 2 2 2
    10
    1 3 1 3 1 3 1 3 1 3
    3
    2 2 2
    6
    1 1 1 2 2 2
    6
    3 2 2 2 3 3
    4
    1 5 10 15
    4
    100 330 101 440

 test0.out
    Num_triangles= 1
    Num_triangles= 4
    Num_triangles= 120
    Num_triangles= 10
    Num_triangles= 70
    Num_triangles= 1
    Num_triangles= 11
    Num_triangles= 20
    Num_triangles= 0
    Num_triangles= 0

 */