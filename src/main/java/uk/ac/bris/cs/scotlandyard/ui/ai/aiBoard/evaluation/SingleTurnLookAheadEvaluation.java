package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Objects;

/**
 * {@link EvaluationStrategy} which evaluates a board by looking one move
 * ahead and then evaluating.
 */
public class SingleTurnLookAheadEvaluation implements EvaluationStrategy {
    private static final int POSITIVE_INFINITY =  10000000;
    private static final int NEGATIVE_INFINITY = -10000000;

    private final EvaluationStrategy strategy;

    private final boolean maximise;

    private Move bestMove;

    public SingleTurnLookAheadEvaluation(EvaluationStrategy strategy, boolean maximise) {
        this.strategy = Objects.requireNonNull(strategy);
        this.maximise = maximise;
    }

    @Override
    public Integer visit(StandardAiBoard board) {
        int evaluation = initialEvaluation();
        for (Move move : board.getAvailableMoves()) {
            StandardAiBoard board2 = (StandardAiBoard) board.advance(move);
            int moveEvaluation = board2.accept(strategy);
            evaluation = updateEvaluation(evaluation, move, moveEvaluation);
        }
        return evaluation;
    }

    @Override
    public Integer visit(LocationAiBoard board) {
        int evaluation = initialEvaluation();
        for (Move move : board.getAvailableMoves()) {
            LocationAiBoard board2 = (LocationAiBoard) board.advance(move);
            int moveEvaluation = board2.accept(strategy);
            evaluation = updateEvaluation(evaluation, move, moveEvaluation);
        }
        return evaluation;
    }

    private int initialEvaluation() {
        if (maximise)
            return NEGATIVE_INFINITY;
        else
            return POSITIVE_INFINITY;
    }

    private int updateEvaluation(int evaluation, Move move, int moveEvaluation) {
        if (maximise) {
            if (moveEvaluation > evaluation) {
                bestMove = move;
                evaluation = moveEvaluation;
            }
        } else {
            if (moveEvaluation < evaluation) {
                bestMove = move;
                evaluation = moveEvaluation;
            }
        }
        return evaluation;
    }

    public Move getBestMove() { return bestMove; }
}
