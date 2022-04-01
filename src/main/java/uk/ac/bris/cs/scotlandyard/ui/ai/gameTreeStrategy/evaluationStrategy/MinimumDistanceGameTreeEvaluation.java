package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinimumDistanceAlgorithmStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.MinimumDistanceStaticPositionEvaluation;

public class MinimumDistanceGameTreeEvaluation extends MinimaxAlgorithm {
    public MinimumDistanceGameTreeEvaluation(MinimumDistanceAlgorithmStrategy minimumDistanceAlgorithm) {
        super(new MinimumDistanceStaticPositionEvaluation(
                minimumDistanceAlgorithm
        ));
    }
}
