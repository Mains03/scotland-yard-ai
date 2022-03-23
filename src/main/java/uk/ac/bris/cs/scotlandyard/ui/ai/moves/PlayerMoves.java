package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.ArrayList;
import java.util.List;

/**
 * All moves available to a player at each location.
 */
class PlayerMoves {
    /**
     * Moves available to the player. Moves available at index i correspond
     * to node i+1 on the board.
     */
    private final ImmutableList<MoveCollection> moves;

    /**
     *
     * @param graph game board
     * @param piece piece to generate moves for
     */
    public PlayerMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece
    ) {
        moves = createMoves(graph, piece);
    }

    /**
     *
     * @param graph game board
     * @param piece piece to generate moves for
     * @return available moves
     */
    private ImmutableList<MoveCollection> createMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece
    ) {
        List<MoveCollection> moves = new ArrayList<>();
        for (int location : graph.nodes())
            moves.add(createMoveCollection(graph, piece, location));
        return ImmutableList.copyOf(moves);
    }

    /**
     * Factory method to create a MoveCollection instance
     * @param graph game board
     * @param piece piece to generate moves for
     * @param location location to generate moves at
     * @return available moves for the piece at the specified location
     */
    private MoveCollection createMoveCollection(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        if (piece.isMrX())
            return new MrXMoveCollection(graph, location);
        else
            return new DetectiveMoveCollection(graph, piece, location);
    }

    /**
     *
     * @param location node to get the available moves for
     * @return available moves
     */
    public MoveCollection getMoves(int location) {
        // the index is location - 1
        return moves.get(location-1);
    }
}
