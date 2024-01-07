package tp.GameLogic;

import tp.Game.Move;
import tp.Message.Message;

/**
 *  This class is responsible for analyzing moves and checking if they are valid.
 *  It will return board state after move.
 */
public class MoveAnalyzer {
    public MoveAnalyzer() {
    }

    public Message analyzeMove(Move move) {
        /**
         * TODO: implement analyzeMove and then return actual board state
         * for now we add +10 to move
         */

        String this_move = move.getMove();

        String[] split_move = this_move.split(";");

        String message ="Move;"+ String.valueOf(Integer.parseInt(split_move[0])+10)+";"+String.valueOf(Integer.parseInt(split_move[1])+10)+";";
        System.out.println(message);
        return new Message(message);
    }
}
