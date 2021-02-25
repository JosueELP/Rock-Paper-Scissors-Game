package com.example.rockpaper_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;

public class GameActivity extends AppCompatActivity {

    int combinations[][] = {{0, -1, 1, 1, -1},
                      {1, 0, -1, -1, 1},
                      {-1, 1, 0, 1, -1},
                      {-1, 1, -1, 0, 1},
                      {1, -1, 1, -1, 0}}; //Matrix with all posibles combinations
    int selecciones[] = {0,0,0,0,0}; // 0-Rock, 1-Paper, 2-Scissors, 3-Lizard, 4-Spock
    int tempSelect = -1; //To contain the posible selection of the player
    int movCpu = 0; //To contain the posible selection of the computer
    int randomMovements = 5; //The total random numbers in orden to the cpu to learn
    int tieds = 0, wins = 0, fails = 0;

    //Views
    TextView tvSelect, tvResultado, tvCpu, tvTied, tvJugador;
    ImageView ivPlayer, ivCpu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initiate components
        tvSelect = (TextView) findViewById(R.id.tvSelect);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        tvCpu = (TextView) findViewById(R.id.tvCpu);
        tvTied = (TextView) findViewById(R.id.tvTied);
        tvJugador = (TextView) findViewById(R.id.tvJugador);
        ivPlayer = (ImageView) findViewById(R.id.ivPlayer);
        ivCpu = (ImageView) findViewById(R.id.ivCpu);


    }
    public void selectionRock(View view){
        tempSelect = 0;
        int id = getResources().getIdentifier("rock_icon", "drawable", getPackageName());
        ivPlayer.setImageResource(id); //Put rock icon on imageview
        tvSelect.setText(getString(R.string.text_roca));
    }
    public void selectionPaper(View view){
        tempSelect = 1;
        int id = getResources().getIdentifier("paper_icon", "drawable", getPackageName());
        ivPlayer.setImageResource(id); //Put rock icon on imageview
        tvSelect.setText(getString(R.string.text_papel));
    }
    public void selectionScissors(View view){
        tempSelect = 2;
        int id = getResources().getIdentifier("scissors_icon", "drawable", getPackageName());
        ivPlayer.setImageResource(id); //Put rock icon on imageview
        tvSelect.setText(getString(R.string.text_tijeras));
    }
    public void selectionLizard(View view){
        tempSelect = 3;
        int id = getResources().getIdentifier("lizard_icon", "drawable", getPackageName());
        ivPlayer.setImageResource(id); //Put rock icon on imageview
        tvSelect.setText(getString(R.string.text_lagarto));
    }
    public void selectionSpock(View view){
        tempSelect = 4;
        int id = getResources().getIdentifier("spock_icon", "drawable", getPackageName());
        ivPlayer.setImageResource(id); //Put rock icon on imageview
        tvSelect.setText(getString(R.string.text_spock));
    }

    public void jugar(View view){
        if(tempSelect == -1){ //If player didn't select anything
            tvResultado.setText(getString(R.string.text_selecciona));
        }else{
            computerGame();
            checkGame();
        }

    }

    public void computerGame(){
        if(randomMovements >= 0){
            movCpu = randomNumber();
            randomMovements--;
        }else{ //CPU MOVEMENT
            //Analize the movements of player
            //Get the most used game from player
            int max = 0;
            int cpuChoices[] = new int[2];
            for (int i = 1; i < selecciones.length; i++){
                if(selecciones[i] > selecciones[max]){
                    max = i;
                }
            }
            //Analize the matrix in order to choose a counter attack
            int choice = 0;
            for (int f = 0; f < 5; f++){
                if(combinations[max][f] == -1){
                    cpuChoices[choice] = f;
                    choice++;
                }
            }
            //Analize wich one of the counter attack choice is the less used by player
            int min = 0;
            if(selecciones[cpuChoices[0]] > selecciones[cpuChoices[1]]){
                min = cpuChoices[0];
            }else{
                min = cpuChoices[1];
            }
            //Make cpu movement
            movCpu = min;
        }
        //Store players movement
        selecciones[tempSelect]++;

        //Select the image for the selection
        int id = 0;
        switch (movCpu){
            case 0:
                id = getResources().getIdentifier("rock_icon", "drawable", getPackageName());
                break;
            case 1:
                id = getResources().getIdentifier("paper_icon", "drawable", getPackageName());
                break;
            case 2:
                id = getResources().getIdentifier("scissors_icon", "drawable", getPackageName());
                break;
            case 3:
                id = getResources().getIdentifier("lizard_icon", "drawable", getPackageName());
                break;
            case 4:
                id = getResources().getIdentifier("spock_icon", "drawable", getPackageName());
                break;
        }
        ivCpu.setImageResource(id);
    }

    public void checkGame(){
        if((tempSelect == 0 && movCpu == 0) || (tempSelect == 1 && movCpu == 1) || (tempSelect == 2 && movCpu == 2) || (tempSelect == 3 && movCpu == 3) || (tempSelect == 4 && movCpu == 4)){ //All tied that end in tied
            tvResultado.setText(getString(R.string.text_empate));
            tieds++;
            tvTied.setText(""+tieds);
        }else if((tempSelect == 0 && movCpu == 2) || (tempSelect == 0 && movCpu == 3)){ //All wins posible games for rock - Player
            setTextGanar();
        }else if((tempSelect == 1 && movCpu == 0) || (tempSelect == 1 && movCpu == 4)){ //All wins posible games for paper - Player
            setTextGanar();
        }else if((tempSelect == 2 && movCpu == 1) || (tempSelect == 2 && movCpu == 3)){ //All wins posible games for scissors - Player
            setTextGanar();
        }else if((tempSelect == 3 && movCpu == 1) || (tempSelect == 3 && movCpu == 4)){ //All wins posible games for lizard - Player
            setTextGanar();
        }else if((tempSelect == 4 && movCpu == 0) || (tempSelect == 4 && movCpu == 2)){ //All wins posible games for spock - Player
            setTextGanar();
        }else{
            tvResultado.setText(getString(R.string.text_perder));
            fails++;
            tvCpu.setText(""+fails);
        }
    }

    //To get a random number between 0 and 4
    public int randomNumber(){
        return (int) Math.floor(Math.random() * 5);
    }

    public void setTextGanar(){
        tvResultado.setText(getString(R.string.text_ganar));
        wins++;
        tvJugador.setText(""+wins);
    }

    public void salir(View view){
        finish();
    }
}