package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: MainActivity class for hamster run project
 */

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

    // private variables
    private Game game;
    private GameView gameView;
    private StateMachine stateMachine;
    private boolean isFABOpen = false;
    private int backgroundColor;

    // constants for saving to bundle
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


    /* Description: onCreate for the mainActivity, basically a constructor of sorts
     * Params: savedInstanceState - the bundle which will contain information to create the mainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super.onCreate first since this is overridden
        super.onCreate(savedInstanceState);
        // set the content view
        setContentView(R.layout.main_view);
        // get the already existing gameView and game
        gameView = findViewById(R.id.gameArea);
        game = gameView.getGame();
        // create the stateMachine for this mainActivity
        stateMachine = new StateMachine(game, this);
        // set the initial background color
        setBackgroundColor(Color.LTGRAY);
        // update the UI
        this.updateUI();
    }


    /* Description: when the activity is going to be destroyed, performs a "save" of the necessary info
     * Params: savedInstanceState - the saving bundle that will contain all the information for re-creating the scene
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save zooms left
        savedInstanceState.putInt(ZOOMS_LEFT, game.getZoomsLeft());
        // save energy
        savedInstanceState.putInt(ENERGY, game.getEnergy());
        // save number of moves
        savedInstanceState.putInt(MOVES, game.getMoves());
        // save food
        savedInstanceState.putInt(FOOD, game.getFood());
        // save stored home food
        savedInstanceState.putInt(STORED, game.getHomeStores());
        // save location x
        savedInstanceState.putInt(LOCATION_X, game.getLocation().x);
        // save location y
        savedInstanceState.putInt(LOCATION_Y, game.getLocation().y);
        // save the tint color
        savedInstanceState.putString(TINT_COLOR, gameView.getTintColor());
        // save the tint flag
        savedInstanceState.putBoolean(TINT_FLAG, gameView.getTintFlag());
        // save the background color
        savedInstanceState.putInt(BACKGROUND_COLOR, this.backgroundColor);
    }


    /* Description: uses the bundle param to restore the saved information before the destruction
     *              (for example, caused by rotation of the device)
     * Params: savedInstanceState - the saved state to restore the values of the variables from
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore zooms left
        game.setZoomsLeft(savedInstanceState.getInt(ZOOMS_LEFT));
        // restore energy
        game.setEnergy(savedInstanceState.getInt(ENERGY));
        // restore food
        game.setFood(savedInstanceState.getInt(FOOD));
        // restore moves
        game.setMoves(savedInstanceState.getInt(MOVES));
        // restore home stored food
        game.setStoredFood(savedInstanceState.getInt(STORED));
        // restore location x
        game.setLocationX(savedInstanceState.getInt(LOCATION_X));
        // restore location y
        game.setLocationY(savedInstanceState.getInt(LOCATION_Y));
        // restore hamster tint color
        gameView.setTintColor(savedInstanceState.getString(TINT_COLOR));
        // restore tint flag
        gameView.setTintFlag(savedInstanceState.getBoolean(TINT_FLAG));
        // restore background color
        this.setBackgroundColor(savedInstanceState.getInt(BACKGROUND_COLOR));
        // update UI
        updateUI();
    }


    /* Description: gets the game this mainActivity is associated with
     * Returns: the game for this mainActivity
     */
    public Game getGame() {
        return game;
    }



    /* Description: handler for the reset button
     * Params: view - the view this button is in
     */
    public void handleResetButton(View view) {
        // set the reset flag to true
        this.game.setResetFlag(true);
        // do maintenance task on this state
        stateMachine.doMaintenanceTask();
        // update UI
        updateUI();
    }


    /* Description: handler for the zoom button
     * Params: view - the view this button is in
     */
    public void handleZoomButton(View view) {
        // if there are zooms left, perform the zoom functions
        if(this.game.getZoomsLeft() > 0) {
            // set zooming to true
            this.game.setIsZooming(true);
            // decrement zooms left
            this.game.setZoomsLeft(this.game.getZoomsLeft() - 1);
            // do maintenance task for the state
            stateMachine.doMaintenanceTask();
            // update UI
            updateUI();
        }
    }


    /* Description: handler for the eat button
     * Params: view - the view this button is in
     */
    public void handleEatButton(View view) {
        // call eat from game
        this.game.eat();
        // perform maintenance task
        stateMachine.doMaintenanceTask();
        // update UI
        updateUI();
    }


    /* Description: handler for the move right button
     * Params: view - the view this button is in
     */
    public void handleRightButton(View view) {
        // call move with x value of 1
        this.game.move(1, 0);
        // perform maintenance task
        stateMachine.doMaintenanceTask();
        // update UI
        updateUI();
    }


    /* Description: handler for the move left button
     * Params: view - the view this button is in
     */
    public void handleLeftButton(View view) {
        // call move with x value of -1
        this.game.move(-1, 0);
        // perform maintenance task
        stateMachine.doMaintenanceTask();
        // update UI
        updateUI();
    }


    /* Description: handler for the move down button
     * Params: view - the view this button is in
     */
    public void handleDownButton(View view) {
        // call move with a y value of 1
        this.game.move(0, 1);
        // perform maintenance task
        stateMachine.doMaintenanceTask();
        // update UI
        updateUI();
    }


    /* Description: handler for the move up button
     * Params: view - the view this button is in
     */
    public void handleUpButton(View view) {
        // call move with a y value of -1
        this.game.move(0, -1);
        // perform maintenance task
        stateMachine.doMaintenanceTask();
        // update UI
        updateUI();
    }


    /* Description: handler for the floating action button
     * Params: view - the view this button is in
     */
    public void handleFABControlClick(View view) {
        // show the fab menu if it's not open
        if (!isFABOpen)
            showFABMenu();
        else // hide the fab menu if it is open
            hideFABMenu();
    }


    /* Description: handler for the tint option 1 button
     * Params: view - the view this button is in
     */
    public void handleTintOptionOneClick(View view) {
        // restore the hamster to its original tint
        gameView.setTintFlag(false);
        // tell android to re-draw the canvas
        gameView.invalidate();
    }


    /* Description: handler for the tint option 2 button
     * Params: view - the view this button is in
     */
    public void handleTintOptionTwoClick(View view) {
        // set the tint flag to true
        gameView.setTintFlag(true);
        // set the tint color
        gameView.setTintColor("#2587be");
        // tell android to re-draw the canvas
        gameView.invalidate();
    }


    /* Description: handler for the tint option 3 button
     * Params: view - the view this button is in
     */
    public void handleTintOptionThreeClick(View view) {
        // set the tint flag to true
        gameView.setTintFlag(true);
        // set the tint color
        gameView.setTintColor("#6c25be");
        // tell android to re-draw the canvas
        gameView.invalidate();
    }


    /* Description: gets the state machine
     * Returns: the state machine associated with this mainActivity
     */
    public StateMachine getStateMachine() {
        return stateMachine;
    }


    /* Description: sets the background color of the mainActivity
     * Params: newColor - the color to set the background to
     */
    public void setBackgroundColor(int newColor) {
        // set the background color variable to the new color
        this.backgroundColor = newColor;
        // locate the root layout in the XML
        ConstraintLayout rootLayout = findViewById(R.id.root_layout);
        // set the background color
        rootLayout.setBackgroundColor(newColor);
    }


    /* Description: updates the UI text fields and then calls invalidate to tell android to re-draw the canvas
     */
    public void updateUI() {
        // get and set the moves text
        TextView movesText = findViewById(R.id.moves);
        movesText.setText(String.valueOf(this.game.getMoves()));

        // get and set the energy text
        TextView energyText = findViewById(R.id.energy);
        energyText.setText(String.valueOf(this.game.getEnergy()));

        // get and set the stores text
        TextView storesText = findViewById(R.id.stores);
        storesText.setText(String.valueOf(this.game.getHomeStores()));

        // get and set the zoom text
        TextView zoomText = findViewById(R.id.zoom);
        zoomText.setText(String.valueOf(this.game.getZoomsLeft()));

        // get and set the food text
        TextView foodText = findViewById(R.id.food);
        foodText.setText(String.valueOf(this.game.getFood()));

        // disable / enable the zoom button based on whether or not there are zooms left in the game
        findViewById(R.id.zoomBtn).setEnabled(game.getZoomsLeft() > 0);

        // call invalidate to tell android to re-draw the canvas
        gameView.invalidate();
    }


    /* Description: opens the floating action button menu
     */
    public void showFABMenu() {
        // locate the children buttons
        FloatingActionButton option1 = findViewById(R.id.option1);
        FloatingActionButton option2 = findViewById(R.id.option2);
        FloatingActionButton option3 = findViewById(R.id.option3);

        // set isFABOpen to true because we're opening it right now
        isFABOpen = true;

        // animate buttons opening
        option1.animate().translationY(55);
        option2.animate().translationY(105);
        option3.animate().translationY(155);
    }


    /* Description: hides the floating action button menu
     */
    public void hideFABMenu() {
        // locate the children buttons
        FloatingActionButton option1 = findViewById(R.id.option1);
        FloatingActionButton option2 = findViewById(R.id.option2);
        FloatingActionButton option3 = findViewById(R.id.option3);

        // set isFABOpen to false because we're opening it right now
        isFABOpen = false;

        // animate buttons closing
        option1.animate().translationY(0);
        option2.animate().translationY(0);
        option3.animate().translationY(0);
    }


    /* Description: shows the ending dialog once the game is complete
     * Params: message - the message to be displayed within the dialog
     */
    public void showEndingDialog(String message) {
        // create the dialog box builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the message for the dialog box
        builder.setMessage(message);
        // add a button to the dialog box
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // reset the game once the user clicks reset in the dialog box
                game.reset();
                updateUI();
            }
        });
        // create and show the dialog box
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /* Description: sets the background to be red
     * Params: view - the current view in which to set the background to red
     */
    public void setRedBackground(View view) {
        this.setBackgroundColor(Color.RED);
    }

    /* Description: sets the background to be gray
     * Params: view - the current view in which to set the background to gray
     */
    public void setGrayBackground(View view) {
        this.setBackgroundColor(Color.LTGRAY);
    }
}
