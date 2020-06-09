package com.javarush.games.snake;


import com.javarush.engine.cell.*;


public class SnakeGame extends Game {
    private Snake snake;
    private Apple apple;
    private int  turnDelay;
    private int score;
    private boolean isGameStopped;
    private static final int GOAL = 30;

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.initialize();
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
       snake.move(apple);
       if(!apple.isAlive){
           createNewApple();
           score += 5;
           setScore(score);
           turnDelay -= 10;
           setTurnTimer(turnDelay);
       }
       if(!snake.isAlive){
           gameOver();
       }
       if(snake.getLength() > GOAL){
           win();
       }
       drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if(key.equals(Key.LEFT)){
            snake.setDirection(Direction.LEFT);

        }
        if(key.equals(Key.RIGHT)){
            snake.setDirection(Direction.RIGHT);

        }
        if(key.equals(Key.UP)){
            snake.setDirection(Direction.UP);

        }
        if(key.equals(Key.DOWN)){
            snake.setDirection(Direction.DOWN);
        }
        if(key.equals(Key.SPACE)){
            if(isGameStopped){
                createGame();
            }
        }
    }

    private void createNewApple(){
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));

    }

    private void gameOver(){
       stopTurnTimer();
       isGameStopped = true;
       showMessageDialog(Color.RED, "Game Over", Color.CORAL, 80);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.LIME, "YOU WIN", Color.AQUA, 80);
    }

}