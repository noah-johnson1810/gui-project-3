package edu.sdsmt.hamsterrun_johnson_noah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Game game;
    GameView gameView;
    StateMachine stateMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        gameView = new GameView(this);
        game = gameView.getGame();
        stateMachine = new StateMachine(game);
    }

    public Game getGame() {
        return game;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // save the state
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore the state
    }

    public void updateUI(){
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
    }

    public void handleResetButton(View view) {
        this.game.reset();
        stateMachine.doMaintenanceTask();
        updateUI();
    }

    public void handleZoomButton(View view) {
        this.game.setIsZooming(true);
        stateMachine.doMaintenanceTask();
        updateUI();
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
}
