package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinimumDistanceAlgorithmStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.MinimumDistanceStaticPositionEvaluation;

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
