package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class MinimumDistanceStrategy implements BestMoveStrategy {
    private static final int POSITIVE_INFINITY = 100000000;

    private final Move bestMove;

    public MinimumDistanceStrategy(Board board) {
        Objects.requireNonNull(board);
        AiBoard aiBoard = new AiBoardAdapter(board);
        MinimumDistance minimumDistanceStrategy = new BreadthFirstSearch(aiBoard.getGraph());
        Move currentBestMove = null;
        int bestMoveDist = -1;
        for (Move move : aiBoard.getAvailableMoves()) {
            AiPlayer mrX = aiBoard.getMrX().applyMove(move);
            int dist = minimumDistanceBetweenMrXAndDetectives(mrX, aiBoard.getDetectives(), minimumDistanceStrategy);
            System.out.println(move.accept(new Move.Visitor<Integer>() {
                @Override
                public Integer visit(Move.SingleMove move) {
                    return move.destination;
                }

                @Override
                public Integer visit(Move.DoubleMove move) {
                    return move.destination2;
                }
            }) + " " + dist);
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
        int mrXLocation = mrX.getLocation();
        Optional<Integer> minDist = detectives.stream()
                .map(detective -> strategy.minimumDistance(mrXLocation, detective.getLocation()))
                .min(Integer::compareTo);
        return minDist.orElse(POSITIVE_INFINITY);
    }

    @Override
    public Move determineBestMove() {
        return bestMove;
    }
}
