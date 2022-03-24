package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

/**
 * Moves available to a player at each location.
 */
abstract class PlayerMoves {
    private final Piece piece;
    private Map<Integer, Collection<Move>> moves = new HashMap<>();

    PlayerMoves(final Piece piece) {
        this.piece = piece;
    }

    Collection<Move> getAvailableMoves(int location) {
        if (!moves.containsKey(location))
            moves.put(location, createMoves(piece, location));
        return moves.get(location);
    }

    abstract Collection<Move> createMoves(final Piece piece, int location);
}
