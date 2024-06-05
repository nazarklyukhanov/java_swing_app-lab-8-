/**
 * The BandGenerator class generates MusicBand objects based on user input.
 */
package org.example.recources.generators;

import org.example.exceptions.WrongArgumentException;
import org.example.recources.*;
import org.example.system.Validator;

import java.util.Scanner;

public class BandGenerator {
    /**
     * Generates a MusicBand object based on user input.
     *
     * @return the generated MusicBand object
     */
    public static MusicBand generateBand() {
        System.out.println("Create a new object");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name: \n");
        String name = "";
        while (true) {
            name = sc.nextLine();
            try {
                Validator.isNotNull(name);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        float x = 0;
        Float y = (float) 0L;
        while (true) {
            System.out.print("Enter the coordinates of the performer in the format [x space y]: \n");
            String xy = sc.nextLine();
            try {
                Validator.XisCorrect(xy.split(" ")[0]);
                Validator.YisCorrect(xy.split(" ")[1]);
                x = Float.parseFloat(xy.split(" ")[0]);
                y = Float.parseFloat(xy.split(" ")[1]);
                break;
            } catch (WrongArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        Integer numberOfParticipants;
        while (true) {
            System.out.print("Enter the number of participants in the music group: \n");
            String numberOfParticipantsInput = sc.nextLine();
            try {
                Validator.isNotNullZero(numberOfParticipantsInput);
                numberOfParticipants = Integer.parseInt(numberOfParticipantsInput);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        Integer albumsCount;
        while (true) {
            System.out.print("Enter the number of albums of the music group: \n");
            String albumsCountInput = sc.nextLine();
            try {
                Validator.isNotNullZero(albumsCountInput);
                albumsCount = Integer.parseInt(albumsCountInput);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        MusicGenre genre;
        while (true) {
            System.out.print("Enter the music genre [PROGRESSIVE_ROCK, JAZZ, BLUES]: \n");
            String genreInput = sc.nextLine();
            try {
                genre = MusicGenre.valueOf(genreInput);
                break;
            } catch (Exception e) {
                System.out.println("Enter a genre from the options provided");
            }
        }

        Label label;
        while (true) {
            System.out.print("Enter the label's total sales revenue: \n");
            String labelInput = sc.nextLine();
            try {
                Validator.isNotNullZero(labelInput);
                label = new Label(Integer.parseInt(labelInput));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return new MusicBand(name, coordinates, numberOfParticipants,
                albumsCount, genre, label);
    }
}
