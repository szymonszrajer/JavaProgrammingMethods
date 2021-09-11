//Szymon Szrajer - 8

import java.util.Locale;
import java.util.Scanner;
import java.text.DecimalFormat;

/*
    Idea dzialania programu opiera sie na implementacji QuickSortu do sortowania tablicy tak dlugo jak dlugosc podzapytan
    jest wieksza od 20. Algorytm rozpatruje 2 przypadki:
    Gdy rozpatrywany fragment tablicy ma wiecej niz 20 elementow: wykonanie metody Partition (wersja Lomuto), zaznaczenie
    prawej granicy liczba ujemna, przesuniecie prawej granicy.
    Gdy rozpatrywany fragment tablicy ma <= 20 elementow: przesuniecie lewej granicy, odnalezienie pierwszej liczby ujemnej-
    prawej granicy, zmiana wartosci prawej granicy na przeciwny znak.
    Po  przejsciu po calej tablicy, przed wykonaniem InsertionSorta sprawdzane jest jeszcze czy ostatnia wartosc w tabeli
    nie stanowi liczby ujemnej.
    Metoda Partition: zaznaczany jest Pivot jako ostatnia wartosc w tabeli- jesli elementy sa mniejsze niz pivot zostaja
    umieszczone przed miejscem w ktorym nastepnie zostanie umieszczony pivot. Zwracany zostaje indeks podzialu.
    InsertionSort- po przeprowadzeniu QuickSorta dla podzapytan wiekszych niz 20, przeprowadzany jest takze InsertionSort,
    aby dokonczyc tasowanie ktore zaczal QuickSort. Kazdy kolejny element w tablicy jest porownywany z poprzednim i zamieniany,
    tak dlugo jak jest od niego wiekszy.
 */

public class Source {
    // podzial w metodzie QuickSort
    public static int Partition(int L, int R, int col) {
            int l = L - 1; // zmienna przechowujaca indeks miejsca wzgledem ktorego bedzie wstawiony nastepny element
            float pivot = array[R][col]; // wartosc dzielacego (ostatniego) elementu
            for ( int i = L;  i < R; i++) { // przejscie po tablicy
                if (array[i][col] <= pivot) { // jesli wartosc <= od wartosci elementu dzielacego
                    l++; // zwiekszenie indeksu dla zamiany elementow
                    float [] temp = array[l]; // zamiana znalezionego <= elementu z koncem akyualnie poprzedzajacej pivot tablicy elementow
                    array[l] = array[i];
                    array[i] = temp;
                }
            }
            // zamiana elementu dzielacego z pierwszym wiekszym od niego elementem wystepujacym po tablicy mniejszych lub rownych elementow
            float [] temp = array[l+1];
            array[l+1] = array[R];
            array[R] = temp;
            return (l+1); // zwrocenie indeksu podzialu
    }
    // metoda InsertionSort
    private static void InsertionSort(int col, int rows) {
        for (int i = 1; i < rows; i++) { // dla kazdego kolejnego elementu
            int j = i - 1; // wyznaczenie indeksu o jeden mniejszego
            float [] temp = array[i]; // zapisany zostaje w zmiennej tymczasowej
            while (j >= 0 && temp[col] < array[j][col] ) { // porownywanie z poprzedzajacym elementem
                array[j + 1] = array[j]; // jesli element jest mniejszy nastepuje zamiana
                j--; // wyznaczenie nowego indeksu poprzedzajacego eleemntu
            }
            array[j + 1] = temp; // przepisanie porownywanego elementu w nowe wynaczone miejsce lub niezmienione
        }
    }
    // metoda QuickSort
    public static void QuickSort (int col, int rows) {  // QuickSort
        int L = 0; // lewa granica
        int R = rows-1; // prawa granica
        if ( R - L < 20) { // jesli wyjsciowa tablica ma < 20 elementow - od razu insertion sort
            InsertionSort(col, rows);
        } else {
            while (R-L >= 20 && R != rows-1) { // dopoki nie osiagnieta zostanie ostatnia tabela <20 elementow i z R na kocu tablicy
                if (R - L >= 20) { // jesli tablica jest wieksza lub rowna 20
                    int q = Partition(L, R, col); // podzial
                    array[R][col] = - array[R][col]; // zaznaczenie ostatniego elementu przed podzialem
                    R = q-1; // zmniejszenie R wzgledem pierwszego podzialu
                } else { // gdy tablica jest mniejsza niz 20
                    L = R + 2; // przesuniecie lewego indeksu
                    R = L;
                    while (R < rows - 2 && array[R][col] > 0) R++; // szukanie pierwszego ujemnego elementu
                    array[R][col] = -array[R][col]; // zmiana znaku znowu na dodatni
                }
            }
            if (array[R][col] < 0) array[R][col] = -array[R][col]; // jesli znak ostatniego elementu nie zostal jeszce zmieniony
            InsertionSort(col,rows); // odwolanie do InsertionSorta
        }
    }

