package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.ArrayList;
import java.util.List;

class PlayerMoves {
    private final ImmutableList<MoveCollection> moves;

    public PlayerMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece
    ) {
        moves = createMoves(graph, piece);
    }

    private ImmutableList<MoveCollection> createMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece
    ) {
        List<MoveCollection> moves = new ArrayList<>();
        for (int location : graph.nodes())
            moves.add(createMoveCollection(graph, piece, location));
        return ImmutableList.copyOf(moves);
    }

    // Factory method to create move collections
    private MoveCollection createMoveCollection(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Piece piece,
            final int location
    ) {
        if (piece.isMrX())
            return new MrXMoveCollection(graph, piece, location);
        else
            return new DetectiveMoveCollection(graph, piece, location);
    }

    public MoveCollection getMoves(int location) {
        return moves.get(location-1);
    }
}
