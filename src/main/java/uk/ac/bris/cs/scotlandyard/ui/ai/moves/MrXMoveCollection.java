package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Objects;

class MrXMoveCollection extends MoveCollection {
    private final ImmutableSet<Move> nonSpecialMoves;
    private final ImmutableSet<Move> specialMoves;

    MrXMoveCollection(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(piece);
        if (piece.isDetective()) throw new IllegalArgumentException();
        nonSpecialMoves = generateNonSpecialMoves(graph, piece, location);
        specialMoves = generateSpecialMoves(graph, piece, location);
    }

    private ImmutableSet<Move> generateNonSpecialMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        return generateMoves(graph, piece, location);
    }

    private ImmutableSet<Move> generateSpecialMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        return null;
    }
}
