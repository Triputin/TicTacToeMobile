package by.android.g_triputin.tictactoemobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.Locale;
import android.content.res.Configuration;
import android.app.DialogFragment;
import android.view.View.OnClickListener;

import by.android.g_triputin.tictactoemobile.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private final int maxFieldSize=9; //restricts game field size
    private TicTacToe activePlayer = TicTacToe.Zero; // current player
    private  int gameSize=3; // default game size
    private static boolean isFirstStart = true; // flag to identify that drawfield is needed
    private SmartImage[][] board =  new SmartImage [maxFieldSize][maxFieldSize]; // array of field cells
    private boolean isGameOver=false;
   // 0 HumanGame , 1 Bot plays first , 2 bot plays second, 3 campain
    private int gameType=3;
    private static final String STATE_GAMESIZE = "GameSize";
    private static final String STATE_TEXT_LABEL = "Label";
    private static final String STATE_BOARD = "Board";
    private static final String STATE_ACTIVE_PLAYER = "ActivePlayer";
    private static final String STATE_IS_GAME_OVER = "IsGameOver";
    private static final String STATE_GAMETYPE = "GameType";
    private static final String STATE_ACTIVE_SKIN = "ActiveSkin";
    private static final String STATE_CURRENT_LEVEL = "CurrentLevel";
    static String[] skins = {"Default","RoosterVsPony"};
    static String[] languages = {"English","Русский"};
    static String[] gameTypeArrayEn = {"Two players","Bot plays first","Bot plays second","Campain"};
    static String[] gameTypeArrayRu = {"Два игрока","Бот играет первым","Бот играет вторым","Кампания"};
    private int activeSkin = 0;
    private int activeLanguage =0;
    private boolean blockPlayer = false;// Устанавливает на время запрет действий?, т.к. иначе компьютер ходит неестественно быстро
    DialogFragment dlg1;
    public static final int levelCount = 14;
    private LevelSettings[] levelSettings;
    private AdView mAdView;

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        TextView txtvwLevel = (TextView) findViewById(R.id.content_main_Textview);
        txtvwLevel.setText(Integer.toString(currentLevel+1));
    }

    private int currentLevel = 0;

    public LevelSettings[] createLevelMap(){
        LevelSettings[] levelSettings = new LevelSettings[levelCount];
        int trigger = 1;
        for(int i =0;i<levelCount;i++){

            levelSettings[i] =new LevelSettings( (int)Math.ceil(((double)i+1)/2)+2 ,trigger);
            if(trigger==1){trigger=2; }else {trigger=1;}
        }
        return levelSettings;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        // если пришло ОК
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    int gameSizeNew = data.getIntExtra("gameSize", 3);
                    if(gameSize!= gameSizeNew){
                        if(gameSizeNew<3){
                            gameSizeNew = 3;
                        }
                        if(gameSizeNew>maxFieldSize){
                            gameSizeNew = maxFieldSize;
                        }
                        gameSize=gameSizeNew;

                        changeBordSize();
                    }
                    int activeLanguageNew = data.getIntExtra("language", 0);
                    if(activeLanguage!=activeLanguageNew){
                        activeLanguage=activeLanguageNew;
                        changeLanguage(activeLanguage);
                    }
                    int activeSkinNew = data.getIntExtra("skin", 0);
                    if(activeSkin!=activeSkinNew){
                        activeSkin=activeSkinNew;
                        changeSkin(activeSkin);
                    }
                    int activeGameTypeNew = data.getIntExtra("gameType", 2);
                    if(gameType!=activeGameTypeNew){
                        gameType=activeGameTypeNew;
                       setGameType(gameType);
                    }
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result 1", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putInt(STATE_GAMETYPE,gameType);
        outState.putInt(STATE_ACTIVE_SKIN,activeSkin);
        outState.putBoolean(STATE_IS_GAME_OVER,isGameOver);
        outState.putString(STATE_ACTIVE_PLAYER,activePlayer.toString());
        outState.putInt(STATE_CURRENT_LEVEL,currentLevel);
        String [] stringBoard =  new String [gameSize*gameSize];
        for(int i =0;i<gameSize;i++){
            for (int j = 0;j<gameSize;j++){
                stringBoard[i*gameSize+j]= board[i][j].getState().toString();
            }
        }
        outState.putStringArray(STATE_BOARD,stringBoard);
        outState.putInt(STATE_GAMESIZE,gameSize);
        super.onSaveInstanceState(outState);
    }


    public int getActiveLanguage() {
        return activeLanguage;
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameType = savedInstanceState.getInt(STATE_GAMETYPE);
        isGameOver = savedInstanceState.getBoolean(STATE_IS_GAME_OVER);
        activeSkin = savedInstanceState.getInt(STATE_ACTIVE_SKIN,activeSkin);
        activePlayer= TicTacToe.fromString(savedInstanceState.getString(STATE_ACTIVE_PLAYER));
        String [] stringBoard ;
        stringBoard= savedInstanceState.getStringArray(STATE_BOARD);
        gameSize = savedInstanceState.getInt(STATE_GAMESIZE);
        setCurrentLevel(  savedInstanceState.getInt(STATE_CURRENT_LEVEL));
        drawFieldWithImages();
        //drawField();
        for(int i =0;i<gameSize;i++){
            for (int j = 0;j<gameSize;j++){
                board[i][j].setState( TicTacToe.fromString(stringBoard[i*(gameSize)+j]),activeSkin);
            }
        }


    }


    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        SettingsLoader.Settings settings = new SettingsLoader.Settings();
        settings.activeSkin  = activeSkin;
        settings.gameSize = gameSize;
        settings.gameType = gameType;
        settings.activeLanguage = activeLanguage;

        SettingsLoader.saveAppSettings(this,settings);
    }
    */

    @Override
    protected void onStop() {

        SettingsLoader.Settings settings = new SettingsLoader.Settings();
        settings.activeSkin  = activeSkin;
        settings.gameSize = gameSize;
        settings.gameType = gameType;
        settings.activeLanguage = activeLanguage;
        settings.currentLevel = currentLevel;

        SettingsLoader.saveAppSettings(this,settings);
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);
        MobileAds.initialize(this, "ca-app-pub-9387110068169481~9239407726");
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button btnMenu = (Button) findViewById(R.id.content_main_Button_Menu);
        Button btnRestart = (Button) findViewById(R.id.content_main_Button_Restart);
        TextView txtvwLevel = (TextView) findViewById(R.id.content_main_Textview);
        // присвоим обработчик кнопке (btnMenu)
        btnMenu.setOnClickListener(this);
        btnRestart.setOnClickListener(this);


        SettingsLoader.Settings settings;
        //SettingsLoader settingsLoader = new SettingsLoader();
        settings = SettingsLoader.getAppSettings(this);
        setCurrentLevel(settings.currentLevel);
        activeSkin = settings.activeSkin;
        gameSize = settings.gameSize;
        gameType = settings.gameType;
        activeLanguage = settings.activeLanguage;
        changeLanguage(activeLanguage);
        levelSettings = createLevelMap();
        if(gameType==3){
            gameSize = levelSettings[currentLevel].getGameSize();
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(isFirstStart){
            drawFieldWithImages();
            changeSkin(activeSkin);

        }


       final View.OnClickListener restartlistener = new View.OnClickListener() {
            @Override
            public void onClick(View e) {
               restartGame();
                }
            };

      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
               public void onClick(View view) {
               Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                .setAction("Restart game", restartlistener).show();

            }
        });*/
        isFirstStart=false;

    }

    public void setGameType(int gameType){
        this.gameType = gameType;
        restartGame();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.content_main_Button_Menu:

                Intent intent = new Intent(this, Menu_Activity.class);

                intent.putExtra("gameSize", gameSize);
                intent.putExtra("language", activeLanguage);
                intent.putExtra("skin", activeSkin);
                intent.putExtra("gameType", gameType);
                intent.putExtra("CurrentLevel", currentLevel);

                startActivityForResult(intent, 1);
                break;
            case R.id.content_main_Button_Restart:
                Button btnRestart = (Button) findViewById(R.id.content_main_Button_Restart);
                btnRestart.setText(R.string.restart_game);
                restartGame();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showWinner(String winner){
        if(winner==""){
            winner=getResources().getString(R.string.draw);
        }
        else{
            winner=getResources().getString(R.string.winnerIs)+" "+winner+"!!!";
        }

        int duration = Toast.LENGTH_LONG;
        Toast toast2 = Toast.makeText(getApplicationContext(),
                winner,
                duration);
        LinearLayout toastLayout = (LinearLayout) toast2.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(30);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
    }

    public void showCampainWinner(){
        String winner;

            winner=getResources().getString(R.string.congratulations);

        int duration = Toast.LENGTH_LONG;
        Toast toast2 = Toast.makeText(getApplicationContext(),
                winner,
                duration);
        LinearLayout toastLayout = (LinearLayout) toast2.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(30);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
    }

    public TicTacToe getActivePlayer() {
        return activePlayer;
    }
    public   void changeBordSize(){
        clearField();
        drawFieldWithImages();

        restartGame();
    }
    public void changeSkin(int skinId){
        activeSkin = skinId;
        for(int i =0;i<gameSize;i++){
            for (int j = 0;j<gameSize;j++){
                board[i][j].setState(board[i][j].getState(),activeSkin);
            }
        }
        setBackGround();
    }

    public  void clearField(){
        TableLayout layout =(TableLayout) findViewById(R.id.tablelayout);
        layout.removeAllViews();
        for (int i=0;i<gameSize;i++){
            for (int j=0;j<gameSize;j++){

            }
        }

    }


// Create field using ImageViews
    public void drawFieldWithImages(){
        int margin=20/(gameSize-2);
        int  color = Color.parseColor("#2e33bd");

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout);
        //constraintLayout.setBackgroundColor(color);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        //tableLayout.setBackgroundColor(color);
        //tableLayout.setBackgroundResource(R.drawable.back_nature01);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                SmartImage smartImage = (SmartImage) e;
                if(isGameOver){
                    return;
                }
                if(blockPlayer){
                    return;
                }
                if(smartImage.getState()==TicTacToe.Empty) {
                    smartImage.setState(getActivePlayer(),activeSkin);
                    Handler handler = new Handler();
                    blockPlayer = true;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            OnAction();
                            blockPlayer = false;
                        }
                    }, 500);


                }
            }

        };

        setBackGround();

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width<height) {
            height = width;
        }
        else {
            width = height;
        }

        for (int i = 0; i < gameSize; i++) {

            TableRow tableRow = new TableRow(this);
            TableLayout.LayoutParams trp = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            //trp.setMargins(5,20,5,20);
            tableRow.setLayoutParams(trp);
            //tableRow.setGravity(Gravity.CENTER);
           //tableRow.setBackgroundColor(Color.parseColor("#ffffff"));
            //tableRow.setWeightSum(gameSize); //total row weight
            //tableRow.setMinimumHeight(300/gameSize);
            //tableRow.setBackgroundResource(R.drawable.diamond);

            for (int j = 0; j < gameSize; j++) {
                SmartImage smartImage = new SmartImage(this);

                TableRow.LayoutParams lp;
                //lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                lp = new TableRow.LayoutParams(0, (height-400)/(gameSize), 1f);
                board [i][j] = smartImage;
                //smartImage.setPadding(margin,margin,margin,margin);
                lp.setMargins(margin,margin,margin,margin);
                smartImage.setBackgroundColor(Color.parseColor("#ffffff"));

                smartImage.setLayoutParams(lp);
                tableRow.addView(smartImage, j);
                smartImage.setOnClickListener(listener);
                //ViewGroup.LayoutParams params =  imageView.getLayoutParams();
                //ConstraintLayout constraintLayout = findViewById(R.id.layout);
                //params.width = (constraintLayout.getWidth()/gameSize);
                //params.height = (constraintLayout.getWidth()/gameSize);
                //imageView.setLayoutParams(params);
            }
            tableLayout.addView(tableRow, i);
        }

    }


    public void OnAction () {
        if (activePlayer==TicTacToe.Cross) {
            activePlayer = TicTacToe.Zero;
        }else {
            activePlayer=TicTacToe.Cross;
        }

        checkDraw();

        if((!isGameOver)&&(gameType==1)||(!isGameOver)&&(gameType==2)||(!isGameOver)&&(gameType==3)) {
            if (activePlayer == TicTacToe.Cross) {
                AI().setState(TicTacToe.Cross,activeSkin);
                activePlayer = TicTacToe.Zero;
                checkDraw();
            }
        }


    }

    public SmartImage AI(){
        //Check Our Win Turn
        for(int x=0; x<gameSize-2;x++){
            for(int y=0; y<gameSize-2;y++){
                SmartImage smartImage=checkPairs(TicTacToe.Cross,x,y);
                if(smartImage!=null) {
                    return smartImage;
                }
            }
        }
        //Check Our Lose Turn
        for(int x=0; x<gameSize-2;x++){
            for(int y=0; y<gameSize-2;y++){
                SmartImage smartImage=checkPairs(TicTacToe.Zero,x,y);
                if(smartImage!=null) {
                    return smartImage;
                }
            }
        }
        //General Turn
        for (int i=0;i<gameSize;i++) {
            for (int j=0;j<gameSize;j++){
                if (board[i][j].getState()==TicTacToe.Empty) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public boolean globalCheckWin(){
        boolean win=false;
        for (int i=0;i<(gameSize-2);i++){
            for (int j=0;j<(gameSize-2);j++){
                win = checkWin(i,j);
                if(win){
                    return win;
                }
            }
        }
        return win;
    }
    public boolean checkWin(int x,int y){
        //Check rows
        for (int i=0;i<3;i++){
            if(board[i+y][x].getState()!=TicTacToe.Empty){
                int win = 1;
                for (int j=1;j<3;j++){
                    if(board[i+y][x+j].getState()==board[i+y][x].getState()){
                        win++;
                    }

                }
                if(win==3){
                    showWinner(board[i+y][x].getState().toString());
                    System.out.println("x="+x+" y="+y);
                    SmartTableLayout smartTableLayout = findViewById(R.id.tablelayout);
                    smartTableLayout.setWinLine(WinLineTypes.Horizontal,board[i+y][x],board[i+y][x+2],i+y,i+y,true);

                    return true;
                }
            }
        }
        // Check columns
        for (int i=0;i<3;i++){
            if(board[y][i+x].getState()!=TicTacToe.Empty){
                int win = 1;
                for (int j=1;j<3;j++){
                    if(board[j+y][i+x].getState()==board[y][i+x].getState()){
                        win++;
                    }

                }
                if(win==3){
                    showWinner(board[y][i+x].getState().toString());
                    SmartTableLayout smartTableLayout = findViewById(R.id.tablelayout);
                    smartTableLayout.setWinLine( WinLineTypes.Vertical ,board[y][i+x],board[2+y][i+x], y,2+y,true);
                    return true;
                }
            }
        }
        if (board[y][x].getState()!=TicTacToe.Empty){
            if((board[y][x].getState()==board[y+1][x+1].getState())&&(board[y+1][x+1].getState()==board[y+2][x+2].getState())){
                showWinner(board[y][x].getState().toString());
                SmartTableLayout smartTableLayout = findViewById(R.id.tablelayout);
                smartTableLayout.setWinLine(WinLineTypes.LeftUpToRightDown, board[y][x],board[y+2][x+2],y,y+2,true);

                return true;
            }

        }
        if (board[y][x+2].getState()!=TicTacToe.Empty){
            if((board[y][x+2].getState()==board[y+1][x+1].getState())&&(board[y+1][x+1].getState()==board[y+2][x].getState())){
                showWinner(board[y][x+2].getState().toString());
                SmartTableLayout smartTableLayout = findViewById(R.id.tablelayout);
                smartTableLayout.setWinLine(WinLineTypes.LeftDownToRightUp, board[y+2][x],board[y][x+2],y+2, y, true);

                return true;
            }

        }
        return false;
    }

    public void restartGame(){
        SmartTableLayout smartTableLayout = findViewById(R.id.tablelayout);
        // forbid line draw
        smartTableLayout.setWinLine(WinLineTypes.Horizontal, board[0][0],board[0][0],1,1,false);

        switch (gameType) {
            case 0:  onHumanGame();
                break;
            case 1:  onBotGameX();
                break;
                case 2: onBotGame0();
                break;
                case 3: onCampainGame();
                break;
            default:
                break;
        }
    }

    public void onHumanGame(){
        gameType=0;
        activePlayer=TicTacToe.Cross;
        for (int i=0;i<gameSize;i++){

            for (int j=0;j<gameSize;j++){
                board[i][j].setState(TicTacToe.Empty,activeSkin);
            }
        }
        isGameOver=false;
    }
    public void onBotGameX(){
        gameType=1;
        activePlayer=TicTacToe.Cross;
        for (int i=0;i<gameSize;i++){

            for (int j=0;j<gameSize;j++){
                board[i][j].setState(TicTacToe.Empty,activeSkin);
            }
        }
        isGameOver=false;
        activePlayer = TicTacToe.Zero;
        board[1][1].setState(TicTacToe.Cross,activeSkin);

    }
    public void onBotGame0(){
        gameType=2;
        activePlayer=TicTacToe.Zero;
        for (int i=0;i<gameSize;i++){

            for (int j=0;j<gameSize;j++){
                board[i][j].setState(TicTacToe.Empty,activeSkin);
            }
        }
        isGameOver=false;

    }
    public void onCampainGame(){
        gameType=3;
        clearField();
        gameSize = levelSettings[currentLevel].getGameSize();
        drawFieldWithImages();
        isGameOver=false;
        activePlayer = TicTacToe.Zero;
        if(levelSettings[currentLevel].getGameType()==1) {
            board[1][1].setState(TicTacToe.Cross, activeSkin);
        }
    }
    public void checkDraw(){
        if(globalCheckWin()){
            if(gameType==3){
               if(currentLevel == levelCount){
                   isGameOver=true;
               }else{
                   if(activePlayer == TicTacToe.Cross){
                       currentLevel++;
                       setCurrentLevel(currentLevel);
                       if(currentLevel==14){
                           showCampainWinner();
                          setCurrentLevel(0);
                       }
                   }
                 //  restartGame();
                   isGameOver = true;
                   Button btnRestart = (Button) findViewById(R.id.content_main_Button_Restart);
                   btnRestart.setText(R.string.continue_);
               }

            }else {
                isGameOver = true;
            }
            return;
        }
        //check empty cell
        for (int i=0;i<gameSize;i++) {
            for (int j = 0; j < gameSize; j++) {
                if(board[i][j].getState()==TicTacToe.Empty){
                    return;
                }

            }
        }
        //draw

            isGameOver = true;

        showWinner("");
    }
public void getChangeLanguage(Context context){
    LayoutInflater li = LayoutInflater.from(context);
    View languageView = li.inflate(R.layout.dialog_language, null);

    //Создаем AlertDialog
    AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

    //Настраиваем board_size_dialog.xml для нашего AlertDialog:
    mDialogBuilder.setView(languageView);

    //Настраиваем отображение поля для ввода текста в открытом диалоге:
    final ListView listView = (ListView) languageView.findViewById(R.id.listViewLanguage);

    // создаем адаптер
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, languages);

    // присваиваем адаптер списку
    listView.setAdapter(adapter);
    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    listView.setItemsCanFocus(true);
    listView.setItemChecked(activeLanguage,true);
    //listView.setSelection(activeSkin);



    //Настраиваем сообщение в диалоговом окне:
    mDialogBuilder
            .setCancelable(false)
            .setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int i = listView.getCheckedItemPosition();
                            changeLanguage(i);

                        }
                    })
            .setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

    //Создаем AlertDialog:
    AlertDialog alertDialog = mDialogBuilder.create();
    //и отображаем его:
    alertDialog.show();

}
public void changeLanguage(int languageId){
        switch (languageId) {
            case 0:
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
            setTitle(R.string.app_name);

            break;
            case 1:
                Locale locale1 = new Locale("ru");
                Locale.setDefault(locale1);
                Configuration configuration1 = new Configuration();
                configuration1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(configuration1, null);
                setTitle(R.string.app_name);
                break;
        }

}
    public  SmartImage checkPairs(TicTacToe state, int x, int y ){
        SmartImage smartImage = board[0][0];
        int countCross = 0;
        int countEmpty = 0;
        //Check rows
        for (int i=0;i<3;i++){
            countCross = 0;
            countEmpty = 0;

            for (int j=0;j<3;j++){
                if(board[i+y][j+x].getState()==state){
                    countCross++;
                }
                if(board[i+y][j+x].getState()==TicTacToe.Empty){
                    countEmpty++;
                    smartImage=board[i+y][j+x];

                }

            }
            if((countCross==2)&&(countEmpty==1)){
                return smartImage;
            }
        }
        //Check columns

        for (int i=0;i<3;i++){
            countCross = 0;
            countEmpty = 0;
            for (int j=0;j<3;j++){
                if(board[j+y][i+x].getState()==state){
                    countCross++;
                }
                if(board[j+y][i+x].getState()==TicTacToe.Empty){
                    countEmpty++;
                    smartImage=board[j+y][i+x];

                }

            }
            if((countCross==2)&&(countEmpty==1)){
                return smartImage;
            }
        }
        //Diagonali 1
        countCross = 0;
        countEmpty = 0;

        for (int i=0;i<3;i++) {
            if(board[i+y][i+x].getState()==state){
                countCross++;
            }
            if(board[i+y][i+x].getState()==TicTacToe.Empty){
                countEmpty++;
                smartImage=board[i+y][i+x];

            }
            if((countCross==2)&&(countEmpty==1)){
                return smartImage;

            }

        }
        //Diagonali 2
        countCross = 0;
        countEmpty = 0;
        for (int i=0;i<3;i++) {

            if(board[i+y][2-i+x].getState()==state){
                countCross++;
            }
            if(board[i+y][2-i+x].getState()==TicTacToe.Empty){
                countEmpty++;
                smartImage=board[i+y][2-i+x];

            }
            if((countCross==2)&&(countEmpty==1)){
                return smartImage;

            }

        }


        return null;
    }
    public void setBackGround() {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout);
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width < height) {
            switch (activeSkin) {
                case 0:
                    constraintLayout.setBackgroundResource(R.drawable.brick01);
                    break;
                case 1:
                    constraintLayout.setBackgroundResource(R.drawable.back_nature01);
                    break;

            }
        }
        else{
                switch (activeSkin) {
                    case 0:
                        constraintLayout.setBackgroundResource(R.drawable.brick02);
                        break;
                    case 1:
                        constraintLayout.setBackgroundResource(R.drawable.back_nature02);
                        break;

                }

            }
        }



}
