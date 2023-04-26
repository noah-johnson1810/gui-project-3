package edu.sdsmt.hamsterrun_johnson_noah;

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

    private final Bitmap hamsterImage = BitmapFactory.decodeResource(getResources(), R.drawable.hamster);
    private Game game;
    private Paint paint;
    private String tintColor = "";

    private boolean tintFlag = false;
    DisplayMetrics displayMetrics = new DisplayMetrics();

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

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        game = new Game();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = Math.round(displayMetrics.heightPixels - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, getResources().getDisplayMetrics()));
        int screenReference = Math.min(screenWidth, screenHeight);
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
        if(tintFlag)
            tintHamster(canvas, Color.parseColor(this.tintColor));
        canvas.restore();
    }

    public Game getGame() {
        return game;
    }

    public void tintHamster(Canvas canvas, int color) {
        // Create a new bitmap with the same dimensions as the original hamsterImage
        Bitmap tintedBitmap = Bitmap.createBitmap(hamsterImage.getWidth(), hamsterImage.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a new canvas to draw the tinted bitmap
        Canvas tintedCanvas = new Canvas(tintedBitmap);

        // Apply the color filter to the hamsterImage and draw it on the tinted canvas
        Paint tintPaint = new Paint();
        tintPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        tintedCanvas.drawBitmap(hamsterImage, 0, 0, tintPaint);

        // Draw the tinted bitmap on the original canvas
        canvas.drawBitmap(tintedBitmap, 0, 0, paint);
    }

    public void setTintFlag(boolean flag) {
        this.tintFlag = flag;
    }

    public boolean getTintFlag() {
        return this.tintFlag;
    }

    public void setTintColor(String tintColor) {
        this.tintColor = tintColor;
    }

    public String getTintColor() {
        return this.tintColor;
    }
}
