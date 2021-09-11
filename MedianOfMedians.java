//Szymon Szrajer - 8
import java.util.Scanner;

/*
    Istote programu stanowi implementacja metody znajdujacej k-ty najmniejszy element w
    nieposortowanej tablicy w pesymistycznym czasie O(n) i przy zlozonosci pamieciowej O(1)
    (pomijajac zlozonosc pamieciowa wywolan rekurencyjnych na stosie).

    W glownej metodzie rekurencyjnej kolejno wykonywane sa nastepujace kroki:
    (1) sprawdzenie czy k-ty najmniejszy element powinien byc szukany i czy znajduje sie w tablicy
    (2) podzial na tablice 5 elementowe i ewentualnie jedna tablice <5 elementowa posortowanie
    kazdej z nich i odpowiednie przesuniecie wyznaczonych median na poczatek tablicy
    (3) wyznaczenie mediany median na podstawie przesunietych wartosci na poczatku tablicy
    (4) podzial calego rozpatrywanego w danym momencie zbioru wzgledem mediany median na
    elementy mniejsze lub rowne i wieksze
    (5) porownanie k-najmniejszego elementu z pozycja mediany i ewentualnie z liczebnoscia
    zbiorow wiekszych i mniejszych lub rownych co do wartosci wzgledem mediany median.
    Wyznaczenie k-najmniejszego elementu, jesli jest rowny pozycji mediany lub odpowiednie
    odeslanie do zbioru mniejszych lub wiekszych elementow ze zmienionym parametrem k w
    przypadku zbioru elementow wiekszych.

    Nie sa tworzone zadne dodatkowe listy, ani tablice- tym samym program ma zlozonosc
    pamieciowa O(1) (zlozonosc pamieciowa stosu rekurencji jest pomijana).

    Jesli chodzi o zlozonosc czasowa to wynosi ona w pesymistycznym przypadku O(n):

     Co najmniej 1/4 elementow jest >= od mediany median.
     Co najmniej 1/4 elementow jest <= od mediany median.
    Wynika to z charakterystycznego podzialu zbiorow na piatki- elementy piatki ktore sa
    mniejsze od mediany, a ktorych mediana jest mniejsza od mediany median beda tym samym
    mniejsze od mediany median. Analogicznie zbiory w ktorych mediana jest wieksza od mediany median.
    Tym samym w kazdorazowo zbior dopelniajacy co najmniej 1/4 zbioru bedzie stanowil najwyzej
    3/4 mocy zbioru.
    Z powyzszych zalozen wynika, ze rownanie rekurencyjne opisujace zlozonosc to:
    T(n) <= c1 * n, dla podwywolan rekurencji - c1-stala
    T(n) <= T(n/5) + T(3/4n) + c2*n, dla najwiekszego zbioru, - c2-stala
    (1/5 + 3/4 = 0,95 < 1)
    T(n/5) szukanie mediany median.
    T(3n/4) - pesymistyczny przypadek szukania w S1 lub S3.
    dzialania wyodrebniajace podzbiory i operujace na 5-elementowych podzbiorach to koszt liniowy.
    Mozna indukcyjnie dowiesc ze powyzszy koszt sprowadza sie do:
    T(n) <= 20*c*n = theta(n), gdzie c = max(c1,c2)
    Zatem pesymistyczna zlozonosc jest liniowa.
 */

