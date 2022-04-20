package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2Adapter;

import java.util.NoSuchElementException;

/**
 * Various evaluation strategies only need to look one move ahead.
 */
public abstract class SingleTurnLookAheadStrategy implements BestMoveStrategy {
    private static final int NEGATIVE_INFINITY = -10000000;

    private final AiBoardV2 board;

    public SingleTurnLookAheadStrategy(Board board) {
        this.board = new AiBoardV2Adapter(board);
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
        AiBoardV2 newBoard = board.applyMove(move);
        return staticEvaluation(newBoard);
    }

    public abstract int staticEvaluation(AiBoardV2 board);
}
