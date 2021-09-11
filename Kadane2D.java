//Szymon Szrajer - 8
import java.util.Scanner;

/*
    Program szuka dla danej niepustej tablicy dwuwymiarowej liczb calkowitych:
    a[0][0], ... ,a[n-1][m-1] dla 0 <= i <= j < n, 0 <= k <= l < m, maksymalnej podtablicy definiowanej jako spojny fragment:
    a[i..j][k..l] o maksymalnej nieujemnej sumie elementow, obliczanej wedlug wzoru:
    ms(i, j, k, l) = 3*D+2*U , gdzie D- suma dodatnich elementow, U= suma ujemnych elementow
    indeksy x, y danego elementu a[x][y] mieszcza sie w ramach  i <= x <= j oraz k <= y <= l.
    Sposob dzialania programu opiera sie na rozpatrzeniu trzech przypadkow:
    1. maksymalna wartosc w tablicy to 0- wypisywana jest suma 0 i wspolrzedna pierwszego zera,
    2. wszystkie elementy tablicy sa mniejsze od zera- maksymalna tablica jest pusta i maksymalna suma wynosi 0,
    3. przynajmniej jeden z elementow jest dodatni- na tablicy wywolana jest metoda szukajaca zadanej przez warunki
    zadania podtablicy, uzywajac algorytmu Kadane
    Sama idea dzialania wspomnianego algorytmu zaklada interpretacje podtablicy identyfikowanej przez dwa z jej
    wierzcholkow lezacych na jednej przekatnej. Kolejne iteracje opieraja sie na zmianie pierwszego wierzcholka
    w ilosci rownej liczbie kolumn oraz zmianie drugiego z wierzcholkow na wszystkie mozliwe sposoby, tak zeby
    jego wspolrzedne byly wieksze od wspolrzednych pierwszego.
    Dla kolejnych zmian kazdego z wierzcholkow liczone sa sumy wierszy- od poczatku podtablicy- determinowanej
    przez wspolrzedne pierwszego wierzcholka, do obecnych wspolrzednych drugiego wierzcholka- wyznaczajacych koniec
    podtablicy. Dla tak wyznaczonych sum przeprowadzany jest algorytm Kadane dla tablicy jednowymiarowej- zakladajacy,
    ze najwieksza mozliwa suma lokalna jest albo wartoscia obecnie rozpatrywanego elementu lub najwiekszej sumy do tej
    pory oraz tego elementu.
 */




class Source {
    // deklaracja Scannera
    public static Scanner inp = new Scanner(System.in);

