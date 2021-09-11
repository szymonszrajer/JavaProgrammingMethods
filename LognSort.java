//Szymon Szrajer - 8

import java.util.Scanner;

/*
    Dzialanie programu opiera sie na jednej metodzie, ktora jednoczesnie szuka najdluzszego wspolnego prefixu
    i tasuje tablice w postaci a1 a2 ... an b1 b2 ... bn do postaci a1 b1 a2 b2 ... an bn.

    Jesli wejsciowa tablica ma nieparzysta liczbe elementow to w liniowym czasie przesuniety zostaje srodkowy
    (nieparzysty) element tablicy na jej koniec- jak rowniez elementy drugiego zbioru tak by reprezentowac
    tablice parzysta. Dla przesunietego elementu szukany jest prefix.

    Sama funkcja rozpatruje 3 przypadki:
    1) Gdy rozpatrywany podzbior tablicy ma 2 elementy.
    2) Gdy polowa rozpatrywanego podzbioru jest parzysta- i podzbior nie ma wylacznie 2 elementow.
    3) Gdy polowa rozpatrywanego podzbioru jest nieparzysta.

    3) - Operowanie na tym przypadku opiera sie na liniowym przesunieciu srodkowego (nieparzystego) elementu na
    przedostatnie miejsce w podzbiorze- tym samym zapewnienie dobrego miejsca dla ostatniego miejsca. Nastepnie
    wywolywana jest ponownie funkcja dla tak poprawionej tablicy (nie uwzgledniajac juz dwoch ostatnich elementow)
    oraz odbywa sie szukanie najdluzszego wspolnego prefixu dla ostatniego i przedostatniego elementu tablicy.

    2) - Operowanie na tym przypadku opiera sie na liniowej zamianie cwierci elementow za polowa podzbioru z
    odpowiadajaca jej cwiercia elementow bezposrednio przed polowa podzbioru. Nastepnie dla powstalych wymieszanych
    polow ponownie wywolywana jest funkcja.

    1) W tym przypadku szukany jest wylacznie najdluzszy wspolny prefix. Odbywa sie to dwukrotnie w liniowym czasie -
    dla porownania prefixow rozpatrywanych elementow i drugi raz dla porownania tego prefixu z ogolnym.

    Zlozonosc pamieciowa wynosi O(1) - nie sa rezerwowane zadne dodatkowe tablice ani listy.

    Zlozonosc dla szukania prefixu (przypadek1)- liniowa- dwukrotne przejscie po dlugosci 3 porownywanych Stringow.
    Zlozonosc dla przypadku2- nastepuje liniowa zamiana polowy elementow- jednej cwiartki wzgledem drugiej i
    dwukrotne wywolanie rekuurencji dla dwoch powstalych podzbiorow.
    Zlozonosc dla przypadku3- nastepuje tylko liniowe przesuniecie blisko polowy elementow o 1 miejsce i ponowne
    wywolanie dla zmniejszonej tablicy.

    Zatem wszystkie przypadki poza drugim maja liniowa zlozonosc czasowa. Dla przypadku 2 zlozonosc mozna wyrazic
    jako: O(n*log n).
    W kazdym kroku gdy dzielimy tablice na pol - dlugosc tablicy mozemy wyrazic za pomoca funkcji logarytmicznej -
    log n, a log n + 1 bedzie stanowic najwieksza mozliwa ilosc pokonanych krokow.
    By wykonac operacje zamian bedzie potrzebny liniowy czas O(n)
    Tym samym caly wszystkie operacje mozna przedstawic jako: n * ( log n + 1 ) co koniec koncow daje zlozonosc O(nlogn)
 */


class Source {

