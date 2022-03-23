package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Objects;

/**
 * Moves available to MrX at the specified location.
 */
class MrXMoveCollection extends MoveCollection {
    // Moves which can also be done by a detective
    private final ImmutableSet<Move> nonSpecialMoves;
    // Moves which are specific to MrX only
    private final ImmutableSet<Move> specialMoves;

    /**
     *
     * @param graph game board
     * @param location location to generate moves at
     */
    MrXMoveCollection(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final int location
    ) {
        Objects.requireNonNull(graph);
        nonSpecialMoves = generateNonSpecialMoves(graph, location);
        specialMoves = generateSpecialMoves(graph, location);
    }

    private ImmutableSet<Move> generateNonSpecialMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final int location
    ) {
        return generateMoves(graph, Piece.MrX.MRX, location);
    }

    private ImmutableSet<Move> generateSpecialMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final int location
    ) {
        return null;
    }
}
