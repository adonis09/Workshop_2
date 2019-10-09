<img alt="Logo" src="http://coderslab.pl/svg/logo-coderslab.svg" width="400">

# Warsztaty II Java

## Zadanie 1

Przygotowanie
* Przygotuj folder pod aplikację.
* Załóż nowe repozytorium git na GitHubie i nową bazę danych.
* Pamiętaj o robieniu backupów bazy danych (najlepiej co każde ćwiczenie) i tworzeniu commitów
(również co każde ćwiczenie).
* Stwórz plik .gitignore i dodaj do niego wszystkie podstawowe dane: (pliki *.*~, katalog z danymi
twojego IDE, jeżeli istnieje itp.).
* Utwórz klasę zawierającą metodę o sygnaturze:
public static Connection getConnection() throws SQLException
, która będzie zwracała utworzone połączenie z bazą danych.


## Zadanie 2

Podczas ćwiczeń z wykładowcą, stwórz szkielet aplikacji oraz klasy User i UserDao (na podstawie
schematu z prezentacji).
W klasie UserDao wykorzystaj utworzoną wcześniej klasę z metodą zwracającą obiekt klasy
Connection do zestawiania połączenia z bazą danych. Dzięki temu w klasach DAO nie będzie trzeba
powtarzać kodu odpowiedzialnego za zestawianie połączenia z bazą danych.

## Zadanie 3

Stwórz wszystkie tabele w bazie danych potrzebne do działania programu.
Pamiętaj o dodaniu kluczy głównych oraz powiązań między tabelami.

## Zadanie 4

Utwórz implementację pozostałych klas:
* Group,
* Exercise,
* Solution.

Dla każdej klasy utwórz klasę DAO oraz metody:
* create,
* read,
* update,
* delete,
* findAll.

## Zadanie 5

Utwórz implementację dodatkowych metod realizujących zadania:
* pobranie wszystkich rozwiązań danego użytkownika (dopisz metodę findAllByUserId do klasy
Solution),
* pobranie wszystkich rozwiązań danego zadania, posortowanych od najnowszego do najstarszego
(dopisz metodę findAllByExerciseId do klasy Solution),
* pobranie wszystkich członków danej grupy (dopisz metodę findAllByGroupId do klasy User).

# Programy administracyjne

## Program 1 – zarządzanie użytkownikami

Program po uruchomieniu wyświetli na konsoli listę
wszystkich użytkowników.
Następnie wyświetli
"Wybierz jedną z opcji:
* add – dodanie użytkownika,
* edit – edycja użytkownika,
* delete – usunięcie użytkownika,
* quit – zakończenie programu."

Po wpisaniu i zatwierdzeniu jednej z opcji program
odpyta o odpowiednie dane:
* jeśli wybrano add – program zapyta
o wszystkie dane, występujące w klasie User,
oprócz id,
* w przypadku edit – o wszystkie dane
występujące w klasie User oraz id,
* po wybraniu delete – zapyta o id
użytkownika, którego należy usunąć.

Po wykonaniu dowolnej z opcji, program ponownie wyświetli listę danych i zada pytanie o wybór opcji.

## Program 2 – zarządzanie zadaniami

Program po uruchomieniu wyświetli na konsoli listę
wszystkich zadań.
Następnie wyświetli w konsoli napis
"Wybierz jedną z opcji:
* add – dodanie zadania,
* edit – edycja zadania,
* delete – edycja zadania,
* quit – zakończenie programu."

Po wpisaniu i zatwierdzeniu odpowiedniej opcji
program odpyta o następujące dane:
* w przypadku add – o wszystkie dane
występujące w klasie Exercise bez id,
* po wybraniu edit – wszystkie dane
występujące w klasie Exercise oraz id,
* jeśli wybrano delete – zapyta o id zadania
które należy usunąć.

Po wykonaniu dowolnej z opcji, program ponownie wyświetli listę danych i zada pytanie o wybór opcji.

## Program 3 – zarządzanie grupami

Program po uruchomieniu wyświetli na konsoli listę
wszystkich grup. Następnie wyświetli w konsoli napis

"Wybierz jedną z opcji:
* add – dodanie grupy,
* edit – edycja grupy,
* delete – edycja grupy,
* quit – zakończenie programu."

Po wpisaniu i zatwierdzeniu odpowiedniej opcji
program odpyta o dane i wykona odpowiednią
operacje:
* add – wszystkie dane występujące w klasie
Group, bez id,
* edit – wszystkie dane występujące w klasie
Group oraz id,
* delete – id grupy którą należy usunąć.

Po wykonaniu dowolnej z opcji, program ponownie wyświetli listę danych i zada pytanie o wybór opcji.

## Program 4 – przypisywanie zadań

Program po uruchomieniu wyświetli w konsoli
napis
"Wybierz jedną z opcji:
* add – przypisywanie zadań do użytkowników,
* view – przeglądanie rozwiązań danego
użytkownika,
* quit – zakończenie programu."

Po wpisaniu i zatwierdzeniu odpowiedniej opcji
program odpyta o dane:
* jeśli wybrano add – wyświetli listę wszystkich
użytkowników, odpyta o id, następnie
wyświetli listę wszystkich zadań i zapyta o id
zadania, utworzy i zapisze obiekt typu
Solution.
Pole created wypełni się automatycznie,
a pola updated i description mają zostać
puste.
* view – zapyta o id użytkownika, którego
rozwiązania chcemy zobaczyć.

Po wykonaniu dowolnej z opcji, program ponownie zada pytanie o wybór opcji.

# Program Użytkownika

## Dodawanie rozwiązań

Program przyjmie jeden parametr, podawany
podczas uruchamiania z konsoli lub IDE,
symbolizujący identyfikator Użytkownika.
Pamiętaj, że parametry takie pobieramy z tablicy
args parametrów metody main.
```
public static void main(String[] args)
```
Program, po uruchomieniu, wyświetli w konsoli
napis:

"Wybierz jedną z opcji:
* add – dodawanie rozwiązania,
* view – przeglądanie swoich rozwiązań."

Po wybraniu odpowiedniej opcji, program odpyta o dane i wykona odpowiednią operację:
* po wybraniu add – wyświetli listę zadań, do których Użytkownik nie dodał jeszcze rozwiązania,
a następnie odpyta o id zadania, do którego ma zostać dodane rozwiązanie.
Pole created zostanie wypełnione automatycznie, więc Użytkownik zostanie odpytany jeszcze tylko
o rozwiązanie zadania,
* w przypadku wybrania quit – program zakończy działanie.

Dla uproszczenia przyjmujemy, że dodanego rozwiązania nie możemy usuwać, ani edytować.

W przypadku próby dodania rozwiązania, które już istnieje czyli Użytkownik poda id z zakresu innego niż
zaprezentowany w programie, program ma wyświetlić odpowiedni komunikat.

Zastanów się jakie dodatkowe programy warto by
było dopisać.
Jeżeli masz pomysł na inne potrzebne metody lub
klasy, to dopisz je do swojego programu.