public class Source {
    // zamiana kolejnosci dwoch elementow o wskazanych indeksach
    public static void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    // implementacja metody insertion sort
    public static void sort(int begining,int n) {
        if (n != 1) { // pojedynczy element nie jest sortowany
            for (int i = begining + 1; i < n + begining; i++) { // dla kazdego elementu
                int temp = array[i];
                int j = i - 1;
                while (j >= begining && array[j] > temp) { // przesuwaj dopoki jest mniejszy
                    // niz jego poprzednik
                    array[j + 1] = array[j]; // zamiana elementow
                    j--;
                }
                array[j + 1] = temp; // przypisanie porownywanego elementu w odpowiednie miejsce
            }
        }
    }
    // funkcja rekurencyjna select stanowiaca clue calego programu opisana szczegolowo wyzej
    static int select(int begin, int end, int k) {
        if (k > 0 && k <= end - begin + 1) { // dopoki k != 0 i znajduje sie w rozpatrywanej tablicy
            int n = end - begin + 1 ; // ilosc elementow
            int i;
            int start;
            for (i = begin; i < n/5 - begin; i++) { // podzial na tablice 5 elementowe
                start = begin + (i-begin)*5; // indeks poczatku rozpatrywanej podtablicy
                sort(start, 5); // sortowanie kazdej tablicy osobno
                swap(i, 2 + start); // zamiana mediany z odpowiednim miejscem na
                // poczatku tablicy
            }
            i = i - begin; // usuniecie wartosci begin z i w celu sprawdzenia czy trzeba jeszcze
            // posortowac tablice o mniej niz pieciu elementach
            if (i*5 < n) { // jesli taka tablica istnieje
                int b = begin + i * 5; // poczatek tablicy
                sort(b, n%5); // sortowanie poczatek n%5- ilosc elementow
                int add = find(n%5); // szukanie wartosci przesuniecia mediany wzgledem poczatku
                swap(i + begin,b+add); // zamiana mediany z odpowiednim miejscem na poczatku
                i++; // zwiekszenie ineksu poczatku podtablicy median
            }
            int median;
            if (i == 1) median = array[i + begin - 1]; // jesli i==1 to mediana jest pierwszym
            // elementem podtablicy
            else median = select(begin, begin + i - 1, i / 2); // w przypadku gdy i != 1
            // szukanie mediany rekurencyjnie
            int pivot = partition(begin, end, median); // podzaial tablicy wzgledem mediany
            if (pivot == begin + k - 1) return array[pivot]; // element podzialowy jest k-najmniejszym
            // elementem i zostaje zwrocony
            // element podzialowy jest wiekszy niz k- najmniejszy element i nastepuje odeslanie do
            // tablicy mniejszych (z uwagi na mozliwosc powtarzania sie wartosci w tablicy do tablicy
            // elementow mniejszych lub rownych)
            else if (pivot > begin + k - 1) return select( begin, pivot - 1, k);
            // element podzialowy jest mniejszy niz k-najmniejszy element- nastepuje odeslanie do
            // tablicy wiekszych elementow
            else return select(pivot + 1, end, k - pivot + begin - 1);
        }
        return -1; // nie ma k-najmniejszego elementu w tablicy
    }
    public static int find(int i) { // zwrocenie przesuniecia indeksu dla
        // ostatniej tablicy < 5 elementowej
        if (i == 3 || i == 4) return 1; // tablica 3/4 elementy- mediana bedzie
        // elementem poczatkowym +1
        return 0; // tablica 1/2 elementy - mediana to poczatkowy element
    }
    // podzial elementy na wieksze lub mniejsze-rowne wzgledem mediany
    public static int partition(int begining, int end, int pivot) {
        int l = begining - 1;
        int i = begining;
        while (array[i] != pivot) i++; // przesuniecie pivotu na koniec tablicy
        swap(end,i);
        for (i = begining;  i < end; i++) {
            if (array[i] <= pivot) { // jesli element <= wzgledem pivotu laduje w lewej
                // czesci tablicy
                l++;
                swap(l, i);
            }
        }
        l++;
        swap(end,l); // zamiana konca tablicy- pivotu na koniec czesci mniejszej-rownej
        return (l);
    }
    public static int [] array;
    public static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {

        int loop = inp.nextInt(); // ilosc zestawow danych
        int result; // wynik algorytmu
        for (int i = 0; i < loop; i ++ ) {
            int length = inp.nextInt(); // dlugosc tablicy elementow
            array = new int [length];
            for (int j = 0; j < length; j++) array[j] = inp.nextInt(); // wczytanie elementow
            int access = inp.nextInt(); // ilosc zapytan o element
            int [] arr = new int [length];
            for (int j = 0; j < access; j++) {arr[j] = inp.nextInt();} // wczytanie zapytan
            for (int j = 0; j < access; j++) {
                result = select(0,length-1,arr[j]); // szukanie arr[j]- najmniejszego elementu
                if (result == -1) System.out.println(arr[j] + " brak"); // nie znaleziono
                else System.out.println(arr[j] + " " + result); // wypisanie znalezionego wyniku
            }
        }
    }
}

/*
test0.in
4
26
45	25	9	98	85	27	85	53	71	2	69	8	52	8	38	71	48	36	26	44	68	77	70	48	94	25
26
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
26
1 1 1 1 1 6 6 6 6 6 3 3 3 3 3 4 4 4 4 4 5 5 5 5 5 2
1
16
26
1 1 1 1 1 2 3 3 3 3 3 4 4 4 4 4 5 5 5 5 5 6 6 6 6 6
1
18
9
8 2 5 3 7 4 1 6 9
2
7 4
test0.out
1 2
2 8
3 8
4 9
5 25
6 25
7 26
8 27
9 36
10 38
11 44
12 45
13 48
14 48
15 52
16 53
17 68
18 69
19 70
20 71
21 71
22 77
23 85
24 85
25 94
26 98
16 4
18 5
7 7
4 4
 */