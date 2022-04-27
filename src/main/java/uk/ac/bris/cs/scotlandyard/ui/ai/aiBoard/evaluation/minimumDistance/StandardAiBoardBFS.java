package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PlayerMoveAdvance;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

/**
 * {@link MinimumDistanceAlgorithm} using breadth-first search on a {@link StandardAiBoard}.
 */
public class StandardAiBoardBFS extends BFS implements MinimumDistanceAlgorithm {
    private static StandardAiBoardBFS instance;

    public static StandardAiBoardBFS getInstance() {
        if (instance == null)
            instance = new StandardAiBoardBFS();
        return instance;
    }

    private StandardAiBoardBFS() {
        super();
    }

    @Override
    public int minimumDistance(AiBoard board, Piece piece) {
        if (piece.isMrX())
            throw new IllegalArgumentException();
        if (board instanceof StandardAiBoard standardAiBoard)
            return minimumDistance(standardAiBoard, piece);
        else
            throw new IllegalArgumentException("Expected StandardAiBoard");
    }

    /**
     * Considers tickets.
     */
    private Integer minimumDistance(StandardAiBoard board, Piece detective) {
        Queue<Pair<Player, Integer>> queue = new ArrayDeque<>();
        Player player = PlayerFactory.getInstance().createPlayer(board, detective);
        queue.add(new Pair<>(player, 0));
        while (queue.size() > 0) {
            Pair<Player, Integer> node = queue.poll();
            if (node.left().location() == board.mrX.location())
                // we know this is the minimum distance since each edge has weight 1
                return node.right();
            Set<Move> availableMoves = DetectiveMoveFactory.getInstance().generateMoves(board.getSetup().graph, node.left());
            for (Move move : availableMoves) {
                Player newPlayer = PlayerMoveAdvance.getInstance().applyMove(node.left(), move);
                int newDist = node.right()+1;
                queue.add(new Pair<>(newPlayer, newDist));
            }
        }
        // no path found
        return POSITIVE_INFINITY;
    }
}
