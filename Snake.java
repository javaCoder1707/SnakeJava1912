package com.javarush.games.snake;

import com.javarush.engine.cell.*;


import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction = Direction.LEFT;

    public boolean isAlive = true;
    public int count = 0;

   public Snake(int x, int y){
       snakeParts.add(new GameObject(x,y));
       snakeParts.add(new GameObject(x+1,y));
       snakeParts.add(new GameObject(x+2,y));
   }

    public List<GameObject> getSnakeParts() {
        return snakeParts;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if( this.direction.equals(Direction.LEFT) && snakeParts.get(0).x == snakeParts.get(1).x) return;
        if(this.direction.equals(Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) return;
        if(this.direction.equals(Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) return;
        if( this.direction.equals(Direction.UP) && snakeParts.get(0).y == snakeParts.get(1).y) return;

        
        if(direction.equals(Direction.LEFT)) {
          if (!getDirection().equals(Direction.RIGHT)) {
              this.direction = direction;
          } }
      if(direction.equals(Direction.RIGHT)) {


          if (!getDirection().equals(Direction.LEFT)) {
              this.direction = direction;
          }
      }
      if(direction.equals(Direction.DOWN)) {


          if (!getDirection().equals(Direction.UP)) {
              this.direction = direction;
          }
      }
      if(direction.equals(Direction.UP)) {


          if (!getDirection().equals(Direction.DOWN)) {
                this.direction = direction;
          }
      }

    }

    public int getLength(){
        return getSnakeParts().size();
    }

    public  void draw(Game game){
      for(int i = 0; i < snakeParts.size(); i++){
          if(i == 0){
              if(isAlive)
              game.setCellValueEx(snakeParts.get(i).x,snakeParts.get(i).y,Color.NONE, HEAD_SIGN, Color.ROYALBLUE,75);
              else {
                  game.setCellValueEx(snakeParts.get(i).x,snakeParts.get(i).y,Color.NONE, HEAD_SIGN, Color.RED,75);
              }
          }
          if(i > 0){
              if(isAlive)
              game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,Color.NONE, BODY_SIGN,Color.AQUA,75);
              else {
                  game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
              }
          }
       }
   }

    public  void move(Apple apple){
     GameObject go = createNewHead();
     if (go.x >= SnakeGame.WIDTH || go.x < 0 || go.y >= SnakeGame.HEIGHT || go.y < 0) {
            isAlive = false;
            return;
     }
     if(checkCollision(go)) {
         isAlive = false;
         return;
     } else {
         snakeParts.add(0, go);
     }
     if(go.x == apple.x && go.y == apple.y){
         apple.isAlive = false;
         snakeParts.add(new GameObject(snakeParts.get(snakeParts.size()-1).x+1,snakeParts.get(snakeParts.size()-1).y));
         return;
     }

        removeTail();
   }

    public GameObject createNewHead(){
       if(direction.equals(Direction.LEFT)){
           return new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
       }
       if(direction.equals(Direction.RIGHT)){
            return new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        }

       if(direction.equals(Direction.DOWN)){
            return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
        }

       if(direction.equals(Direction.UP)){
            return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
        }
       return null;
    }
   public boolean checkCollision(GameObject gameObject){
       for (GameObject go : snakeParts) {
           if(go.x == gameObject.x && go.y == gameObject.y){
               return true;
           }
       }
       return false;
   }

    public void removeTail(){
       snakeParts.remove(snakeParts.get(snakeParts.size()-1));
    }
}
