//Szymon Szrajer - 8

import java.util.Scanner;

/*
    Dzialanie obu metod opiera sie na algorytmie przedstawionym na wykladzie:
    (1) Jesli w jakimkolwiek momencie realizacji procesu pakowania suma wag wybranych
    elementow bedzie rowna wadze docelowej, nalezy zakonczyc dzialanie.
    (2) Poczatkowo wybierany jest pierwszy element. Po jego wybraniu, wyznaczamy nowa
    wage docelowa, jako roznice dotychczasowej wagi docelowej i wagi wybranego
    elementu. Jesli suma wag wybranych elementow nie bedzie rowna wadze docelowej,
    nalezy wybrac nastepny element.
    (3) Kolejno nalezy wyprobowac wszystkie dostepne kombinacje pozostalych elementow. Przy
    czym nalezy zauwazyc, ze w rzeczywistosci wcale nie trzeba sprawdzac wszystkich
    kombinacji gdyz sumowanie mozna zakonczyc w momencie, gdy sumaryczna waga
    wybranych elementow przekracza wage docelowa.
    (4) Jesli nie uda sie znalezc kombinacji elementow o zadanej wadze, to nalezy odrzucic pierwszy
    element i rozpoczac caly proces od poczatku, wybierajac element kolejny.
    (5) W podobny sposob nalezy rozpoczac caly proces sprawdzania, wybierajac na poczatku
    kolejne elementy, az do momentu przeanalizowania calego zbioru dostepnych elementow.
    Sprawdzenie wszystkich mozliwosci bedzie oznaczac, ze poszukiwane rozwiazanie nie
    istnieje.
    Dla metody rekurencyjnej wykorzystano tablice boolean w celu zapamietania ineksow wykorzystanych elementow.
    Dla metody iteracyjnej zaimplementowano stos dla indeksow tych elementow, tak zeby po odrzuceniu danej kombinacji
    moc wrocic do indeksu elementu, ktory umozliwi iteracje po tablicy od nastepnego elementu i rozpatrzenie
    nowych kombinacji.
 */


class Source {

    // inicjalizacja Scannera
    public static Scanner inp = new Scanner(System.in);
    // implementacja stosu uzywana w poprzednich projektach
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
//------------------------------------------------------------------------
        // metoda wypisywania ze stosu w metodzie iteracyjnej
        public void displayI() {
            StringBuilder result = new StringBuilder();
            while (!empty()) {
                result.insert(0,array[pop()]).insert(0," ");
            }
            result.insert(0,"ITER: " + sum + " =");
            System.out.println(result);
        }

        // czyszczenie stosu przed kolejnym zestawem danych
        public void clear() {
            while (!empty()) {
                pop();
            }
        }
    }

    // metoda wypisywania ze stosu w metodzie rekurencyjnej
    public static void displayR() {
        StringBuilder result = new StringBuilder("REC:  " + sum + " = ");
        for  (int i = 0; i < array.length; i++) {
            if(resultArray[i] == true) {
                result.append(array[i]).append(" ");
            }
        }
        System.out.println(result);
    }

    public static int currsum = 0; // przechowywanie aktualnej sumy
    public static int sum = 0;
    public static int numOfElem = 0; // przechowywanie ilosci elementow
    public static intStack ITERix = new intStack(); // stos do metory iteracyjnej przechowujacy indeksy
    public static boolean[] resultArray; // tablica zaznaczania indeksow wybranych elementow
    public static int[] array; // tablica indeksow wybranych elementow

    public static boolean rec_pakuj(int start) { // metoda rekurencyjna
        if (sum == currsum) { // suma zostala uzyskana - koniec dzialania
            return true;
        } else if (currsum > sum) { // jesli tym wywolaniem przekroczylismy sume docelowa to nie znajdziemy
            // nim sumy docelowej i nalezy zwrocic false
            return false;
        } else if (start == numOfElem) { // nie ma juz wiecej elementow do rozpatrzenia w konkretnym wywolaniu
            return false;
        } else {
            // zapisanie nowego elementu
            resultArray[start] = true;
            currsum += array[start];
            if (rec_pakuj(start + 1)) return true; // sprawdzenie sumy
            else { // jesli suma nie zostala osiagnieta to usuniety zostaje element
                // i dodany nastepny w kolejnosci kombinacji
                currsum -= array[start];
                resultArray[start] = false;
                if(rec_pakuj(start + 1))return true; // sprawdzanie sumy dalej
            }
        }
        return false;
    }


    public static void iter_pakuj (int [] array, int capacity) { // metoda iteracyjna
        int i = 0; // indeks rozpatrywanego elementu
        int limit = numOfElem - 1; // ostatni mozliwy indeks
        while (i < numOfElem) { // dopoki nie zostana rozpatrzone wszystkie elementy
            capacity -= array[i]; // zmniejszanie pozostalej sumy docelowej
            // zapamietanie elementu
            ITERix.push(i);
            if ( capacity == 0 ) { // suma zostala uzyskana
                return;
            } else if ( i == limit ) { // osiagnieto ostatni element w tablicy- nalezy usunac 2 gorne elementy ze
                // stosu i zaczac od kolejnego elementu co stanie sie dzieki ostatniej linijce tej metody
                // 1 element
                capacity += array[i];
                ITERix.pop();
                // 2 element
                i = ITERix.pop();
                capacity += array[i];
            } else if ( capacity < 0 ) { // jesli suma zostala przekroczona to nalezy pominac element i przejsc dalej
                capacity += array[i];
                ITERix.pop();
            }
            i++;
        }
    }

    public static void main(String[] args) {
        // wczytanie ilosci zestawow danych
        int loop = inp.nextInt();
        int desiredCapacity = 0; // inicjalizacja wagi docelowej
        int start = 0; // indeks startowy
        for (int i = 0; i < loop; i++) { // wczytanie danych
            desiredCapacity = inp.nextInt();
            numOfElem = inp.nextInt();
            array = new int[numOfElem]; // tablica elementow
            resultArray = new boolean[numOfElem]; // tablica zaznaczania indeksow wybranych elementow
            int b = 0;
                for (int j = 0; j < numOfElem; j++) {
                    int a = inp.nextInt();
                    if (a <= desiredCapacity) { // jesli elemnt jest wiekszy od wagi docelowej zostaje pominiety
                        array[b]  = a;
                        b++;
                    }
                }
                numOfElem = b; // jest tyle elementow ile z nich jest mniejsza od wagi docelowej
                sum  = desiredCapacity; // ustawienie sum docelowych dla kazdej z metod
                if (rec_pakuj (start)) { // jesli uda sie znalezc sume pierwsza metoda druga tez zaczyna dzialac
                    displayR(); // wypisanie wyniku dla metody rekurencyjnej
                    iter_pakuj (array, desiredCapacity); // metoda iteracyjna
                    ITERix.displayI(); // wypisanie wyniku dla metody iteracyjnej
                } else {
                    System.out.println("BRAK"); // komunikat o braku wyniku
                }
                // wyczyszczenie obiektow przed nastepna petla
                ITERix.clear();
                currsum = 0;
        }
    }
}

/*
test0.in
3
14
3
7 3 3
9
8
1 1 1 1 1 1 1 7
1000000
12
1 390426 1 11333 1 1 1 349382 1 1 248858 1
test0.out
BRAK
REC:  9 = 1 1 7
ITER: 9 = 1 1 7
REC:  1000000 = 1 390426 11333 349382 248858
ITER: 1000000 = 1 390426 11333 349382 248858
 */