package com.example.codamon.core.batlle.turn_manager;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Random;

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

    public void sortQueue(){
        ArrayList<Move> newQueue = new ArrayList<>();
        while (!moveQueue.isEmpty()){
            Move prioMove = moveQueue.getFirst();
            for(Move move : moveQueue){
                prioMove = compareMovePriority(move, prioMove);
            }
            newQueue.add(prioMove);
            moveQueue.remove(prioMove);
        }
        moveQueue = newQueue;
    }

    private Move compareMovePriority(Move move1, Move move2){
        ArrayList<Move> winMove = new ArrayList<>();
        winMove.add(move2);
        ArrayList<Move> loseMove= new ArrayList<>();
        loseMove.add(move1);

        if(loseMove.getFirst().getOwner().getCurrentState("SPE") >
                winMove.getFirst().getOwner().getCurrentState("SPE")) {
            changeWinMove(winMove, loseMove);
        }
        if(loseMove.getFirst().getPriority() >
                winMove.getFirst().getPriority()){
            changeWinMove(winMove, loseMove);
        }
        if((loseMove.getFirst().getOwner().getCurrentState("SPE")==
                winMove.getFirst().getOwner().getCurrentState("SPE")) &&
                (move1.getPriority() == move2.getPriority())){
            winMove.add(getRandomMove(move1, move2));
            winMove.remove(winMove.getFirst());
        }
        return winMove.getFirst();
    }


    private void changeWinMove(ArrayList<Move> winMove, ArrayList<Move> loseMove){
        winMove.add(loseMove.getFirst());
        loseMove.add(winMove.getFirst());
        winMove.remove(winMove.getFirst());
        loseMove.remove(loseMove.getFirst());
    }

    public String toString(){
        String str = "";
        for(Move move : moveQueue){
            str += move.toString()+" of "+move.getOwner().getName()+"//";
        }
        return str;
    }

    private Move getRandomMove(Move move1, Move move2){
        Random rand = new Random();
        int choice = rand.nextInt(2);
        if(choice == 0){
            return move1;
        }
        return move2;

    }

    public void deleteFirstMove(){
        this.deleteMoveInQueue(moveQueue.getFirst());
    }

    public Move getNextMove(){
        return moveQueue.getFirst();
    }

    public void nextMoveExecute(){
        moveQueue.getFirst().execute();
        this.deleteMoveInQueue(moveQueue.getFirst());
    }


}
