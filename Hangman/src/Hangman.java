import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner inputScanner = new Scanner(System.in);
        List<Character> playerGuesses = new ArrayList<>();
        int wrongCount = 0;

        System.out.println("Welcome to Hangman!");
        System.out.println("Do you want to play alone (1) or with a friend (2)?");
        String playersChoice = inputScanner.nextLine();

        String secretWord = (playersChoice.equals("1")) ? getRandomWordFromFile() : getPlayerInput(inputScanner);

        while (true) {
            printHangedMan(wrongCount);

            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The word was: " + secretWord);
                break;
            }

            printWordState(secretWord, playerGuesses);
            if (!getPlayerGuess(inputScanner, secretWord, playerGuesses)) {
                wrongCount++;
            }

            if (printWordState(secretWord, playerGuesses)) {
                System.out.println("You win!");
                break;
            }

            System.out.println("Please enter your guess for the word:");
            String guessedWord = inputScanner.nextLine();
            if (guessedWord.equals(secretWord)) {
                System.out.println("You win!");
                break;
            } else {
                System.out.println("Nope! Try again.");
            }
        }
    }

    private static String getRandomWordFromFile() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("C:/Users/hangmanword.txt"));
        List<String> words = new ArrayList<>();

        while (fileScanner.hasNext()) {
            words.add(fileScanner.nextLine());
        }

        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    private static String getPlayerInput(Scanner scanner) {
        System.out.println("Player 1, please enter your word:");
        clearScreen();
        return scanner.nextLine();
    }

    private static void printHangedMan(int wrongCount) {
        System.out.println(" _______");
        System.out.println(" |     |");
        if (wrongCount >= 1) {
            System.out.println(" O");
        }

        if (wrongCount >= 2) {
            System.out.print("\\ ");
            if (wrongCount >= 3) {
                System.out.println("/");
            } else {
                System.out.println("");
            }
        }

        if (wrongCount >= 4) {
            System.out.println(" |");
        }

        if (wrongCount >= 5) {
            System.out.print("/ ");
            if (wrongCount >= 6) {
                System.out.println("\\");
            } else {
                System.out.println("");
            }
        }
        System.out.println();
        System.out.println();
    }

    private static boolean getPlayerGuess(Scanner scanner, String word, List<Character> playerGuesses) {
        System.out.println("Please enter a letter:");
        String letterGuess = scanner.nextLine();
        playerGuesses.add(letterGuess.charAt(0));

        return word.contains(letterGuess);
    }

    private static boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (char letter : word.toCharArray()) {
            if (playerGuesses.contains(letter)) {
                System.out.print(letter);
                correctCount++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();

        return word.length() == correctCount;
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
