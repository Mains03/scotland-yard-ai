package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Singleton helper class to generate {@link Set<Move>} for a detective {@link Player}.
 */
public class DetectiveMoveFactory {
    private static DetectiveMoveFactory instance;

    public static DetectiveMoveFactory getInstance() {
        if (instance == null)
            instance = new DetectiveMoveFactory();
        return instance;
    }

    private DetectiveMoveFactory() {}

    public Set<Move> generateMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            Player player
    ) throws IllegalArgumentException {
        if (player.isMrX())
            throw new IllegalArgumentException("Expected detective");
        Set<Move> moves = new HashSet<>();
        for (int destination : graph.adjacentNodes(player.location())) {
            Optional<ImmutableSet<ScotlandYard.Transport>> mTransport = graph.edgeValue(player.location(), destination);
            if (mTransport.isPresent()) {
                for (ScotlandYard.Transport transport : mTransport.get()) {
                    if (player.has(transport.requiredTicket())) {
                        moves.add(
                                new Move.SingleMove(player.piece(), player.location(), transport.requiredTicket(), destination)
                        );
                    }
                }
            }
        }
        return moves;
    }
}
