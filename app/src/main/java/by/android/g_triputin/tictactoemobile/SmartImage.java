package by.android.g_triputin.tictactoemobile;

import android.content.Context;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;

import by.android.g_triputin.tictactoemobile.R;

public class SmartImage extends AppCompatImageView {
    private TicTacToe state;

    public SmartImage(Context context) {
        super(context);
        state = TicTacToe.Empty;
        setImageResource(R.drawable.empty);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public TicTacToe getState() {
        return state;
    }


    public void setState(TicTacToe state,int skinId) {
        this.state = state;
        switch (skinId) {
            case 0:
                switch (state) {
                    case Zero:
                        setImageResource(R.drawable.red_circle);
                        break;
                    case Cross:
                        setImageResource(R.drawable.red_cross);
                        break;
                    case Empty:
                        setImageResource(R.drawable.empty);
                        break;

                }
                break;
            case 1:
            switch (state) {
                case Zero:
                    setImageResource(R.drawable.rooster);
                    break;
                case Cross:
                    setImageResource(R.drawable.unicorn);
                    break;
                case Empty:
                    setImageResource(R.drawable.empty);
                    break;

            }
            break;
        }
    }




}
