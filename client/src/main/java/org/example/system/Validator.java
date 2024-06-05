package org.example.system;

import org.example.exceptions.WrongArgumentException;

/**
 * The Validator class provides methods for validating user input.
 */
public class Validator {

    /**
     * Checks if a string is not null or empty.
     *
     * @param line The string to be validated.
     * @return True if the string is not null or empty, false otherwise.
     * @throws WrongArgumentException If the string is empty.
     */
    public static boolean isNotNull(String line) throws WrongArgumentException {
        if (line.isEmpty()) {
            throw new WrongArgumentException("Пустая строка");
        } else return true;
    }

    /**
     * Checks if the input for the x-coordinate is correct.
     *
     * @param line The string to be validated as the x-coordinate.
     * @return True if the input is correct, false otherwise.
     * @throws WrongArgumentException If the input is invalid for x-coordinate.
     */
    public static boolean XisCorrect(String line) throws WrongArgumentException {
        try {
            Float x = Float.parseFloat(line);
            if (x <= 845) {
                return true;
            }
            throw new WrongArgumentException("Недопустимое значение для x");
        } catch (Exception e) {
            throw new WrongArgumentException("Недопустимое значение для x или y");
        }
    }

    /**
     * Checks if the input for the y-coordinate is correct.
     *
     * @param line The string to be validated as the y-coordinate.
     * @return True if the input is correct, false otherwise.
     * @throws WrongArgumentException If the input is invalid for y-coordinate.
     */
    public static boolean YisCorrect(String line) throws WrongArgumentException {
        try {
            Float y = Float.parseFloat(line);
            return true;
        } catch (Exception e) {
            throw new WrongArgumentException("Недопустимое значение для y");
        }
    }

    /**
     * Checks if a string is not null or zero.
     *
     * @param line The string to be validated.
     * @return True if the string is not null or zero, false otherwise.
     * @throws WrongArgumentException If the string is null or zero.
     */
    public static boolean isNotNullZero(String line) throws WrongArgumentException {
        try {
            if (Integer.parseInt(line) > 0) {
                return true;
            }
            throw new WrongArgumentException("Недопустимое значение");
        } catch (Exception e) {
            throw new WrongArgumentException("Недопустимое значение");
        }
    }

    /**
     * Checks if the input string represents a correct music genre.
     *
     * @param line The string to be validated as a music genre.
     * @return True if the input represents a correct music genre, false otherwise.
     * @throws WrongArgumentException If the input is not a valid music genre.
     */
    public static boolean isCorrectMusicGenre(String line) throws WrongArgumentException {
        try {
            if (line.equals("PROGRESSIVE ROCK") || line.equals("JAZZ") || line.equals("BLUES")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new WrongArgumentException("Введите музыкальный жанр из предложенных");
        }
    }
}