    // metoda maxSubArr sluzaca do obliczenia maksymalnej sumy dla najmniejszej tablicy w obrebie tablicy 2D
    // przy zachowaniu porzadku leksykograficznego
    // szczegolowy opis dzialania algorytmu opisany jest powyzej i ponizej
    public static void maxSubArr(int[][] inpArray, int num, int row, int col) {

        // inicjalizacja zmiennej przechowujacej wartosc maksymalnej sumy w obrebie tablicy
        int maxSum = -1;
        // inicjalizacja tablicy sluzacej do obliczenia algorytmu kadane dla ttablicy jednowymiarowej, maksymalnej
        // sumy lokalnej oraz obecnej pierwszej wspolrzednej pierwszego wierzcholka (top)
        int[] tempArr = new int[row];
        int maxHere = 0;
        int t = 0;
        // wspolrzedne wierzcholkow podtablicy o maksymalnej sumie, poniewaz dany prostokat mozna jednoznacznie
        // zidentyfikowac na podstawie wspolrzednych dwoch wierzcholkow lezacych na jednej przekatnej  wlasnie
        // w taki sposob sa one identyfikowane w programie
        int maxL = 0; // druga wspolrzedna pierwszego wierzcholka (max left)
        int maxR = 0; // druga wspolrzedna drugiego wierzcholka (max right)
        int maxT = 0; // pierwsza wspolrzedna pierwszego wierzcholka (max top)
        int maxB = 0; // pierwsza wspolrzedna drugiego wierzcholka (max bottom)

        // iteracja po drugiej wspolrzednej pierwszego wierzcholka (left)
        for ( int l = 0; l < col; l++ ) {

            // wyzerowanie wartosci w tablicy pomocniczej
            for (int i = 0; i < row; i++) tempArr[i] = 0;

            // iteracja po drugiej wspolrzednej drugiego wierzcholka (right)
            for ( int r = l; r < col; r++ ) {

                // wyliczanie aktualnej sumy dla tablicy pomocniczej
                for ( int c = 0; c < row; c++ ) {
                    tempArr[c] += inpArray[c][r];
                }

                    // iteracja po pierwszej wspolrzednej drugiego wierzcholka, tym samym algorytm Kadane dla
                    // tablicy pomocniczej
                    for ( int b = 0; b < row; b++) {
                        // obliczenie sumy lokalnej przez dodanie do sumy lokalnej obecnie sprawdzanej wartosci
                        maxHere += tempArr[b];

                        // jesli suma lokalna jest wieksza od sumy maksymalnej, suma lokalna staje sie suma maksymalna
                        // i wpolrzedne rozpatrywanej podtablicy sa zapisywane jako maksymalne
                        if (maxHere > maxSum) {
                            maxSum = maxHere;
                            maxL = l;
                            maxR = r;
                            maxT = t;
                            maxB = b;
                        }

                        // jesli suma jest mniejsza lub rowna zero, to algorytm kadane zaklada wyzerowanie sumy
                        // lokalnej i zmiane pierwszej wspolrzednej pierwszego wierzcholka na jedna wieksza niz
                        // aktualna pierwsza wspolrzedna drugiego wierzcholka
                        if (maxHere <= 0) {
                            maxHere = 0;
                            t = b + 1;
                        }

                        // warunki sluzace do zachowania najmniejszego rozmiaru tablicy i zachowania porzadku
                        // leksykograficznego - pierwszy z warunkow po porownaniu wartosci sum sprawdza pole
                        // aktualnie rozpatrywanego prostokata i porownuje je z polem prostokata o aktualnie
                        // najwiekszej sumie. Drugi z warunkow- gdy rozmiar jest jednakowy sprawdza zachowanie porzadku
                        // leksykograficznego.
                        if (maxHere == maxSum) {
                            if (((r-l + 1)*(b-t + 1)) < ((maxR-maxL + 1)*(maxB-maxT + 1))) {
                                maxL = l;
                                maxR = r;
                                maxT = t;
                                maxB = b;
                            }
                            if ((r-l + 1)*(b-t + 1) == (maxR-maxL + 1)*(maxB-maxT + 1)) {
                                if (t < maxT || b < maxB || l < maxL || r < maxR ) {
                                    maxL = l;
                                    maxR = r;
                                    maxT = t;
                                    maxB = b;
                                }
                            }
                        }
                    }
                // zresetowanie maksymalnej sumy lokalnej i wartosci "gornej" pierwszego wierzcholka
                maxHere = 0;
                t = 0;
            }
        }
        // wypisanie numeru zestawu danych, ilosci wierszy, ilosci kolumn, maksymalnej sumy oraz wspolrzednych
        // znalezionej podtablicy
        System.out.println(num + ": n=" + row +  " m=" + col + ", ms= " + maxSum + ", mstab= a["
                + maxT + ".." + maxB + "][" + maxL + ".." + maxR + "]" );
    }