    // metoda sortujaca i szukajaca najdluzszy wspolny prefix
    public static void shuffle_prefix(String[] array, int size, int start) {

        // jesli rozmiar tablicy to 2, szukany jest tylko prefix
        if (size == 2) {
            int len1 = array[start].length(); // dlugosc pierwszego elementu
            int len2 = array[start-1].length(); // dlugosc drugiego elementu
            StringBuilder result = new StringBuilder(""); // StringBuilder do szukania wspolnego prefixu
            // pomiedzy dwoma porownywanymi Stringami w dwuelementowej tablicy
            StringBuilder result2 = new StringBuilder(""); // StringBuilder do szukania wspolnego prefixu
            // pomiedzy wspolnym prefixem z result i ogolnym wspolnym prefixem dla calej tablicy
            len1 = Math.min(len1,len2); // szukanie wspolnego prefixu bedzie sie odbywac wzgledem krotszego Stringa
            int i = 0;
            while (i<len1 && array[start-1].charAt(i) == array[start].charAt(i)) { // szukanie1
                result.append(array[start-1].charAt(i));
                i++;
            }
            if (result == null) { // jesli nie znaleziono zadnego wspolnego prefixu to tym samym ogolny wspolny
                // prefix nie bedzie wystepowal
                prefix = result;
                check = true;
                return;
            }
            else if (check == false) { // jesli jeszcze nie szukano prefixu to ogolny prefix bedzie szukanym prefixem
                prefix = result;
                check = true;
                return;
            }
            else if (prefix != null) { // jesli byl juz szukany prefix i nie jest pusty to szukany jest nowy
                // w przeciwnym wypadku nic sie nie zmienia
                len1 = Math.min(prefix.length(),result.length()); // dlugosc wzgledem ktorej szukany bedzie najdluzszy
                // wspolny prefix to najkrotsza z dwoch mozliwych
                i = 0;
                while (i<len1 && result.charAt(i) == prefix.charAt(i)) { // szukanie2
                    result2.append(result.charAt(i));
                    i++;
                }
                prefix = result2;
                check = true;
                return;
            }
        }

        String temp = ""; // inicjalizacja Stringa tymczasowego
        int halfSize = size/2; // wyznaczenie polowy dlugosci tablicy
        int qtSize = size/4; // wyznaczenie cwierci dlugosci tablicy

        // przypadek2- szczegolowo opiasny wyzej
        if (halfSize % 2 == 0) {
            for ( int i = start; i < start + qtSize; i++) { // "swap" odpowiadajacych sobie elementow w dwoch podzbiorach
                temp = array[i];
                array[i] = array[i-qtSize];
                array[i-qtSize] = temp;
            }
            shuffle_prefix(array,halfSize,start+qtSize); // nowe wywolanie dla pierwszej polowy
            shuffle_prefix(array,halfSize,start-qtSize); // nowe wywolanie dla deugiej polowy
        }

        // przypadek3- szczegolowo opisany wyzej
        else if (halfSize % 2 == 1) {
            temp = array[start-1]; // zapisanie przesuwanego elemntu
            for ( int i = (start-1); i < start-2+halfSize; i++) { // przesuniecie pozostalych zmienianych wzgledem niego
                array[i] = array[i+1];
            }
            array[start+halfSize-2] = temp; // umieszczenie elementu we wlasciwym miejscu
            shuffle_prefix(array,2,start+halfSize-1); // szukanie prefixu dla poprawionych elementow
            shuffle_prefix(array,size-2,start-1); // nowe wywolanie dla zmniejszonej tablicy
        }
    }



    // inicjalizacja Scannera
    public static Scanner inp = new Scanner(System.in);
    public static StringBuilder prefix = new StringBuilder(); // inicjalizacja zmiennej do szukania najdluzszego
    // wspolnego prefixu
    public static boolean check = false; // inicjalizacja flagi potrzebnej do szukania najdluzszego wspolnego prefixu

    public static void main(String[] args) {
        // wczytanie ilosci zestawow danych
        int loop = inp.nextInt();

        for (int i = 0; i < loop; i ++ ) {
            // wczytanie zestawu danych
            int size = inp.nextInt();
            String array[] = new String[size];
            for (int j = 0; j < size; j++) {
                array[j] = inp.next();
            }
            // przypadek dla tablicy jednoelementowej
            if (size == 1) prefix.append(array[0]);
            else if (size % 2 == 1) { // jesli tablica ma nieparzysta liczbe elementow
                String temp = "";
                temp = array[(size-1)/2]; // zapisanie wartosci srodkowego nieparzystego elementu
                for ( int j = ((size-1)/2); j < size-1; j++) { // przesuniecie wartosci pozostalych elementow
                    array[j] = array[j+1];
                }
                array[size-1] = temp; // przypisanie zapisanej wartosci na ostatnie miejsce w tablicy
                shuffle_prefix(array,2,size-1); // szukanie prefixu dla ostatniego elementu tablicy
                shuffle_prefix(array,size-1,size/2); // odeslanie tablicy z parzysta liczba elementow w celu
                // potasowania i znalezienia najdluzszego wspolnego preffixu
            }
            else { shuffle_prefix(array, size, size / 2); } // odeslanie tablicy z parzysta liczba elementow w celu
            // potasowania i znalezienia najdluzszego wspolnego preffixu
            // przepisanie tablicy wynikowej do Stringa
            StringBuilder result = new StringBuilder("");
            for ( String s : array) {
                result.append(s).append(" ");
            }
            System.out.println(result); // wypisanie tablicy wynikowej
            System.out.println(prefix); // wypisanie najdluzszego wspolnego prefixu
            check = false; // zresetowanie flagi
            prefix = new StringBuilder(); // stworzenie nowej zmiennej na prefix
        }
    }
}

/*
test0.in
5
2
a1 A1
17
a1 a2 a3 a4 a5 a6 a7 a8 a9 b1 b2 b3 b4 b5 b6 b7 b8
15
a1 a2 a3 a4 a5 a6 a7 a8 b1 b2 b3 b4 b5 b6 b7
1
a1
18
aAaA1 aAaA2 aAaA3 aAaA4 aAaA5 aAaA6 aAaA7 aAaA8 aAaA9 aAaA10 aAaA11 aAaA12 aAaA13 aAaA14 aAaA15 aAaA16 aAaA17 aAaA18

test0.out
a1 A1

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8

a1
a1

aAaA1 aAaA10 aAaA2 aAaA11 aAaA3 aAaA12 aAaA4 aAaA13 aAaA5 aAaA14 aAaA6 aAaA15 aAaA7 aAaA16 aAaA8 aAaA17 aAaA9 aAaA18
aAaA

 */
