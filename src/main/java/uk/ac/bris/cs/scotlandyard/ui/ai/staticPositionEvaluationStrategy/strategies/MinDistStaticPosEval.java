package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinDistStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Evaluation is minimum distance between MrX and the detectives.
 */
public class MinDistStaticPosEval implements StaticPositionEvaluationStrategy {
    private static final int POSITIVE_INFINITY = 1000000;

    private final MinDistStrategy<Pair<Integer, Integer>> strategy;

    public MinDistStaticPosEval(MinDistStrategy<Pair<Integer, Integer>> strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public int evaluate(AiGameState gameState) {
        int mrXLocation = getMrXLocation(gameState);
        int minDist = POSITIVE_INFINITY;
        for (int location : getDetectiveLocations(gameState)) {
            Pair<Integer, Integer> locations = new Pair<>(mrXLocation, location);
            int minDistComparison = strategy.getMinimumDistance(locations);
            minDist = Math.min(minDist, minDistComparison);
        }
        return minDist;
    }

    private int getMrXLocation(AiGameState gameState) {
        Player mrX = gameState.getMrX();
        return mrX.location();
    }

    private Iterable<Integer> getDetectiveLocations(AiGameState gameState) {
        List<Player> detectives = gameState.getDetectives();
        List<Integer> locations = new ArrayList<>();
        for (Player detective : detectives)
            locations.add(detective.location());
        return locations;
    }
}
