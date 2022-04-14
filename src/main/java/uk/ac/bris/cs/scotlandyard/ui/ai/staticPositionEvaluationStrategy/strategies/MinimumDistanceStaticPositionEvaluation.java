package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies;

import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceAlgorithm.MinimumDistanceAlgorithmStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.List;
import java.util.Objects;

/**
 * The evaluation of the game state is the minimum distance between the detectives
 * and MrX.
 */
public class MinimumDistanceStaticPositionEvaluation implements StaticPositionEvaluationStrategy {
    private static final int POSITIVE_INFINITY = 10000000;

    private final MinimumDistanceAlgorithmStrategy algorithm;

    public MinimumDistanceStaticPositionEvaluation(MinimumDistanceAlgorithmStrategy algorithm) {
        Objects.requireNonNull(algorithm);
        this.algorithm = algorithm;
    }

    @Override
    public int evaluate(AiGameState gameState) {
        Player mrX = gameState.getMrX();
        List<Player> detectives = gameState.getDetectives();
        int minDist = POSITIVE_INFINITY;
        for (Player detective : detectives) {
            int dist = algorithm.minimumDistance(detective, mrX);
            minDist = Math.min(minDist, dist);
        }
        return minDist;
    }
}
