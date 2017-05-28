package com.mygdx.game.game;


import android.util.Log;

import com.mygdx.game.main_controler.Controler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */
public class GameField {


    private static final int NUMBEROF_VERTICALS = 10;
    private static final int NUMBEROF_HORIZONTAL = 10;
    private static final String TAG = "GameField OriginalList";
    private static final String TAG2 = "GameField SubList";


    private final List<Field> fields;
    private static Field goal;


    private static int[] fieldnumbers = new int[NUMBEROF_VERTICALS * NUMBEROF_HORIZONTAL];

//    private final Player player;
//    private final Player player2;
    private static List<Player> players;
    private static Player currentPlayer;
    private static int currentPlayerNumber;

    /**
     * Instantiates a new Game field.
     *
     * @param fields the fields
     * @param players the player
     */
    public GameField(List<Field> fields, List<Player> players) {

//        this.player = players.get(0);
//        this.player2 = players.get(1);
        this.players = players;
        this.fields = fields;


    }

    /**
     * Gets fieldfrom pos.
     *
     * @param vertical   the vertical
     * @param horizontal the horizontal
     * @return the fieldfrom pos
     * @throws RuntimeException the runtime exception
     */
    public Field getFieldfromPos(int vertical, int horizontal) throws RuntimeException {

        for (Field field : fields) {

            if (field.getPosX() == horizontal && field.getPosY() == vertical) {

                return field;
            }
        }

        throw new RuntimeException("Field wurde nicht gefunden!");
    }

    /**
     * Gets start field.
     *
     * @return the start field
     */
    public Field getStartField() {

        return getFieldfromPos(0, 0);
    }

    /**
     * Gets fields.
     *
     * @return the fields
     */
    public List<Field> getFields() {

        return fields;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {

        return currentPlayer;
    }

    public static void nextPlayer(){
        currentPlayerNumber = (currentPlayerNumber+1)%4;
        currentPlayer = players.get(currentPlayerNumber);
    }

    /**
     * Gets fieldof player.
     *
     * @return the fieldof player
     */
    public Field getFieldofPlayer() {

        return currentPlayer.getCurrentField();
    }

    /**
     * Gets goal.
     *
     * @return the goal
     */
    public Field getGoal() {

        return goal;
    }

    /**
     * Create game field game field.
     *
     * @return the game field
     */
    public static GameField createGameField() {

        List<Field> fields = new ArrayList<>();

        int number = NUMBEROF_VERTICALS * NUMBEROF_HORIZONTAL;

        goal = new Field(NUMBEROF_VERTICALS, NUMBEROF_HORIZONTAL, number--);

        fields.add(goal);

        for (int i = NUMBEROF_HORIZONTAL; i > 0; i--) {

            for (int j = NUMBEROF_VERTICALS; j > 0; j--) {

                if (i == NUMBEROF_HORIZONTAL && j == NUMBEROF_VERTICALS) {

                    continue;
                }


                Field field = new Field(i, j, number--);
                fields.add(field);
            }

        }

        getFieldNumbers(fields);

        Elevator.generateElevator();
        List<Field> sortedFields = snakeOrder(fields);
        sortNextField(sortedFields);

        List<Player> players = new ArrayList<>();

        Player playerOne = new Player(sortedFields.get(sortedFields.size() - 1));
        players.add(playerOne);

        Player playerTwo = new Player(sortedFields.get(sortedFields.size() - 1));
        players.add(playerTwo);

        currentPlayer = playerOne;
        currentPlayerNumber = 0;

//        Player playerThree = new Player(sortedFields.get(sortedFields.size() - 1));
//        Player playerFour = new Player(sortedFields.get(sortedFields.size() - 1));

        return new GameField(sortedFields, players);

    }

    /**
     * Sort next field.
     *
     * @param sortedFields the sorted fields
     */
    public static void sortNextField(List<Field> sortedFields) {
        for (int i = 1; i < sortedFields.size(); i++) {
            sortedFields.get(i).setNextField(sortedFields.get(i - 1));
        }

    }

    /**
     * Gets field from.
     *
     * @param number the number
     * @return the field from
     * @throws RuntimeException the runtime exception
     */
    public Field getFieldFrom(int number) throws RuntimeException {
        for (Field field : fields) {
            if (field.getFieldnumber() == number) {
                return field;
            }
        }

        throw new RuntimeException("Field not found!");
    }


    /**
     * Get field numbers int [ ].
     *
     * @param gameField the game field
     * @return the int [ ]
     */
    public static int[] getFieldNumbers(List<Field> gameField) {

        for (int i = 0; i < gameField.size(); i++) {

            fieldnumbers[i] = gameField.get(i).getFieldnumber();
        }

        return fieldnumbers;
    }

    /**
     * Snake order list.
     *
     * @param originalList the original list
     * @return the list
     */
    public static List<Field> snakeOrder(List<Field> originalList) {            //this method changes the order of our List of fields, so
        //that the players "slither" across the board instead of
        //the sequential

        List<Field> subList1 = originalList.subList(0, 10);               //generation of sublists, so that i can change the order
        List<Field> subList2 = originalList.subList(10, 20);
        List<Field> subList3 = originalList.subList(20, 30);
        List<Field> subList4 = originalList.subList(30, 40);
        List<Field> subList5 = originalList.subList(40, 50);
        List<Field> subList6 = originalList.subList(50, 60);
        List<Field> subList7 = originalList.subList(60, 70);
        List<Field> subList8 = originalList.subList(70, 80);
        List<Field> subList9 = originalList.subList(80, 90);
        List<Field> subList10 = originalList.subList(90, 100);

        Collections.reverse(subList1);                                  //reversing the order of every 2nd list
        Collections.reverse(subList3);
        Collections.reverse(subList5);
        Collections.reverse(subList7);
        Collections.reverse(subList9);

        List<Field> newList = new ArrayList<>();                   //generate a new list

        newList.addAll(subList1);                                       //add all subLists into the new list
        newList.addAll(subList2);
        newList.addAll(subList3);
        newList.addAll(subList4);
        newList.addAll(subList5);
        newList.addAll(subList6);
        newList.addAll(subList7);
        newList.addAll(subList8);
        newList.addAll(subList9);
        newList.addAll(subList10);


        return newList;
    }
}
