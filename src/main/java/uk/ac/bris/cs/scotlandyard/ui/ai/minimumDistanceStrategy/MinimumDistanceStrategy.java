package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;

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
    private static final int POSITIVE_INFINITY = 100000000;

    private final Move bestMove;

    public MinimumDistanceStrategy(Board board) {
        Objects.requireNonNull(board);
        AiBoard aiBoard = new AiBoardAdapter(board);
        MinimumDistance minimumDistanceStrategy = new MinimumDistanceWithTicket(aiBoard.getGraph());
        Move currentBestMove = null;
        int bestMoveDist = -1;
        for (Move move : aiBoard.getAvailableMoves()) {
            AiPlayer mrX = aiBoard.getMrX().applyMove(move);
            int dist = minimumDistanceBetweenMrXAndDetectives(mrX, aiBoard.getDetectives(), minimumDistanceStrategy);
            if (dist > bestMoveDist) {
                currentBestMove = move;
                bestMoveDist = dist;
            }
        }
        if (currentBestMove == null)
            throw new NoSuchElementException("No moves available");
        bestMove = currentBestMove;
    }

    private int minimumDistanceBetweenMrXAndDetectives(
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

    @Override
    public Move determineBestMove() {
        return bestMove;
    }
}
