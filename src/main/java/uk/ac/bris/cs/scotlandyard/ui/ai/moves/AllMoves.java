package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

public class AllMoves {
    private static AllMoves allMoves;

    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        if (allMoves == null)
            allMoves = new AllMoves(graph);
    }

    public static AllMoves getInstance() {
        Objects.requireNonNull(allMoves);
        return allMoves;
    }

    private final ImmutableMap<Piece, PlayerMoves> moves;

    private AllMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        moves = createMoves(graph);
    }

    private ImmutableMap<Piece, PlayerMoves> createMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Map<Piece, PlayerMoves> moves = new HashMap<>();
        for (Piece piece : getPieces())
            moves.put(piece, new PlayerMoves(graph, piece));
        return ImmutableMap.copyOf(moves);
    }

    private Iterable<Piece> getPieces() {
        return Arrays.asList(
                Piece.MrX.MRX,
                Piece.Detective.BLUE,
                Piece.Detective.GREEN,
                Piece.Detective.RED,
                Piece.Detective.WHITE,
                Piece.Detective.YELLOW
        );
    }

    public MoveCollection getAvailableMoves(final Piece piece, int location) {
        return moves.get(piece).getMoves(location);
    }
}