    public static float[][] array; // inicjalizacja globalnej tablicy elementow
    public static DecimalFormat format = new DecimalFormat("0.####"); // inicjalizacja formatu danych


    public static Scanner inp = new Scanner(System.in).useLocale(Locale.GERMAN); // instalacja skanera
    public static void main(String[] args) {
        // wczytanie ilosci zestawow danych
        int loop = inp.nextInt(); // ilosc zestawow danych
        for (int i = 0; i < loop; i ++ ) {
            int cols = inp.nextInt(); // ilosc kolumn
            int rows = inp.nextInt(); // ilosc wierszy
            array = new float[rows][cols];
            String sArray[] = new String[cols]; // naglowek danych
            for (int j = 0; j < cols; j++) {
                sArray[j] = inp.next(); // wczytanie naglowka
            }
            for (int k = 0; k < rows; k++) {
                for(int m = 0; m < cols; m++) {
                    array[k][m] = inp.nextFloat(); // wczytanie danych
                }
            }
            int commands = inp.nextInt(); // wczytanie ilosci polecen
            for (int c = 0; c < commands; c++) {
                String allSingle = inp.next(); // wczytanie parametur <all/single>
                String collname = inp.next(); // wczytanie indeksu kolumny
                int a = 0;
                while (!collname.equals(sArray[a]) && a < cols - 1 ) { // szukanie kolumny w naglowku
                    a++;
                }
                if (collname.equals(sArray[a])) { // kolumna znaleziona
                    QuickSort(a, rows); // odwolanie do sortowania
                    if (allSingle.equals("all")) { // przypadek #1 - parametr all
                        System.out.println("$ " + allSingle + " " + collname); // wypisanie polecenia
                        StringBuilder header = new StringBuilder(); // inicjalizacja naglowka
                        for (String s : sArray) header.append(s).append("\t"); // konstrukcja naglowka
                        System.out.println(header); // wypisanie naglowka
                        for ( float[] f : array ) { // wypisanie tablicy wynikowej
                            StringBuilder res = new StringBuilder();
                            for ( float l : f ) {
                                res.append(format.format(l)).append("\t");
                            }
                            System.out.println(res);
                        }
                    }
                    else { // przypadek #2 - parametr single
                        System.out.println("$ " + allSingle + " " + collname); // wypisanie polecenia
                        System.out.println(sArray[a]); // wypisanie naglowka
                        for ( int b = 0; b < rows; b++) {
                            System.out.println(format.format(array[b][a])); // wypisanie danych
                        }
                    }
                }
                else  { // przypadek #3 - wywolana kolumna nie istnieje
                    System.out.println("$ " + allSingle + " " + collname);
                    System.out.println("invalid column name: " + collname);
                }
            }
        }
    }
}
/*
test0.in
5
10
40
1	2	3	4	5	6	7	8	9	10
118	166	64	178	115	130	71	25	80	21
123	197	45	92	84	111	38	9	151	143
78	56	52	34	113	158	194	53	151	183
103	36	176	12	165	141	86	91	71	20
61	150	198	26	125	83	17	199	88	50
86	72	131	109	97	151	74	111	130	111
29	112	69	74	78	170	68	185	45	180
57	45	145	174	163	144	183	3	181	13
109	59	155	12	87	6	34	31	125	133
189	71	111	159	114	75	44	10	71	103
167	156	155	33	95	75	179	170	167	43
106	113	59	152	148	11	36	132	153	157
35	136	46	121	105	154	198	68	57	118
159	175	156	140	191	183	112	32	102	113
191	189	135	120	100	137	172	26	28	144
162	107	175	88	92	53	196	120	189	130
61	199	3	179	79	44	193	90	20	60
80	167	183	61	81	22	33	50	74	2
171	23	110	94	84	132	82	166	77	143
55	78	149	48	86	160	185	62	189	115
183	25	141	103	122	111	174	106	45	40
139	108	146	87	129	185	94	26	194	164
2	92	196	50	78	180	193	190	153	147
23	7	122	138	9	180	118	110	5	165
77	127	44	178	152	60	152	185	15	137
91	87	86	134	44	200	48	168	18	69
116	188	73	120	92	104	200	125	26	127
21	70	94	106	7	95	197	195	28	122
75	23	172	150	117	18	200	49	189	44
30	68	37	144	24	169	98	177	141	47
198	10	174	186	72	188	96	192	173	50
81	9	31	95	198	53	140	3	49	17
108	71	116	125	170	195	58	43	185	147
116	189	163	28	155	34	176	86	174	7
104	119	151	32	90	64	188	181	38	108
129	50	3	106	200	153	95	84	66	16
112	90	151	79	108	49	114	125	96	25
15	30	139	157	198	150	108	19	73	157
20	33	134	68	83	24	25	40	115	147
68	32	188	177	48	162	164	154	160	93
1
all 2
5
20
a b c d e
14,76 8,98 15,72 12,77 9,46 1,52 13,77 7,72 13,61 12,47 17,92 8,7 15,23 14,75 7,48 19,6 11,41 11,46 7,04 16,57 5,33 13,43 9,12 9,65 16,42 17,99 12,62 9,1 9,37 16,37 10,84 1,25 14,47 18,26 19,43 13,36 10,95 4,5 14,5 8,11 3,98 12,69 6,71 7,82 7,51 12,96 1,36 4,86 6,37 11,59 16,87 1,36 0,73 9,08 11,55 12,43 18,41 0,64 6,63 7,22 12,62 2,83 10,03 19,76 14,41 8,5 12,06 17,46 14,28 10,33 8,72 10,62 10,58 13,25 9,22 13,57 13,11 3,97 11,12 14,6 8,66 17,57 10,16 8,04 18,5 18,67 1,59 12,7 8,4 2,28 13,43 7,66 4 11,32 0,77 8,06 14,58 11,84 14,29 9,08
3
all c
single d
single e
6
50
one	two	three	four	five	six
21	85	22	91	45	55
15	47	80	5	19	3
38	71	29	68	64	22
69	91	14	96	1	45
58	97	75	5	34	33
30	46	51	56	42	55
47	22	40	51	48	28
98	65	23	13	16	17
53	78	57	16	44	51
58	58	12	49	43	78
24	80	14	9	11	43
96	8	47	2	45	47
72	40	64	11	44	4
12	82	91	10	48	97
59	61	38	85	35	17
72	59	93	5	3	99
42	89	37	86	53	6
39	91	2	46	30	63
48	71	79	61	16	95
77	29	40	32	17	30
85	35	19	56	4	54
76	82	4	30	72	95
100	49	43	34	42	87
76	72	79	68	50	11
74	64	100	34	71	68
30	69	18	20	77	71
72	61	43	40	77	15
1	7	50	21	71	52
60	14	45	66	39	42
26	18	87	35	57	5
68	54	98	93	85	42
61	51	64	13	1	91
83	13	53	27	91	84
50	70	29	70	93	36
89	65	18	59	52	56
52	86	70	34	90	60
69	3	85	60	96	16
74	80	27	74	90	70
33	15	28	3	93	37
61	25	14	19	34	73
12	35	70	33	32	53
88	50	38	10	6	25
56	72	4	7	3	42
50	23	38	92	40	24
91	65	39	87	55	55
6	47	63	37	7	62
18	8	16	63	16	1
17	67	6	21	50	100
39	70	35	88	8	96
2	94	26	38	69	86
7
all four
single one
single two
single three
single four
single five
single six
5
4
one	two	three	four	five
65	51	72	94	34
83	91	59	18	79
45	7	19	22	43
84	24	23	1	43
3
all two
single three
single four
4
5
a	b	c	d
112,3	112,3	112,4	112,5
223,4	444,2	442,1	111,4
1,1	2,2	3,3	4,5
4,2	2,24	114,2	122,4
23,4	44,1	55,2	55,2
2
single c
all d
test0.out
-- w poleceniu byla prosba wylacznie o dane wejsciowe--
*/