package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * {@link EvaluationStrategy} which evaluates a board by looking one move
 * ahead and then evaluating.
 */
public class SingleTurnLookAheadEvaluation extends EvaluationStrategy {
    protected Move bestMove;

    public SingleTurnLookAheadEvaluation() {
    }

    @Override
    public int evaluate(AiBoard board) {
        int evaluation = Integer.MIN_VALUE;
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = (AiBoard) board.advance(move);
            int moveEvaluation = EvaluationStrategy.getInstance().evaluate(newBoard);
            if (moveEvaluation > evaluation) {
                bestMove = move;
                evaluation = moveEvaluation;
            }
        }
        return evaluation;
    }

    public Move getBestMove() { return bestMove; }
}
