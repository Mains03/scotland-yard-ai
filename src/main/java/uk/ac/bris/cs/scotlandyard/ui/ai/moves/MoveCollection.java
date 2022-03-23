package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Objects;

/**
 * Abstract collection of moves.
 */
public abstract class MoveCollection {
    // Generate moves common to both MrX and the detectives
    protected ImmutableSet<Move> generateMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(piece);
        return null;
    }
}
