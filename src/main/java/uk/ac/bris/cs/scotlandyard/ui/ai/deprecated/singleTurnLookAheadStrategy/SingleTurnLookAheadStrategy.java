package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

/**
 * Looks ahead one move and finds the best move.
 *
 * @deprecated Deprecated since uses
 * {@link uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.SingleTurnLookAheadStrategy}
 * which is deprecated.
 */
@Deprecated
public class SingleTurnLookAheadStrategy extends uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.SingleTurnLookAheadStrategy {
    public SingleTurnLookAheadStrategy(
            Board board,
            StaticPositionEvaluationStrategy evaluationStrategy
    ) {
        super(board, evaluationStrategy);
    }
}