package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardAdapter;

import java.util.NoSuchElementException;

/**
 * Various evaluation strategies only need to look one move ahead.
 */
public abstract class SingleTurnLookAheadStrategy implements BestMoveStrategy {
    private static final int NEGATIVE_INFINITY = -10000000;

    private final AiBoard board;

    public SingleTurnLookAheadStrategy(Board board) {
        this.board = new AiBoardAdapter(board);
    }

    @Override
    public Move determineBestMove() {
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        for (Move move : board.getAvailableMoves()) {
            int evaluation = evaluateMove(move);
            if (evaluation > bestMoveEvaluation) {
                bestMove = move;
                bestMoveEvaluation = evaluation;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No MrX moves");
        return bestMove;
    }

    private int evaluateMove(Move move) {
        AiBoard newBoard = board.applyMove(move);
        return staticEvaluation(newBoard);
    }

    public abstract int staticEvaluation(AiBoard board);
}
