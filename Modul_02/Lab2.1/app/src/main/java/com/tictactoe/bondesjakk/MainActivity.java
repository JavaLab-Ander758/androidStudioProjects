package com.tictactoe.bondesjakk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    GameBoard gameBoard;
    Button[] buttons;
    TextView[] playerTextViews;
    String colorPlayer2, colorPlayer1;
    boolean startState;
    boolean player1Turn = false;
    int secondsPerRound, remainingSecondsPlayer1, remainingSecondsPlayer2;
    int previousScorePlayer1, previousScorePlayer2;
    long timeElapsedPlayer1, timeElapsedPlayer2;
    int numberOfTapsOnRound;
    Resources resources;
    Button startStopButton;
    boolean countdownShutdown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ads for PlayStore
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        initializeGameBoard();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Start/Pause the game state
     */
    public void startStopGame(View view) {
        if (!startState) {
            startGame();
        } else {
            stopGame();
        }
    }

    /**
     * Starts the game
     */
    private void startGame() {
        startState = true;
        enableButtons(true);
        startStopButton.setText(resources.getText(R.string.btnStartStop_STOP));

        if (player1Turn) {
            enableTextViewPlayer(true, 1);
        }
        else {
            enableTextViewPlayer(true, 2);
        }
        countdownShutdown = false;
        countdown();
    }

    /**
     * Stops the game
     */
    private void stopGame() {
        countdownShutdown = true;
        startState = false;
        enableButtons(false);
        startStopButton.setText(resources.getText(R.string.btnStartStop_START));
        enableTextViewPlayer(false, -1);
    }

    /**
     * Counts down from n-seconds for TextView tvCountdown
     */
    private void countdown() {
        final TextView tvCountdown = findViewById(R.id.tvCountdown);
        final boolean player1TurnInstance = player1Turn;

        if (!countdownShutdown) {
            new CountDownTimer(secondsPerRound * 1000, 10) {
                public void onTick(long millisUntilFinished) {
                    if (countdownShutdown)
                        this.cancel();
                    else
                        tvCountdown.setText(String.format("%02d:%2d", (millisUntilFinished - 1000) / 1000, (millisUntilFinished % 1000)));

                    // Append elapsed time for current player if countdown was cancelled
                    if (player1TurnInstance != player1Turn) {
                        incrementPlayerElapsed((secondsPerRound * 1000) - millisUntilFinished);
                        this.cancel();
                    }
                }

                void incrementPlayerElapsed(long milliseconds) {
                    if (player1TurnInstance)
                        timeElapsedPlayer1 += milliseconds;
                    else
                        timeElapsedPlayer2 += milliseconds;
                }

                public void onFinish() {
                    switchPlayerState();
                    countdown();
                    // Switch players and append total time for current player
                    if (player1TurnInstance) {
                        timeElapsedPlayer1 += secondsPerRound * 1000;
                    }
                    else {
                        timeElapsedPlayer2 += secondsPerRound * 1000;
                    }
                }
            }.start();
        }
    }

    /**
     * Switch current player
     *  Usage: When player has take their turn
     */
    private void switchPlayerState() {
        countdownShutdown = false;
        countdown();
        if (player1Turn) {
            player1Turn = false;
            enableTextViewPlayer(true, 2);
            enableTextViewPlayer(false, 1);
        }
        else {
            player1Turn = true;
            enableTextViewPlayer(true, 1);
            enableTextViewPlayer(false, 2);
        }
    }

    /**
     * Used when players press a button on the game board
     * Changes the state of the game and disables current button
     * @param view View that is pressed
     */
    public void gameButtonClick(View view) {
        numberOfTapsOnRound++;
        // Extract Button's x- and y-coordinate from its name
        Button button = (Button) view;
        String string = view.getResources().getResourceName(view.getId());
        String buttonNumber = string.substring(string.length() - 2);
        int xCoordinate = Character.getNumericValue(buttonNumber.charAt(0));
        int yCoordinate = Character.getNumericValue(buttonNumber.charAt(1));

        if (player1Turn) {
            button.setText("X");
            switchPlayerState();
            countdown();
            button.setClickable(false);
            gameBoard.plotNewEntryInArray(1, xCoordinate, yCoordinate);
        }
        else {
            button.setText("O");
            switchPlayerState();
            countdown();
            button.setClickable(false);
            gameBoard.plotNewEntryInArray(2, xCoordinate, yCoordinate);
        }

        TextView textView = findViewById(R.id.tvResult);
        if (numberOfTapsOnRound == 9) {
            textView.setText(getResultString(false));
            resetGame();
        }
        if (previousScorePlayer1 != gameBoard.getScorePlayer1()) {
            previousScorePlayer1++;
            textView.setText(getResultString(true));
            resetGame();
        } else if (previousScorePlayer2 != gameBoard.getScorePlayer2()) {
            previousScorePlayer2++;
            textView.setText(getResultString(true));
            resetGame();
        }
    }


    /**
     * Fetches String format used in strings.xml in 'values'
     * @return Correctly formated String
     */
    private String getResultString(boolean drawGame) {
        if (drawGame)
            return String.format(getString(R.string.game_won_result_format),
                    !player1Turn ? 1 : 2, !player1Turn ? gameBoard.getScorePlayer1() : gameBoard.getScorePlayer2(),
                    !player1Turn ? 2 : 1, !player1Turn ? gameBoard.getScorePlayer2() : gameBoard.getScorePlayer1(),
                    timeElapsedPlayer1 / 1000, timeElapsedPlayer2 / 1000);
        else
            return String.format(getString(R.string.game_draw_result_format),
                    gameBoard.getScorePlayer1(), gameBoard.getScorePlayer2(),
                    timeElapsedPlayer1 / 1000, timeElapsedPlayer2 / 1000);
    }













    /**
     * Resets the board for a new game
     */
    public void resetGame() {
        timeElapsedPlayer1 = 0;
        timeElapsedPlayer2 = 0;
        numberOfTapsOnRound = 0;
        for (Button button : buttons) {
            button.setText("");
            enableButtons(true);
        }
        stopGame();
        countdownShutdown = true;
        TextView textView = findViewById(R.id.tvCountdown);
        textView.setText("00:00");
    }

    /**
     * Enable or disable buttons on the game boardf
     * @param enable Boolean stating enabling/disabling
     */
    public void enableButtons(boolean enable) {
        for (Button button : buttons) {
            if (enable)
                button.setClickable(true);
            else
                button.setClickable(false);
        }
    }

    /**
     * Enable or disable text views for player
     * @param enable Boolean stating enabling/disabling
     * @param player Which player to change for, -1 for disabling both player's TextView
     */
    public void enableTextViewPlayer(boolean enable, int player) {
        if (enable) {
            if (player == 1)
                playerTextViews[0].setBackground(getDrawable(R.drawable.player_text_view_active));
            else
                playerTextViews[1].setBackground(getDrawable(R.drawable.player_text_view_active));
        } else {
            if (player == 1)
                playerTextViews[0].setBackground(getDrawable(R.drawable.player_text_view_inactive));
            else
                playerTextViews[1].setBackground(getDrawable(R.drawable.player_text_view_inactive));
        }

        // When game stopped
        if (player == -1) {
            for (TextView textView : playerTextViews)
                textView.setBackground(getDrawable(R.drawable.player_text_view_inactive));
        }
    }

    /**
     * Initialize all variables
     */
    private void initializeGameBoard() {
        gameBoard = new GameBoard(3);
        secondsPerRound = 7;
        startState = false;
        player1Turn = true;
        remainingSecondsPlayer1 = secondsPerRound;
        remainingSecondsPlayer2 = secondsPerRound;
        colorPlayer1 = "#0000FF";
        colorPlayer2 = "#FF0000";
        previousScorePlayer1 = 0;
        previousScorePlayer2 = 0;

        buttons = new Button[9];
        buttons[0] = findViewById(R.id.button_00);
        buttons[1] = findViewById(R.id.button_01);
        buttons[2] = findViewById(R.id.button_02);
        buttons[3] = findViewById(R.id.button_10);
        buttons[4] = findViewById(R.id.button_11);
        buttons[5] = findViewById(R.id.button_12);
        buttons[6] = findViewById(R.id.button_20);
        buttons[7] = findViewById(R.id.button_21);
        buttons[8] = findViewById(R.id.button_22);
        enableButtons(false);

        playerTextViews = new TextView[2];
        playerTextViews[0] = findViewById(R.id.tvPlayer1);
        playerTextViews[1] = findViewById(R.id.tvPlayer2);

        timeElapsedPlayer1 = 0;
        timeElapsedPlayer2 = 0;
        resources = getResources();
        startStopButton = findViewById(R.id.btnStartStop);
    }




    //////////////////////////
    // Create Toolbar with Exit function //
    //////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
