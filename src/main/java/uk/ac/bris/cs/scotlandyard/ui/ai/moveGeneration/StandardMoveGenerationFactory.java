package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiPlayer.MoveApplyFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Singleton class to generate moves for a player.
 */
public class StandardMoveGenerationFactory implements MoveGenerationFactory {
    private static StandardMoveGenerationFactory moveGenerationFactory;

    public static StandardMoveGenerationFactory getInstance() {
        if (moveGenerationFactory == null)
            moveGenerationFactory = new StandardMoveGenerationFactory();
        return moveGenerationFactory;
    }

    private StandardMoveGenerationFactory() {}

    @Override
    public Set<Move> generateMoves(MoveGenerationBoard board, Player player) {
        if (player.isMrX()) {
            Set<Move> moves = generateSingleMoves(board, player);
            if (player.has(ScotlandYard.Ticket.DOUBLE)) {
                MoveApplyFactory moveApplyFactory = MoveApplyFactory.getInstance();
                for (Move firstMove : moves) {
                    Player newPlayer = moveApplyFactory.applyMove(player, firstMove);
                    Set<Move> secondMoves = generateSingleMoves(board, newPlayer);
                    for (Move secondMove : secondMoves) {
                        Move.SingleMove firstMoveSingle = (Move.SingleMove) firstMove;
                        Move.SingleMove secondMoveSingle = (Move.SingleMove) secondMove;
                        Move.DoubleMove doubleMove = new Move.DoubleMove(
                                player.piece(),
                                player.location(),
                                firstMoveSingle.ticket,
                                firstMoveSingle.destination,
                                secondMoveSingle.ticket,
                                secondMoveSingle.destination
                        );
                        moves.add(doubleMove);
                    }
                }
            }
            return moves;
        } else
            return generateSingleMoves(board, player);
    }

    private Set<Move> generateSingleMoves(MoveGenerationBoard board, Player player) {
        Set<Move> moves = new HashSet<>();
        ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = board.getGraph();
        List<Integer> detectiveLocations = board.getDetectiveLocations();
        int location = player.location();
        for (int destination : graph.adjacentNodes(location)) {
            if (!detectiveLocations.contains(destination)) {
                Optional<ImmutableSet<ScotlandYard.Transport>> mTransports = graph.edgeValue(location, destination);
                if (mTransports.isPresent()) {
                    ImmutableSet<ScotlandYard.Transport> transports = mTransports.get();
                    for (ScotlandYard.Transport transport : transports) {
                        ScotlandYard.Ticket ticket = transport.requiredTicket();
                        if (player.has(transport.requiredTicket())) {
                            Move move = new Move.SingleMove(player.piece(), location, ticket, destination);
                            moves.add(move);
                        }
                    }
                    if (player.has(ScotlandYard.Ticket.SECRET)) {
                        Move move = new Move.SingleMove(player.piece(), location, ScotlandYard.Ticket.SECRET, destination);
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }
}
