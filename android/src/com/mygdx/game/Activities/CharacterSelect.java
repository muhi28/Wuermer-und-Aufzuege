package com.mygdx.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mygdx.game.Players.PlayerColor;
import com.mygdx.game.R;
import com.mygdx.game.netwoking.FromNetworkProcessor;
import com.mygdx.game.netwoking.GameSync;
import com.mygdx.game.netwoking.NetworkManager;
import com.mygdx.game.netwoking.NetworkMonitor;
import com.mygdx.game.netwoking.NetworkTrafficReceiver;

import java.util.Random;

import static com.mygdx.game.Players.PlayerColor.RED;

/**
 * Created by Muhi on 04.04.2017.
 */

/**
 * This Class is used to give players the option to choose a specific color for their players.
 */
public class CharacterSelect extends Activity {

    private static final String PLAYER_READY_MESSAGE = "READY2PLAY?seed=";
    public static final String PLAYER_COLOR_KEY = "Player_Color";
    public static final String OTHER_PLAYER_COLOR_KEY = "Other_Player_Color";
    public static final String SEED_RANDOM = "RANDOM_SEED";

    private Intent intent;

    private TextView chosenPlayer;
    private TextView playername;
    private NetworkTrafficReceiver networkTrafficReceiver;
    private boolean otherPlayerReady = false;
    private boolean waitingForOtherPlayer = false;

    private PlayerColor color;
    private PlayerColor colorOtherPlayer = null;
    private Long seedRandom = new Random().nextLong();

    /**
     * The onCreate-Method is used to set the content view of the class to the main menu activity.
     *
     * @param savedInstanceState ... Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final CharacterSelect currentInstance = this;
        networkTrafficReceiver = new NetworkTrafficReceiver(new FromNetworkProcessor() {
            public void receiveMessage(String message) {
                currentInstance.processMessageFromNetwork(message);
            }
        });


        GameSync.getSync().waitForWormSelection();
        setContentView(R.layout.character_select_activity);

        //Character auswahl
        chosenPlayer = (TextView) findViewById(R.id.chosen_player_textview);

        //Spielername

        playername = (TextView) findViewById(R.id.spielername_textview);

        Intent in = getIntent();

        if (in.hasExtra("Playername")) {

            String name = in.getStringExtra("Playername");

            playername.setText(String.format(" %s", name));
            playername.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method starts the game after the player has chosen a character
     * and the start game button is pressed.
     *
     * @param view ... View
     */
    public void onClickStartGame(View view) {
        NetworkManager.send(PLAYER_READY_MESSAGE + seedRandom, true);

        if (NetworkManager.isMultiplayer()) {
            if (NetworkMonitor.isConnected() && colorOtherPlayer == null) {
                setMessage("Please wait for other player");
                chosenPlayer.setVisibility(View.VISIBLE);
                return;
            }

            if (!otherPlayerReady) {
                setMessage("Please wait for other player!");
                waitingForOtherPlayer = true;
                return;
            }


        }

        if (colorOtherPlayer == null) {

            colorOtherPlayer = color.equals(RED) ? PlayerColor.BLUE : RED;
        }

        intent = new Intent(this, MainGameActivity.class);
        intent.putExtra("Player_Color", color.toString());
        intent.putExtra(PLAYER_COLOR_KEY, color.toString());
        intent.putExtra(OTHER_PLAYER_COLOR_KEY, colorOtherPlayer.toString());
        intent.putExtra(SEED_RANDOM, seedRandom);


        startActivity(intent);
    }

    private void setMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chosenPlayer.setText(message);
                chosenPlayer.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * onClickGoBackToMenu switches from the character selection screen back
     * to the main menu screen if the button is pressed.
     *
     * @param view the view
     */
    public void onClickGoBackToMenu(View view) {

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    /**
     * Red buttonclicked.
     *
     * @param view the view
     */
    public void redButtonclicked(View view) {

        setMessage("Es wurde die rote Spielfigur gewählt.");


        color = RED;
        sendSelectedColor();
    }

    /**
     * Blue buttonclicked.
     *
     * @param view the view
     */
    public void blueButtonclicked(View view) {

        setMessage("Es wurde die blaue Spielfigur gewählt.");

        color = PlayerColor.BLUE;
        sendSelectedColor();
    }

    /**
     * Green buttonclicked.
     *
     * @param view the view
     */
    public void greenButtonclicked(View view) {

        setMessage("Es wurde die grüne Spielfigur gewählt.");

        color = PlayerColor.GREEN;
        sendSelectedColor();
    }

    /**
     * Yellow buttonclicked.
     *
     * @param view the view
     */
    public void yellowButtonclicked(View view) {

        setMessage("Es wurde die gelbe Spielfigur gewählt.");

        color = PlayerColor.YELLOW;
        sendSelectedColor();
    }

    private void sendSelectedColor() {
        NetworkManager.send(color.toString(), true);
    }

    /**
     * Process message from network.
     *
     * @param inputString the input string
     */
    public void processMessageFromNetwork(final String inputString) {

        if (inputString.startsWith(PLAYER_READY_MESSAGE)) {
            this.seedRandom = Long.valueOf(inputString.replace(PLAYER_READY_MESSAGE, ""));

            this.otherPlayerReady = true;
            if (waitingForOtherPlayer) {
                onClickStartGame(findViewById(R.id.start_game_button));
            } else {
                setMessage("Other player is ready!");
            }
        }

        PlayerColor playerColor = PlayerColor.getFromString(inputString);

        if (playerColor != null) {
            setMessage("Other player chose worm with color: " + inputString);
            this.colorOtherPlayer = playerColor;
            GameSync.getSync().otherPlayerHasSelectedWorm();
        }
    }

}
