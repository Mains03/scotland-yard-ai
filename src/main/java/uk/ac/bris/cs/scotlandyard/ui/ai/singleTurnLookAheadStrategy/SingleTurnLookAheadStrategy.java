package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoardAdapter;

import java.util.NoSuchElementException;

/**
 * Various evaluation strategies only need to look one move ahead.
 */
public abstract class SingleTurnLookAheadStrategy implements BestMoveStrategy {
    private static final int NEGATIVE_INFINITY = -10000000;

    @Override
    public Move determineBestMove(Board board) {
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        for (Move move : board.getAvailableMoves()) {
            int evaluation = evaluateMove(board, move);
            if (evaluation > bestMoveEvaluation) {
                bestMove = move;
                bestMoveEvaluation = evaluation;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No MrX moves");
        return bestMove;
    }

    private int evaluateMove(Board board, Move move) {
        AiBoard aiBoard = new AiBoardAdapter(board);
        AiBoard newAiBoard = aiBoard.applyMove(move);
        return staticEvaluation(newAiBoard);
    }

    public abstract int staticEvaluation(AiBoard board);
}
