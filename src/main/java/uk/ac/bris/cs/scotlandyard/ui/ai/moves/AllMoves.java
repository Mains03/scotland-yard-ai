package uk.ac.bris.cs.scotlandyard.ui.ai.moves;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

/**
 * Singleton class which precomputes all possible moves at every location
 * for each player.
 */
public class AllMoves {
    private static AllMoves allMoves;

    /**
     * Creates an instance of the AllMoves singleton class.
     * @param graph the board
     */
    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        if (allMoves == null)
            allMoves = new AllMoves(graph);
    }

    /**
     * @return the AllMoves singleton instance.
     */
    public static AllMoves getInstance() {
        Objects.requireNonNull(allMoves);
        return allMoves;
    }

    /**
     * The available moves to each player at each location.
     */
    private final ImmutableMap<Piece, PlayerMoves> moves;

    private AllMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        moves = createMoves(graph);
    }

    /**
     * Creates all the moves available to each player at each location.
     * @param graph game board
     * @return
     */
    private ImmutableMap<Piece, PlayerMoves> createMoves(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Map<Piece, PlayerMoves> moves = new HashMap<>();
        for (Piece piece : getPieces())
            moves.put(piece, new PlayerMoves(graph, piece));
        return ImmutableMap.copyOf(moves);
    }

    /**
     * @return iterable collection of all the pieces.
     */
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

    /**
     *
     * @param piece piece to get the moves for
     * @param location location of the piece
     * @return available moves to the piece at the location
     */
    public MoveCollection getAvailableMoves(final Piece piece, int location) {
        return moves.get(piece).getMoves(location);
    }
}
