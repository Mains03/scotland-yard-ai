package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MoveApplyFactory {
    private static MoveApplyFactory moveApplyFactory;

    public static MoveApplyFactory getInstance() {
        if (moveApplyFactory == null)
            moveApplyFactory = new MoveApplyFactory();
        return moveApplyFactory;
    }

    private MoveApplyFactory() {}

    public Player applyMove(Player player, Move move) {
        ImmutableMap<ScotlandYard.Ticket, Integer> newTickets = useTickets(player, move);
        int newLocation = determineNewLocation(player, move);
        return new Player(
                player.piece(),
                newTickets,
                newLocation
        );
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> useTickets(Player player, Move move) {
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>(player.tickets());

        return move.accept(new Move.Visitor<ImmutableMap<ScotlandYard.Ticket, Integer>>() {
            @Override
            public ImmutableMap<ScotlandYard.Ticket, Integer> visit(Move.SingleMove move) {
                ScotlandYard.Ticket ticket = move.ticket;
                useTicket(tickets, ticket);
                return ImmutableMap.copyOf(tickets);
            }

            @Override
            public ImmutableMap<ScotlandYard.Ticket, Integer> visit(Move.DoubleMove move) {
                ScotlandYard.Ticket ticket1 = move.ticket1;
                ScotlandYard.Ticket ticket2 = move.ticket2;
                useTicket(tickets, ticket1);
                useTicket(tickets, ticket2);
                useTicket(tickets, ScotlandYard.Ticket.DOUBLE);
                return ImmutableMap.copyOf(tickets);
            }

            private void useTicket(Map<ScotlandYard.Ticket, Integer> tickets, ScotlandYard.Ticket ticket) {
                tickets.put(ticket, tickets.get(ticket)-1);
                if (tickets.get(ticket) < 0)
                    throw new NoSuchElementException();
            }
        });
    }

    private int determineNewLocation(Player player, Move move) {
        return move.accept(new Move.Visitor<Integer>() {
            @Override
            public Integer visit(Move.SingleMove move) {
                return move.destination;
            }

            @Override
            public Integer visit(Move.DoubleMove move) {
                return move.destination2;
            }
        });
    }
}
