package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class to create and manipulate players.
 */
class PlayerFactory {
    private static PlayerFactory playerFactory;

    static PlayerFactory getInstance() {
        if (playerFactory == null)
            playerFactory = new PlayerFactory();
        return playerFactory;
    }

    private PlayerFactory() {}

    /**
     * Creates the player instance for MrX.
     * @param board the board
     * @param move the chosen move for MrX
     * @return MrX player instance
     */
    Player createMrX(final Board board, final Move move) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(move);
        return new Player(
                Piece.MrX.MRX,
                createPlayerTickets(board, Piece.MrX.MRX),
                move.source()
        ).use(move.tickets()).at(getMoveDestination(move));
    }

    /**
     * Moves MrX.
     * @param mrX MrX
     * @param move desired move
     * @return moved MrX
     */
    Player moveMrX(final Player mrX, final Move move) {
        if (mrX.isDetective()) throw new IllegalArgumentException();
        return mrX.use(move.tickets()).at(getMoveDestination(move));
    }

    /**
     * Moves the detectives.
     * @param detectives the detectives
     * @param detectiveMoves the move for each detective
     * @return the moved detectives
     */
    List<Player> moveDetectives(final List<Player> detectives, final Map<Player, Move> detectiveMoves) {
        return detectives.stream()
                .map(detective -> {
                    if (detectiveMoves.containsKey(detective)) {
                        Move move = detectiveMoves.get(detective);
                        return detective.use(move.tickets()).at(getMoveDestination(move));
                    } else
                        return detective;
                }).collect(Collectors.toList());
    }

    /**
     * Determines the destination of a move irrespective of move type.
     * @param move the move
     * @return the destination
     */
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

    /**
     * Creates the detectives from the board.
     * @param board the board
     * @return the detectives
     */
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
