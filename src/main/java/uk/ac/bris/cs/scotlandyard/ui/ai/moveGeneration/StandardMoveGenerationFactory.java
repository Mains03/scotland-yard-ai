package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMoveAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.MoveApplyFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Singleton class to generate moves for a player.
 * If player has double move tickets, generates double moves as well.
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
            // If MrX, must generate single and double moves
            Set<Move> moves = generateSingleMoves(board, player);
            if (player.has(ScotlandYard.Ticket.DOUBLE)) {
                // If MrX has a double ticket, check all subsequent moves from each possible single move
                MoveApplyFactory moveApplyFactory = MoveApplyFactory.getInstance();
                for (Move firstMove : moves) {
                    // Apply first move and generate double moves
                    Player newPlayer = moveApplyFactory.applyMove(player, firstMove);
                    Set<Move> secondMoves = generateSingleMoves(board, newPlayer);
                    for (Move secondMove : secondMoves) {
                        // Checks if destination of second move is source of first
                        AiMove aiSecondMove = new AiMoveAdapter(secondMove);
                        if (aiSecondMove.getDestination() != firstMove.source()) {
                            // Cast first and second moves into SingleMoves then make and add DoubleMove
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
            }
            return moves;
        } else
            // If not MrX, just generate single moves
            return generateSingleMoves(board, player);
    }

    private Set<Move> generateSingleMoves(MoveGenerationBoard board, Player player) {
        Set<Move> moves = new HashSet<>();
        ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = board.getGraph();
        int location = player.location();
        // For each node connected to player location, if transport exists check if player has the ticket
        for (int destination : graph.adjacentNodes(location)) {
            Optional<ImmutableSet<ScotlandYard.Transport>> mTransports = graph.edgeValue(location, destination);
            if (mTransports.isPresent()) {
                ImmutableSet<ScotlandYard.Transport> transports = mTransports.get();
                for (ScotlandYard.Transport transport : transports) {
                    ScotlandYard.Ticket ticket = transport.requiredTicket();
                    // If transport to this location exists and the player has a ticket
                    if (player.has(transport.requiredTicket())) {
                        Move move = new Move.SingleMove(player.piece(), location, ticket, destination);
                        moves.add(move);
                    }
                }
                // If the player has a secret ticket i.e. they're MrX, add that ticket as well
                if (player.has(ScotlandYard.Ticket.SECRET)) {
                    Move move = new Move.SingleMove(player.piece(), location, ScotlandYard.Ticket.SECRET, destination);
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}
