package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.DijkstraWithTickets;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.MinimumDistance;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of best move strategy which simply considers the minimum distance
 * between the detectives and MrX for each move MrX can make. The best move is the one
 * which maximises this distance.
 */
public class MinimumDistanceStrategy implements BestMoveStrategy {
    protected static final int POSITIVE_INFINITY = 100000000;

    private final AiBoard aiBoard;

    public MinimumDistanceStrategy(Board board) {
        Objects.requireNonNull(board);
        aiBoard = new AiBoardAdapter(board);
    }

    @Override
    public Move determineBestMove() {
        MinimumDistance minimumDistanceStrategy = new DijkstraWithTickets(aiBoard.getGraph());
        AiMove bestMove = null;
        int bestMoveDist = -1;
        for (AiMove move : aiBoard.getAvailableMoves()) {
            AiPlayer mrX = aiBoard.getMrX().applyMove(move);
            int dist = minimumDistanceBetweenMrXAndDetectives(mrX, aiBoard.getDetectives(), minimumDistanceStrategy);
            if (dist > bestMoveDist) {
                bestMove = move;
                bestMoveDist = dist;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No moves available");
        return bestMove.asMove();
    }

    protected int minimumDistanceBetweenMrXAndDetectives(
            AiPlayer mrX,
            List<AiPlayer> detectives,
            MinimumDistance strategy
    ) {
        // apply the strategy to each detective and MrX
        // return the minimum distance found
        Optional<Integer> minDist = detectives.stream()
                .map(detective -> strategy.minimumDistance(detective, mrX))
                .min(Integer::compareTo);
        return minDist.orElse(POSITIVE_INFINITY);
    }
}
