package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

/**
 * Looks ahead one move and finds the best move.
 */
public class SingleTurnLookAheadStrategy extends uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.SingleTurnLookAheadStrategy {
    public SingleTurnLookAheadStrategy(
            Board board,
            StaticPositionEvaluationStrategy evaluationStrategy
    ) {
        super(board, evaluationStrategy);
    }
}