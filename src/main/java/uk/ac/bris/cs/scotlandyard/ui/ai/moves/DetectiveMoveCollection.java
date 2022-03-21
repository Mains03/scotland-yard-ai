package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Objects;

class DetectiveMoveCollection extends MoveCollection {
    public DetectiveMoveCollection(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(piece);
        if (piece.isMrX()) throw new IllegalArgumentException();
    }
}
