package com.example.v_triputin.tictactoemobile;

import android.view.View;
import android.widget.Button;

public class SmartButton {
    //private TicTacToe state = TicTacToe.Empty;
    private Button button ;

    public SmartButton( Button button) {
        this.button = button;
        this.button.setText(getStateFromButton().toString());

    }

    public void setOnClickListener(View.OnClickListener listener){
        button.setOnClickListener(listener);
    }

    public void setState(TicTacToe state) {
        this.button.setText(state.toString());
    }

    public TicTacToe getState() {

        return getStateFromButton();
    }

    public TicTacToe getStateFromButton() {
        if(button.getText()=="X"){
            return TicTacToe.Cross;
        }
        if(button.getText()=="0"){
            return TicTacToe.Zero;
        }
        return TicTacToe.Empty;
    }
}
