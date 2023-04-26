package edu.sdsmt.hamsterrun_johnson_noah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private GameView gameView;
    private StateMachine stateMachine;
    private boolean isFABOpen = false;
    private int backgroundColor;

    /* Constants for saving to bundle */
    private static final String ZOOMS_LEFT = "zoomsLeft";
    private static final String ENERGY = "energy";
    private static final String MOVES = "moves";
    private static final String FOOD = "food";
    private static final String STORED = "stored";
    private static final String LOCATION_X = "location_X";
    private static final String LOCATION_Y = "location_Y";
    private static final String TINT_COLOR = "tint_color";
    private static final String TINT_FLAG = "tint_flag";
    private static final String BACKGROUND_COLOR = "background_color";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        gameView = findViewById(R.id.gameArea);
        game = gameView.getGame();
        stateMachine = new StateMachine(game, this);
        setBackgroundColor(Color.LTGRAY);
        this.updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(ZOOMS_LEFT, game.getZoomsLeft());
        savedInstanceState.putInt(ENERGY, game.getEnergy());
        savedInstanceState.putInt(MOVES, game.getMoves());
        savedInstanceState.putInt(FOOD, game.getFood());
        savedInstanceState.putInt(STORED, game.getHomeStores());
        savedInstanceState.putInt(LOCATION_X, game.getLocation().x);
        savedInstanceState.putInt(LOCATION_Y, game.getLocation().y);
        savedInstanceState.putString(TINT_COLOR, gameView.getTintColor());
        savedInstanceState.putBoolean(TINT_FLAG, gameView.getTintFlag());
        savedInstanceState.putInt(BACKGROUND_COLOR, this.backgroundColor);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        game.setZoomsLeft(savedInstanceState.getInt(ZOOMS_LEFT));
        game.setEnergy(savedInstanceState.getInt(ENERGY));
        game.setFood(savedInstanceState.getInt(FOOD));
        game.setMoves(savedInstanceState.getInt(MOVES));
        game.setStoredFood(savedInstanceState.getInt(STORED));
        game.setLocationX(savedInstanceState.getInt(LOCATION_X));
        game.setLocationY(savedInstanceState.getInt(LOCATION_Y));
        gameView.setTintColor(savedInstanceState.getString(TINT_COLOR));
        gameView.setTintFlag(savedInstanceState.getBoolean(TINT_FLAG));
        this.setBackgroundColor(savedInstanceState.getInt(BACKGROUND_COLOR));
        updateUI();
    }

    public Game getGame() {
        return game;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void setBackgroundColor(int newColor) {
        this.backgroundColor = newColor;
        ConstraintLayout rootLayout = findViewById(R.id.root_layout);
        rootLayout.setBackgroundColor(newColor);
    }

    public void updateUI() {
        TextView movesText = findViewById(R.id.moves);
        movesText.setText(String.valueOf(this.game.getMoves()));

        TextView energyText = findViewById(R.id.energy);
        energyText.setText(String.valueOf(this.game.getEnergy()));

        TextView storesText = findViewById(R.id.stores);
        storesText.setText(String.valueOf(this.game.getHomeStores()));

        TextView zoomText = findViewById(R.id.zoom);
        zoomText.setText(String.valueOf(this.game.getZoomsLeft()));

        TextView foodText = findViewById(R.id.food);
        foodText.setText(String.valueOf(this.game.getFood()));

        findViewById(R.id.zoomBtn).setEnabled(game.getZoomsLeft() > 0);

        gameView.invalidate();
    }

    public void handleResetButton(View view) {
        this.game.setResetFlag(true);
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleZoomButton(View view) {
        if(this.game.getZoomsLeft() > 0) {
            this.game.setIsZooming(true);
            this.game.setZoomsLeft(this.game.getZoomsLeft() - 1);
            stateMachine.doMaintenanceTask();
            updateUI();
        }
    }

    public void handleEatButton(View view) {
        this.game.eat();
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleRightButton(View view) {
        this.game.move(1, 0);
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleLeftButton(View view) {
        this.game.move(-1, 0);
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleDownButton(View view) {
        this.game.move(0, 1);
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleUpButton(View view) {
        this.game.move(0, -1);
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleFABControlClick(View view) {
        if (!isFABOpen)
            showFABMenu();
        else
            hideFABMenu();
    }

    public void handleTintOptionOneClick(View view) {
        gameView.setTintFlag(false);
        gameView.invalidate();
    }

    public void handleTintOptionTwoClick(View view) {
        gameView.setTintFlag(true);
        gameView.setTintColor("#2587be");
        gameView.invalidate();
    }

    public void handleTintOptionThreeClick(View view) {
        gameView.setTintFlag(true);
        gameView.setTintColor("#6c25be");
        gameView.invalidate();
    }

    public void showFABMenu() {
        FloatingActionButton option1 = findViewById(R.id.option1);
        FloatingActionButton option2 = findViewById(R.id.option2);
        FloatingActionButton option3 = findViewById(R.id.option3);

        isFABOpen = true;
        option1.animate().translationY(55);
        option2.animate().translationY(105);
        option3.animate().translationY(155);
    }

    public void hideFABMenu() {
        FloatingActionButton option1 = findViewById(R.id.option1);
        FloatingActionButton option2 = findViewById(R.id.option2);
        FloatingActionButton option3 = findViewById(R.id.option3);

        isFABOpen = false;

        option1.animate().translationY(0);
        option2.animate().translationY(0);
        option3.animate().translationY(0);
    }

    public void showEndingDialog(String message) {
        // Create the dialog box builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the message for the dialog box
        builder.setMessage(message);
        // Add a button to the dialog box
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                game.reset();
                updateUI();
            }
        });
        // Create and show the dialog box
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setRedBackground(View view) {
        this.setBackgroundColor(Color.RED);
    }

    public void setGrayBackground(View view) {
        this.setBackgroundColor(Color.LTGRAY);
    }
}
