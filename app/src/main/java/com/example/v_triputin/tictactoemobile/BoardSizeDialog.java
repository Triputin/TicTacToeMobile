package com.example.v_triputin.tictactoemobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


// !!!Этот класс не используется в коде. Используется только макет в board_size_dialog.xml !!!
// Возможно его надо удалить если не найдется применение
public class BoardSizeDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        /* DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int id) {
                dialog.cancel();
            }

        };

        Button button =  findViewById(R.id.buttonOk);

        button.setOnClickListener(listener);


*/

        //int gameSize = getArguments().getInt("GameSize");



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.board_size_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }
}

