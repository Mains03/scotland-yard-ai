package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class PlayerFactory {
    private static PlayerFactory playerFactory;

    static PlayerFactory getInstance() {
        if (playerFactory == null)
            playerFactory = new PlayerFactory();
        return playerFactory;
    }

    private PlayerFactory() {}

    Player createMrX(final Board board, final Move move) {
        return new Player(
                Piece.MrX.MRX,
                createPlayerTickets(board, Piece.MrX.MRX),
                move.source()
        ).use(move.tickets()).at(getMoveDestination(move));
    }

    private int getMoveDestination(final Move move) {
        return move.accept(new Move.Visitor<>() {
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

    List<Player> createDetectives(final Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> createDetective(board, (Piece.Detective) piece))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Optional<Player> createDetective(final Board board, final Piece.Detective piece) {
        Optional<Integer> location = board.getDetectiveLocation(piece);
        if (location.isEmpty()) return Optional.empty();
        return Optional.of(new Player(
                piece,
                createPlayerTickets(board, piece),
                location.get()
        ));
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(
            final Board board,
            final Piece piece
    ) {
        Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isPresent()) {
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values()) {
                int count = ticketBoard.get().getCount(ticket);

                tickets.put(ticket, count);
            }
        }
        return ImmutableMap.copyOf(tickets);
    }
}
