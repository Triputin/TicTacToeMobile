package by.android.g_triputin.tictactoemobile;

public enum TicTacToe {
    Cross,Zero,Empty;

    @Override
    public String toString() {
        String s;
        switch (this) {
            case Cross:  s = "X";
                break;
            case Zero:  s = "0";
                break;
            default: s = " ";
                break;
        }
        return s;
    }

    public static TicTacToe fromString(String s) {
        TicTacToe n;
        switch (s) {
            case "X":  n = TicTacToe.Cross;
                break;
            case "0":  n = TicTacToe.Zero;
                break;
            default: n = TicTacToe.Empty;
                break;
        }
        return n;
    }
}