package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceAlgorithm.MinimumDistanceAlgorithmStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.MinimumDistanceStaticPositionEvaluation;

/**
 * @deprecated Deprecated singe {@link SingleTurnLookAheadStrategy} is deprecated.
 */
@Deprecated
public class MinimumDistanceSingleTurnLookAhead extends SingleTurnLookAheadStrategy {
    public MinimumDistanceSingleTurnLookAhead(
            Board board,
            MinimumDistanceAlgorithmStrategy algorithm) {
        super(
                board,
                new MinimumDistanceStaticPositionEvaluation(
                        algorithm
                )
        );
    }
}