    public static void main(String [] args ) {

        // wczytanie ilosci zestawu danych
        int loop = inp.nextInt();

        // deklaracja zmiennych sluzacych do wczytywania kolejnych zestawow danych wejsciowych
        int num;  // numer zestawu danych
        int row;  // ilosc wierszy
        int col;  // ilosc kolumn
        char c;   // zmienna typu char pomagajaca pominac dwukropek wystepujacy w dancyh

        // inicjalizacja maksymalnej wartosci w obrebie tablicy- -1, by poprawnie rozwazyc przypadki z samymi liczbami
        // ujemnymi i tymi, w ktorych najwieksza wartosc to zero, inicjalizacja wspolrzednych pierwszego zera
        int maxVal = -1;
        int zeroRow = 0;
        int zeroCol = 0;

        // wczytywanie informacji na temat zestawu danych
        for ( int i = 0; i < loop; i++ ) {
            num = inp.nextInt();
            c = inp.next().charAt(0);
            row = inp.nextInt();
            col = inp.nextInt();
            // inicjalizacja tablicy o wczytaym rozmiarze
            int [][] inpArray =  new int [row][col];
            // zmienna pozwalajaca sprawdzic czy napotkane zero jest pierwsze leksykograficznie w obrabie tablicy
            boolean check = true;

            // wczytanie tablicy
            for ( int j = 0; j < row; j++ ) {
                for ( int k = 0; k < col; k++ ) {
                    inpArray[j][k] = inp.nextInt();
                    // maksymalna suma jest liczona jako suma dodatnich elementow pomnozonych przez 3 oraz ujemnych
                    // elementow pomnozonych przez 2, czynnosci te mozna zrobic juz na etapie wczytywania tablicy
                    if (inpArray[j][k] > 0) inpArray[j][k] *=3;
                    if (inpArray[j][k] < 0) inpArray[j][k] *=2;

                    // sprawdzanie czy wczytywany element nie jest elementem maksymalnym w obrebie tablicy-
                    // potrzebne jest to w przypadku 2
                    if (inpArray[j][k] > maxVal) maxVal = inpArray[j][k];

                    // zapisanie wspolrzednych pierwszego napotkanego zera i zmiana wartosci zmiennej check
                    if (check && inpArray[j][k] == 0) {
                        zeroRow = j;
                        zeroCol = k;
                        check = false;
                    }
                }
            }

            // przypadek 1: maksymalna wartosc w obrebie tablicy to 0- wypisanie numeru zestawu danych, ilosci wierszy,
            // ilosci kolumn, wypisane wspolrzedne dotycza pierwszego napotkanego w tablicy zera
            if (maxVal == 0) {
                System.out.println(num + ": n=" + row +  " m=" + col + ", ms= 0, mstab= a["
                        + zeroRow + ".." + zeroRow + "][" + zeroCol + ".." + zeroCol + "]" );
            }

            // przypadek 2: maksymalna wartosc w tablicy jest mniejsza od zera- wypisanie numeru zestawu danych,
            // ilosci wierszy, ilosci kolumn oraz komunikatu "mstab is empty"
            else if (maxVal == -1) {
                System.out.println(num + ": n=" + row +  " m=" + col + ", ms= 0, mstab is empty");
            }

            // przypadek 3: odeslanie wczytanej tablicy do metody maxSubArr- ktora rozpatruje przypadki z przynajmniej
            // jedna liczba dodatnia w obrebie tablicy
            else {
                maxSubArr(inpArray, num, row, col);
            }

            // zresetowanie wartosci wspolrzednych pierwszego zera oraz maksymalnej wartosci w obrebie tablicy
            // przed przejsciem do nastepnej iteracji
            maxVal = -1;
            zeroRow = 0;
            zeroCol = 0;
        }
    }
}


/*

 test0.in
 10
1 : 2 6
-1 -2 -3  0 -5 0
-9  0 -2 -2 -1 0
2 : 4 4
1 1 -5 -5
1 1 -5 -5
-5 -5 1 1
-5 -5 1 1
3 : 4 4
1 1 -5 -5
1 1 -5 -5
-5 -5 1 5
-5 -5 1 1
4 : 4 4
1 1 -5 -5
1 -1 -5 -5
-5 -5 1 1
-5 -5 1 1
5 : 3 6
 0 -1 -1 4 0 0
 4 -2 -2 0 0 0
 0  0  0 0 0 0
6 : 3 5
 0 0 0 0 0
 0 0 0 0 0
 0 0 0 1 0
7 : 2 5
 2 2 -2 -2 0
 2 2 -2 -2 5
8 : 1 9
-4 7 -4 8 -5 4 -5 -6 29
9 : 2 5
 0  -1 -1 1 -40
 12 -2 -2 1  1
10 : 3 5
 -1 -1 -1 -1 -2
 -1 -1 -1 -1 -1
 -2 -1 -1 -1 -1

 test0.out
1: n=2 m=6, ms= 0, mstab= a[0..0][3..3]
2: n=4 m=4, ms= 12, mstab= a[0..1][0..1]
3: n=4 m=4, ms= 24, mstab= a[2..3][2..3]
4: n=4 m=4, ms= 12, mstab= a[2..3][2..3]
5: n=3 m=6, ms= 12, mstab= a[0..0][3..3]
6: n=3 m=5, ms= 3, mstab= a[2..2][3..3]
7: n=2 m=5, ms= 24, mstab= a[0..1][0..1]
8: n=1 m=9, ms= 104, mstab= a[0..0][1..8]
9: n=2 m=5, ms= 36, mstab= a[1..1][0..0]

 */