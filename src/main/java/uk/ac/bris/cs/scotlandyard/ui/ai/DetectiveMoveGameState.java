package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Random;

public class DetectiveMoveGameState extends MoveGameState {
    private final Move bestMove;

    public DetectiveMoveGameState(final Board board) {
        super(board);
        bestMove = createBestMove(board);
    }

    private Move createBestMove(final Board board) {
        // returns a random move, replace with your own implementation
        var moves = board.getAvailableMoves().asList();
        return moves.get(new Random().nextInt(moves.size()));
    }

    @Override
    public Move pickMove() { return bestMove; }
}
