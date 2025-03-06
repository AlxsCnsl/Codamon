package com.example.codamon.core.batlle.turn_manager;

import com.example.codamon.core.action.move.Move;

import java.util.ArrayList;

public class MoveQueue {
    private ArrayList<Move> moveQueue = new ArrayList<>();

    public MoveQueue(){}

    public ArrayList<Move> getMoveQueue(){
        return moveQueue;
    }

    public void addMoveInQueue(Move move){
        moveQueue.add(move);
    }

    public void deleteMoveInQueue(Move moveRemoved){
        if(moveQueue != null && !moveQueue.isEmpty()){
            for (Move move : moveQueue){

                if(move.equals(moveRemoved)){
                    moveQueue.remove(moveRemoved);
                    System.out.println("#MOVE_QUEUE# Move : \""+
                            moveRemoved.getName()+
                            "\" is Removed on MoveQueue");
                    return;
                }
            }
        }
    }

    public void nextMoveExecute(){
        moveQueue.getFirst().execute();
        this.deleteMoveInQueue(moveQueue.getFirst());
    }


}
