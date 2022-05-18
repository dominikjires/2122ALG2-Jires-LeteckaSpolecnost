/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author jires
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String typeOfAircraft = "A320";
        int[][] seatMap;
        int AllocatedSeatsCount = 1;

        switch (typeOfAircraft) {
            case "A320":
                seatMap = new int[2][2];
                break;
            default:
                throw new NoSuchElementException("Type of aircraft is unsupported.");
        }

        if (AllocatedSeatsCount > seatMap.length * seatMap[0].length) {
            throw new NoSuchElementException("No available seats");
        } else {
            Random rand = new Random();
            int i;
            int j;
            seatMap[0][0] = 1;
            seatMap[0][1] = 1;
            seatMap[1][0] = 1;
            do {
                i = rand.nextInt(seatMap.length);
                j = rand.nextInt(seatMap[0].length);
            } while (seatMap[i][j] == 1);
                        seatMap[i][j] = 1;
            System.out.println(seatMap[0][0]);
            int row = i+1;
            char seat;
            switch (j) {
                case 0:
                    seat = 'A';
                    break;
                case 1:
                    seat = 'B';
                    break;
                case 2:
                    seat = 'C';
                    break;
                case 3:
                    seat = 'D';
                    break;
                case 4:
                    seat = 'E';
                    break;
                case 5:
                    seat = 'F';
                    break;
                default:
                    throw new NoSuchElementException("Number of seats exceeded.");
            }
            AllocatedSeatsCount++;
            System.out.println(row + "" + seat);
        }

    }

}
