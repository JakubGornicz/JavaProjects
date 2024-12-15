public class Bomberman {

    public static void main(String[] args) throws InterruptedException {
        // add Constraints
        //1 <= r, c <= 200
        //1 <=  <= 10^9
        // add user input
        String[] grid = {
                "...",
                ".O.",
                "...",
        };
        int n = 5; // Liczba sekund symulacji

        // Konwersja String[] do char[][] oraz int[][] dla czasów bomb
        char[][] charGrid = convertToCharGrid(grid);
        int[][] timeGrid = new int[charGrid.length][charGrid[0].length];

        simulateGame(charGrid, timeGrid, n);
    }

    // Funkcja symulacji
    public static void simulateGame(char[][] grid, int[][] timeGrid, int n) throws InterruptedException {
        // Symulacja dla każdej sekundy
        for (int t = 0; t <= n; t++) {
            System.out.println("Czas: " + t + " sekundy");

            if (t == 0 || t == 1) {
                // Na starcie tylko wypisujemy grid
                printGrid(grid);
            } else if (t % 2 == 0) {
                // Sadzenie bomb na pustych polach
                plantBombs(grid, timeGrid, t);
                printGrid(grid);
            } else {
                // Detonacja bomb, które mają >= 3 sekundy
                detonate(grid, timeGrid, t);
                printGrid(grid);
            }

            // Pauza na 1 sekundę
            Thread.sleep(1000);
        }
    }

    // Sadzenie bomb na pustych polach
    public static void plantBombs(char[][] grid, int[][] timeGrid, int currentTime) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '.') {
                    grid[i][j] = 'O';
                    timeGrid[i][j] = currentTime; // Zapisujemy czas posadzenia bomby
                }
            }
        }
    }

    // Detonacja bomb starszych niż 3 sekundy
    public static void detonate(char[][] grid, int[][] timeGrid, int currentTime) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] toClear = new boolean[rows][cols]; // Pomocnicza tablica do oznaczania pól do wyczyszczenia

        // Znajdź wszystkie pola do usunięcia
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'O' && (currentTime - timeGrid[i][j]) >= 3) {
                    toClear[i][j] = true; // Obecna komórka wybucha
                    if (i > 0) toClear[i - 1][j] = true; // Góra
                    if (i < rows - 1) toClear[i + 1][j] = true; // Dół
                    if (j > 0) toClear[i][j - 1] = true; // Lewo
                    if (j < cols - 1) toClear[i][j + 1] = true; // Prawo
                }
            }
        }

        // Wyczyść oznaczone pola i przepisz pozostałe bomby
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (toClear[i][j]) {
                    grid[i][j] = '.'; // Wyczyszczenie pola
                    timeGrid[i][j] = 0; // Reset czasu
                }
            }
        }
    }

    // Wypisywanie gridu
    public static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Konwersja String[] do char[][]
    public static char[][] convertToCharGrid(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();
        char[][] charGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            charGrid[i] = grid[i].toCharArray();
        }
        return charGrid;
    }
}
