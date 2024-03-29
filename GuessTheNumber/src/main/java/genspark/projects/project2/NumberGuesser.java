package genspark.projects.project2;

import java.util.ArrayList;
import java.util.Random;

public class NumberGuesser {

    // highest guessable number
    protected int maxNumber = 20;
    // random number generated by this program
    private int randy;
    // list of guesses from the user
    protected ArrayList<Integer> guesses = new ArrayList<>(maxNumber);
    // class which talks to the user
    protected UserIn player;
    private boolean firstGame = true;

    public void startGame(String name) {
        randy = setRandomNumber(maxNumber);
        player = (name == null) ? new UserIn() : new UserIn(name);
        player.setIn(System.in);
        challenge();
        userGuess();
    }

    public void restart(String user) {
        firstGame = false;
        startGame(user);
    }

    public int setRandomNumber(int max) {
        final Random rand = new Random();
        int randNum = rand.nextInt(max + 1);
        return randNum;
    }

    private void userGuess() {
        int count = 0;
        int guess;

        do {
            guess = player.getNumber();
            guesses.add(guess);
            ++count;
            // number is correct
            if (guess == randy) {
                correctAnswer(count);
                player.replay();
                break;
            }
            // number is in the negatives
            if (guess < 0) {
                wayTooLow();
            }
            // number is greater than max
            else if (guess > maxNumber) {
                wayTooHigh();
            }
            // higher than random
            else if (guess > randy) {
                System.out.println("That number is too high.");
            }
            // lower than random
            else if (guess < randy) {
                System.out.println("That number is too low.");
            }
            printGuesses();
        } while (guess != randy);
        System.out.println("Here are the numbers you tried : " + guesses.toString());
        System.out.println();
    }

    private void challenge() {

        if (!firstGame) {
            String str = """
                    Okay %name... Here we go again!
                    """;
            str = str.replace("%name", player.getName());
            System.out.println(str);
        }

        String challenge = """
                Guess the secret number, %name
                   It's between 0 and %max.
                   """;
        challenge = challenge.replace("%max", maxNumber + "");
        challenge = challenge.replace("%name", player.getName());
        System.out.println(challenge);
    }

    private void printGuesses() {
        String guessed = guesses.toString();
        String numbers = """
                Numbers guessed so far : %numbers
                """;
        numbers = numbers.replace("%numbers", guessed.substring(1, guessed.length() - 1));
        System.out.println(numbers);

    }

    private void correctAnswer(int numTimes) {
        String genius = """
                That's exactly it, %name.
                You figured it out in %int attempts.""";
        genius = genius.replace("%int", numTimes + "");
        genius = genius.replace("%name", player.getName());
        System.out.println(genius);
    }

    private void wayTooHigh() {
        String str = """
                That number's way too high, %name.
                I told you the number is less than %max.""";
        str = str.replace("%max", maxNumber + "");
        str = str.replace("%name", player.getName());
        System.out.println(str);
    }

    private void wayTooLow() {
        String str = """
                I told you it's not a negative, %name.
                The number is greater than 0.""";
        str = str.replace("%name", player.getName());
        System.out.println(str);
    }
}
