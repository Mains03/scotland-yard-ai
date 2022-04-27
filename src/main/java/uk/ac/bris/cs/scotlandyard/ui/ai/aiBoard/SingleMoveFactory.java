package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SingleMoveFactory {
    private static SingleMoveFactory instance;

    public static SingleMoveFactory getInstance() {
        if (instance == null)
            instance = new SingleMoveFactory();
        return instance;
    }

    private SingleMoveFactory() {}

    public ImmutableSet<Move> getAvailableMoves(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Player player) {
        Set<Move> moves = new HashSet<>();
        for (int destination : graph.adjacentNodes(player.location())) {
            // generate moves from set of transport
            Consumer<ImmutableSet<ScotlandYard.Transport>> consumer = allTransport -> {
                for (ScotlandYard.Transport transport : allTransport) {
                    if (player.has(transport.requiredTicket()))
                        moves.add(new Move.SingleMove(player.piece(), player.location(), transport.requiredTicket(), destination));
                }

                if (player.has(ScotlandYard.Ticket.SECRET))
                    moves.add(new Move.SingleMove(player.piece(), player.location(), ScotlandYard.Ticket.SECRET, destination));
            };

            graph.edgeValue(player.location(), destination).ifPresent(consumer);
        }
        return ImmutableSet.copyOf(moves);
    }
}
