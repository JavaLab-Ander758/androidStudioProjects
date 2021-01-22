package com.andyeseapps.towerofhanoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


// TODO: Ensure start/stop of game correctly affects time used
// TODO: Implement saving state on rotation change
public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    int numberOfDisks = 3;
    int numberOfMoves = 0;
    long startTime, elapsedTime = 0; // TODO: append this with currently used on game pause
    boolean startState = false, won = false, pausedTimer = true;
    ImageView imageViewSmallRectangle, imageViewMiddleRectangle, imageViewLargeRectangle;
    Button buttonStartStop;
    int[] towers = {0,0,0,  0,0,0,  0,0,0};

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

        findViewById(R.id.smallRectangle).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.middleRectangle).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.largeRectangle).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.leftTower).setOnDragListener(new MyDragListener());
        findViewById(R.id.middleTower).setOnDragListener(new MyDragListener());
        findViewById(R.id.rightTower).setOnDragListener(new MyDragListener());

        startTime = System.currentTimeMillis();

        imageViewSmallRectangle = findViewById(R.id.smallRectangle);
            imageViewSmallRectangle.setEnabled(false); // setEnabled(true) on game restart
        imageViewMiddleRectangle = findViewById(R.id.middleRectangle);
        imageViewLargeRectangle = findViewById(R.id.largeRectangle);
        buttonStartStop = findViewById(R.id.buttonStartStop);
    }

    /**
     * Starts or stops the game depending on game being paused or not
     * @param view View from onClick in activity
     */
    public void startStop(View view) {
        if (won) {
            won = false;
            resetGame();
            startState = true;
        }

        if (buttonStartStop != null) {
            if (startState) {
                // Pause the game
                startState = false;
                buttonStartStop.setText(R.string.button_start);
                elapsedTime += System.currentTimeMillis() - startTime;
                enableAllRectangles(false);
                pausedTimer = true;
            } else {
                // Start the game
                if (startTime == 0 || pausedTimer) {
                    startTime = System.currentTimeMillis();
                }
                startState = true;
                buttonStartStop.setText(R.string.button_stop);
                enableAllRectangles(true);
                pausedTimer = false;
            }
        }

    }

    private void enableAllRectangles(boolean enable) {
        imageViewSmallRectangle.setEnabled(enable);
        imageViewMiddleRectangle.setEnabled(enable);
        imageViewLargeRectangle.setEnabled(enable);
    }

    /**
     * Resets the game for a new round
     */
    private void resetGame() {
        // Move all rectangles to left tower
        LinearLayout linearLayoutRightTower = findViewById(R.id.rightTower);
        LinearLayout linearLayoutLeftTower = findViewById(R.id.leftTower);
        linearLayoutRightTower.removeAllViews();
        linearLayoutLeftTower.addView(imageViewSmallRectangle);
        linearLayoutLeftTower.addView(imageViewMiddleRectangle);
        linearLayoutLeftTower.addView(imageViewLargeRectangle);

        TextView textViewMovesUsed = findViewById(R.id.textViewMovesUsed);
        textViewMovesUsed.setText(R.string.moves_used_by_user);
        TextView textViewSecondsUsed = findViewById(R.id.textViewSecondsUsed);
        textViewSecondsUsed.setText(R.string.seconds_used_by_user);

        buttonStartStop.setText(R.string.button_start);
        numberOfMoves = 0;
        elapsedTime = 0;
    }


    /**
     * Handles touch listening
     */
    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View viewToBeDragged, MotionEvent motionEvent) {
            LinearLayout owner = (LinearLayout) viewToBeDragged.getParent();
            View top = owner.getChildAt(0);

            if (viewToBeDragged == top || owner.getChildCount() == 1) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(viewToBeDragged);
                    viewToBeDragged.startDrag(data, shadowBuilder, viewToBeDragged, 0);
//                    viewToBeDragged.setVisibility(View.INVISIBLE);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Handles drag listening
     */
    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_tower_drop_target);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape_tower);
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    break;

                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    LinearLayout container = (LinearLayout) v;
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();

                    View topElement = null;
                    if (container.getChildCount() > 0) {
                        // Get top disk
                        topElement = container.getChildAt(0);
                    }
                    // Sjekk
                    if (topElement == null || view.getWidth() < topElement.getWidth()) {
                        // Disk can be dropped on current tower
                        owner.removeView(view);
                        container.addView(view, 0);
                        view.setVisibility(View.VISIBLE);
                        numberOfMoves++;
                    }
                    // Declare winner if rightmost tower full
                    LinearLayout linearLayoutRightTower = findViewById(R.id.rightTower);
                    if (linearLayoutRightTower.getChildCount() == numberOfDisks) {
                        // Output win-messages
                        TextView textViewMovesUsed = findViewById(R.id.textViewMovesUsed);
                        textViewMovesUsed.setText(String.format(getString(R.string.moves_used_by_user_win_message), numberOfMoves));
                        TextView textViewSecondsUsed = findViewById(R.id.textViewSecondsUsed);
                        textViewSecondsUsed.setText(String.format(getString(R.string.seconds_used_by_used_win_message), ((System.currentTimeMillis() - startTime) + elapsedTime) / 1000) );

                        ImageView imageViewSmallRing = findViewById(R.id.smallRectangle);
                        imageViewSmallRing.setEnabled(false); // TODO: setEnabled(true) on game restart

                        startState = false; // TODO: set to true on game restart
                        won = true;

                        // re-initialize values for new game
                        buttonStartStop.setText(R.string.button_reset);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Save UI
        // Fetch which tower all rectangles are located
        ArrayList<View> leftTower = new ArrayList<>();
//        LinearLayout linearLayoutLeftTower = findViewById(R.id.leftTower);
        // PUT all 3 linearlayouts in an array and traverse that...


        LinearLayout towerContainer = findViewById(R.id.linearLayoutAllTowers);
        for (int towerIndex = 0; towerIndex < towerContainer.getChildCount(); towerIndex++) {
            LinearLayout tower = (LinearLayout)towerContainer.getChildAt(towerIndex);
            for (int diskIndex = 0; diskIndex < tower.getChildCount(); diskIndex++) {
                if (tower.getChildAt(diskIndex).getWidth() == 60)
                    towers[towerIndex + numberOfDisks] = 60;
                if (tower.getChildAt(diskIndex).getWidth() == 80)
                    towers[towerIndex + numberOfDisks] = 80;
                if (tower.getChildAt(diskIndex).getWidth() == 100)
                    towers[towerIndex + numberOfDisks] = 100;
            }
        }
        outState.putIntArray("diskPlacementInTowers", towers);


        outState.putInt("numberOfDisks", numberOfDisks);
        outState.putInt("numberOfMoves", numberOfMoves);
        outState.putLong("startTime", startTime);
        outState.putLong("elapsedTime", elapsedTime);
        outState.putBoolean("startState", startState);
        outState.putBoolean("won", won);
        outState.putBoolean("pausedTimer", pausedTimer);


        // Save all variables
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        towers = savedInstanceState.getIntArray("diskPlacementInTwers");
        numberOfDisks = savedInstanceState.getInt("numberOfDisks");

        super.onRestoreInstanceState(savedInstanceState);
    }
}