package com.example.v_triputin.tictactoemobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

public class SmartTableLayout extends TableLayout {

    boolean isLineNeeded = true;
    Point pointStart = new Point(0,0);
    Point pointEnd = new Point(0,0);
    Paint paint = new Paint();

    public SmartTableLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public SmartTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isLineNeeded){
           View view = getChildAt(0);
            paint.setStrokeWidth(view.getHeight()/6);
            paint.setColor(Color.RED);
            canvas.drawLine(pointStart.x,pointStart.y,pointEnd.x,pointEnd.y,paint);
        }
    }


    public void setWinLine(WinLineTypes winLineType, SmartImage smartImageStart, SmartImage smartImageEnd, int startRowNumber, int endRowNumber, boolean isLineNeeded) {
        // Allow win line drawing
        this.isLineNeeded = isLineNeeded;
        // Find out row height. Child is a TableRow.
        View view = getChildAt(0);
        int rowHeight = view.getHeight();

        switch (winLineType) {
            case Horizontal:
                this.pointStart.set(smartImageStart.getLeft(), smartImageStart.getTop()+ smartImageStart.getHeight() / 2+ startRowNumber*rowHeight);
                this.pointEnd.set(smartImageEnd.getRight(), smartImageStart.getTop()+ smartImageStart.getHeight() / 2+ startRowNumber*rowHeight);
                break;

            case Vertical:
                break;
            case LeftUpToRightDown:
                break;
            case LeftDownToRightUp:
                break;

        }

    }
}

