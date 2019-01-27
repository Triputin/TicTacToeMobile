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
    final int lineMargin = 10; //отступ для линии при рисовании

    public SmartTableLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public SmartTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }
//рисует для одиночных объектов, для групповых не вызывается
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    //рисует для групповых объектов
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isLineNeeded){
           View view = getChildAt(0);
            paint.setStrokeWidth(view.getHeight()/6);
            paint.setColor(Color.rgb(210,0,100));
            canvas.drawLine(pointStart.x,pointStart.y,pointEnd.x,pointEnd.y,paint);
        }
    }

    //устанавливает, выводить или нет линию и ее координаты

    public void setWinLine(WinLineTypes winLineType, SmartImage smartImageStart, SmartImage smartImageEnd, int startRowNumber, int endRowNumber, boolean isLineNeeded) {
        // Allow win line drawing
        this.isLineNeeded = isLineNeeded;
        // Find out row height. Child is a TableRow.
        View view = getChildAt(0);
        int rowHeight = view.getHeight();

        switch (winLineType) {
            case Horizontal:
                this.pointStart.set(smartImageStart.getLeft()+lineMargin, smartImageStart.getTop()+ smartImageStart.getHeight() / 2+ startRowNumber*rowHeight);
                this.pointEnd.set(smartImageEnd.getRight()-lineMargin, smartImageStart.getTop()+ smartImageStart.getHeight() / 2+ startRowNumber*rowHeight);
                this.invalidate(); //сообщает, что надо перерисовать SmartTableLayout
                break;

            case Vertical:
                this.pointStart.set(smartImageStart.getLeft()+smartImageStart.getWidth()/2 ,smartImageStart.getTop()+lineMargin+startRowNumber*rowHeight);
                this.pointEnd.set(smartImageEnd.getLeft()+smartImageEnd.getWidth()/2 ,smartImageEnd.getBottom()-lineMargin+endRowNumber*rowHeight);
                this.invalidate(); //сообщает, что надо перерисовать SmartTableLayout
                break;
            case LeftUpToRightDown:
                this.pointStart.set(smartImageStart.getLeft()+lineMargin,smartImageStart.getTop()+lineMargin+startRowNumber*rowHeight);
                this.pointEnd.set(smartImageEnd.getRight()-lineMargin,smartImageEnd.getBottom()-lineMargin+endRowNumber*rowHeight);
                this.invalidate(); //сообщает, что надо перерисовать SmartTableLayout
                break;
            case LeftDownToRightUp:
                this.pointStart.set(smartImageStart.getLeft()+lineMargin,smartImageStart.getBottom()-lineMargin+startRowNumber*rowHeight);
                this.pointEnd.set(smartImageEnd.getRight()-lineMargin,smartImageEnd.getTop()+lineMargin+endRowNumber*rowHeight);
                this.invalidate(); //сообщает, что надо перерисовать SmartTableLayout
                break;

        }

    }
}

