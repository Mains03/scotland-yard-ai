package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

/**
 * Singleton class to determine the available (not necessarily possible) moves
 * for each player at each location.
 */
public class AllMoves {
    // AllMoves singleton instance
    private static AllMoves allMoves;

    /**
     * Creates the AllMoves singleton instance
     * @param graph board game
     */
    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        if (allMoves == null)
            allMoves = new AllMoves(graph);
    }

    /**
     *
     * @return AllMoves singleton instance
     */
    public static AllMoves getInstance() {
        Objects.requireNonNull(allMoves);
        return allMoves;
    }

    private final PlayerMovesFactory playerMovesFactory;

    // moves available to a player
    private Map<Piece, PlayerMoves> moves;

    private AllMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        this.playerMovesFactory = new PlayerMovesFactory(graph);
        moves = new HashMap<>();
    }

    /**
     *
     * @param piece piece to get the moves for
     * @param location location of the piece
     * @return available moves to the piece at the location
     */
    public Collection<Move> getAvailableMoves(final Piece piece, int location) {
        if (!moves.containsKey(piece))
            moves.put(piece, createPlayerMoves(piece));
        return moves.get(piece).getAvailableMoves(location);
    }

    private PlayerMoves createPlayerMoves(final Piece piece) {
        return playerMovesFactory.createPlayerMoves(piece);
    }
}
