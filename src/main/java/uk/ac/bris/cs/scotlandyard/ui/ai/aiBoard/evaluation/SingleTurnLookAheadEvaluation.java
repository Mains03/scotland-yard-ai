package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove.BestMoveStrategy;

import java.util.Objects;

/**
 * {@link EvaluationStrategy} which evaluates a board by looking one move
 * ahead and then evaluating.
 */
public class SingleTurnLookAheadEvaluation implements EvaluationStrategy {
    private final EvaluationStrategy strategy = MinimumDistanceEvaluation.getInstance();

    protected Move bestMove;

    public SingleTurnLookAheadEvaluation() {
    }

    @Override
    public int evaluate(AiBoard board) {
        int evaluation = Integer.MIN_VALUE;
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = (AiBoard) board.advance(move);
            int moveEvaluation = strategy.evaluate(newBoard);
            if (moveEvaluation > evaluation) {
                bestMove = move;
                evaluation = moveEvaluation;
            }
        }
        return evaluation;
    }

    public Move getBestMove() { return bestMove; }
}
