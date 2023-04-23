package edu.sdsmt.hamsterrun_johnson_noah;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {

    private final Bitmap hamsterImage = BitmapFactory.decodeResource(getResources(), R.drawable.hamster);
    private Game game;
    private Paint paint;
    private final int squareSize = 80;

    private int[][] colors = {
            { Color.BLUE, Color.BLUE, Color.BLACK, Color.YELLOW, Color.BLUE },
            { Color.YELLOW, Color.BLUE, Color.GREEN, Color.BLUE, Color.BLUE },
            { Color.BLUE, Color.BLUE, Color.YELLOW, Color.BLUE, Color.BLUE },
            { Color.YELLOW, Color.BLUE, Color.RED, Color.BLACK, Color.BLACK },
            { Color.GREEN, Color.BLUE, Color.BLACK, Color.BLUE, Color.LTGRAY }
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
        int x = 0, y = 0;
        for (int[] color : colors) {
            for (int i : color) {
                paint.setColor(i);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(x, y, x + squareSize, y + squareSize, paint);

                // Add an outline
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                canvas.drawRect(x, y, x + squareSize, y + squareSize, paint);

                x += squareSize;
            }
            x = 0;
            y += squareSize;
        }
        drawHamster(canvas);
    }

    private void drawHamster(Canvas c) {
        //get the smaller dimension
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
        c.save();
        c.translate(x,y);
        c.scale(scaleFactor, scaleFactor);
        c.drawBitmap(hamsterImage,0,0, paint); // originally "paint" was hamsterPaint?
        c.restore();
    }

    public Game getGame() {
        return game;
    }
}
