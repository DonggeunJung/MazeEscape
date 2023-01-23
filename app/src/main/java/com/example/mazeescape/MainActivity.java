package com.example.mazeescape;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    JGameLib gameLib = null;
    JGameLib.Card cardAvatar = null;
    int[][] grid = {{0,0,0,0,0},{1,1,1,1,0},{1,0,0,0,0},{0,1,1,1,0},{0,0,0,0,0},{0,1,0,1,1},{0,1,0,0,2}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLib = findViewById(R.id.gameLib);
        initGame();
    }

    @Override
    protected void onDestroy() {
        if(gameLib != null)
            gameLib.clearMemory();
        super.onDestroy();
    }

    private void initGame() {
        int rows = grid.length, cols = grid[0].length;
        gameLib.setScreenGrid(cols,rows);
        for(int y=0; y < rows; y++) {
            for(int x=0; x < cols; x++) {
                int res = R.drawable.img_back;
                switch(grid[y][x]) {
                    case 1: res = R.drawable.img_block; break;
                    case 2: res = R.drawable.img_house_empty; break;
                }
                gameLib.addCard(res, x, y, 1, 1);
            }
        }
        cardAvatar = gameLib.addCard(R.drawable.img_push_man, 0, 0, 1, 1);
        gameLib.playBGM(R.raw.morning);
    }

    // User Event start ====================================

    public void onBtn(View v) {
        int left = (int)cardAvatar.screenRect().left;
        int top = (int)cardAvatar.screenRect().top;
        switch(v.getId()) {
            case R.id.btnUp: {
                if(top == 0 || grid[top-1][left] == 1) return;
                top --;
                break;
            }
            case R.id.btnDown: {
                if(top == grid.length-1 || grid[top+1][left] == 1) return;
                top ++;
                break;
            }
            case R.id.btnLeft: {
                if(left == 0 || grid[top][left-1] == 1) return;
                left --;
                break;
            }
            default: {
                if(left == grid[0].length-1 || grid[top][left+1] == 1) return;
                left ++;
                break;
            }
        }
        cardAvatar.move(left, top);
        if(grid[top][left] == 2) {
            gameLib.playAudioBeep(R.raw.trumpet_fanfare);
            gameLib.popupDialog(null, "You succeeded passing this maze.", "Close");
        }
    }

    // User Event end ====================================

}