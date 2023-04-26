package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Custom GameView class for the hamster run project
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class GameView extends View {

    // private variables
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private final Bitmap hamsterImage = BitmapFactory.decodeResource(getResources(), R.drawable.hamster);
    private Game game;
    private Paint paint;
    private String tintColor = "";
    private boolean tintFlag = false;
    private int[][][] gradients = {
            { // gradient for first row
                    { Color.BLUE, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.BLACK, Color.WHITE },
                    { Color.YELLOW, Color.WHITE },
                    { Color.BLUE, Color.WHITE }
            },
            { // gradient for second row
                    { Color.YELLOW, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.GREEN, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.BLUE, Color.WHITE }
            },
            { // gradient for third row
                    { Color.BLUE, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.YELLOW, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.BLUE, Color.WHITE }
            },
            { // gradient for fourth row
                    { Color.YELLOW, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.RED, Color.WHITE },
                    { Color.BLACK, Color.WHITE },
                    { Color.BLACK, Color.WHITE }
            },
            { // gradient for fifth row
                    { Color.GREEN, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.BLACK, Color.WHITE },
                    { Color.BLUE, Color.WHITE },
                    { Color.MAGENTA, Color.WHITE }
            }
    };


    /* Description: Constructor for GameView that only takes in context and forwards to init
     * Params: context - the current game context
     */
    public GameView(Context context) {
        super(context);
        init(context);
    }

    /* Description: Constructor for GameView
     * Params: context - the current game context
     *         attrs - attributeSet for the current game
     */
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /* Description: Constructor for GameView
     * Params: context - the current game context
     *         attrs - attributeSet for the current game
     *         defStyleAttr - integer describing the number of style attributes
     */
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /* Description: init for the custom GameView, the function that is forwarded to by the constructors
     * Params: context - the current game context
     */
    public void init(Context context) {
        game = new Game();
        paint = new Paint();
    }


    /* Description: gets the game in this gameView
     * Returns: the current game object
     */
    public Game getGame() {
        return game;
    }


    /* Description: gets the current tint color of the hamster
     * Returns: the current tint color of the hamster
     */
    public String getTintColor() {
        return this.tintColor;
    }


    /* Description: gets whether the hamster is tinted right now
     * Returns: true if the hamster is tinted, false if the hamster is not tinted
     */
    public boolean getTintFlag() {
        return this.tintFlag;
    }


    /* Description: onDraw for the custom gameView
     * Params: canvas - the canvas on which to perform the drawing actions
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // first, perform super from the @override
        super.onDraw(canvas);

        // determine the screen width and height to determine which one's bigger
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = Math.round(displayMetrics.heightPixels - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, getResources().getDisplayMetrics()));

        // take the minimum of the screenWidth and screenHeight to determine the smaller one
        int screenReference = Math.min(screenWidth, screenHeight);

        // whichever one's smaller, divide that by 5 for the size of each square
        int squareSize = screenReference / 5;

        // draw the board
        int x_ = 0, y_ = 0;
        for (int[][] gradient : gradients) {
            for (int[] colors : gradient) {
                paint.setShader(new LinearGradient(x_, y_, x_ + squareSize, y_ + squareSize, colors, null, Shader.TileMode.CLAMP));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(x_, y_, x_ + squareSize, y_ + squareSize, paint);
                x_ += squareSize;
            }
            x_ = 0;
            y_ += squareSize;
        }

        // draw the hamster
        float size = (float)Math.max(getWidth(), getHeight());
        size = size * 0.15f;
        //calculate grid area width and height
        int areaWid = getWidth()/Game.GRID_SIZE;
        int areaHit = getHeight()/Game.GRID_SIZE;
        //calculate hamster target size
        float imageSizeW = hamsterImage.getWidth();
        float imageSizeH = hamsterImage.getHeight();
        Location hamsterLocation = game.getLocation();
        Point loc = new Point(hamsterLocation.x, hamsterLocation.y);
        float scaleFactor = size/Math.max(imageSizeW, imageSizeH);
        // get x and y center location
        float x = loc.x*areaWid + areaWid/2 - imageSizeW/2*scaleFactor;
        float y = loc.y*areaHit+areaHit/2-imageSizeH/2*scaleFactor;
        //use graphics matrix to place hamster correctly
        canvas.save();
        canvas.translate(x,y);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(hamsterImage,0,0, paint);
        // if the hamster needs to be tinted, perform the tint with the function tintHamster
        if(tintFlag)
            tintHamster(canvas, Color.parseColor(this.tintColor));
        canvas.restore();
    }


    /* Description: sets the current tint color for the hamster
     * Params: tintColor - the new tint color for the hamster
     */
    public void setTintColor(String tintColor) {
        this.tintColor = tintColor;
    }


    /* Description: sets the tint flag which indicates whether the hamster should be tinted currently
     * Params: flag - the new value for the tintFlag
     */
    public void setTintFlag(boolean flag) {
        this.tintFlag = flag;
    }


    /* Description: tints the hamster with the selected color
     * Params: canvas - the canvas on which to draw the tinted hamster
     *         color - the color to tint the hamster with
     */
    public void tintHamster(Canvas canvas, int color) {
        // create a new bitmap with the same dimensions as the original hamsterImage
        Bitmap tintedBitmap = Bitmap.createBitmap(hamsterImage.getWidth(), hamsterImage.getHeight(), Bitmap.Config.ARGB_8888);

        // create a new canvas to draw the tinted bitmap
        Canvas tintedCanvas = new Canvas(tintedBitmap);

        // apply the color filter to the hamsterImage and draw it on the tinted canvas
        Paint tintPaint = new Paint();
        tintPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        tintedCanvas.drawBitmap(hamsterImage, 0, 0, tintPaint);

        // draw the tinted bitmap on the original canvas
        canvas.drawBitmap(tintedBitmap, 0, 0, paint);
    }
}
