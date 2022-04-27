package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.HashSet;
import java.util.Set;

public class MrXMoveFactory {
    private static MrXMoveFactory instance;

    public static MrXMoveFactory getInstance() {
        if (instance == null)
            instance = new MrXMoveFactory();
        return instance;
    }

    private MrXMoveFactory() {}

    public ImmutableSet<Move> getAvailableMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Player mrX) {
        Set<Move> moves = new HashSet<>();
        Set<Move> firstMoves = SingleMoveFactory.getInstance().getAvailableMoves(graph, mrX);
        for (Move firstMove : firstMoves) {
            if (mrX.has(ScotlandYard.Ticket.DOUBLE)) {
                Player player = PlayerMoveAdvance.getInstance().applyMove(mrX, firstMove);
                Set<Move> secondMoves = SingleMoveFactory.getInstance().getAvailableMoves(graph, player);
                for (Move secondMove : secondMoves) {
                    Move.SingleMove firstSingleMove = (Move.SingleMove) firstMove;
                    Move.SingleMove secondSingleMove = (Move.SingleMove) secondMove;
                    moves.add(new Move.DoubleMove(
                            mrX.piece(),
                            firstSingleMove.source(),
                            firstSingleMove.ticket,
                            secondSingleMove.source(),
                            secondSingleMove.ticket,
                            secondSingleMove.destination
                    ));
                }
            } else
                moves = firstMoves;
        }
        return ImmutableSet.copyOf(moves);
    }
}
