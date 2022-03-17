package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;
import java.util.stream.Collectors;

public final class MrXMove {
    private final Move bestMove;

    public MrXMove(final Board board) {
        bestMove = createBestMove(board);
    }

    private Move createBestMove(final Board board) {
        // choose the move which maximises the distance between MrX and the detectives
        return board.getAvailableMoves().stream()
                .max(Comparator.comparingInt(
                        move -> findMinDistanceToDetectives(board, getMoveDestination(move))
                ))
                .get();
    }

    private int getMoveDestination(Move move) {
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

    private int findMinDistanceToDetectives(final Board board, int mrXLocation) {
        Collection<Player> detectives = createDetectives(board);
        Dijkstra dijkstra = new Dijkstra(board.getSetup().graph);
        Optional<Integer> minDist = detectives.stream()
                .map(detective -> dijkstra.minimumRouteLength(detective, mrXLocation))
                .flatMap(Optional::stream)
                .min(Integer::compareTo);
        if (minDist.isPresent()) {
            return minDist.get();
        } else {
            return 1000000;
        }
    }

    private Collection<Player> createDetectives(final Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> createDetective(board, piece))
                .collect(Collectors.toList());
    }

    private Player createDetective(final Board board, Piece piece) {
        return new Player(
                piece,
                getPlayerTickets(board, piece),
                getPlayerLocation(board, piece)
        );
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> getPlayerTickets(final Board board, Piece piece) {
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isPresent()) {
            Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values()) {
                tickets.put(ticket, ticketBoard.get().getCount(ticket));
            }
            return ImmutableMap.copyOf(tickets);
        } else {
            return ImmutableMap.of();
        }
    }

    private int getPlayerLocation(final Board board, Piece piece) {
        if (piece.isMrX()) throw new IllegalArgumentException();
        Optional<Integer> location = board.getDetectiveLocation((Piece.Detective) piece);
        if (location.isPresent()) {
            return location.get();
        } else {
            return -1;
        }
    }

    public Move pickMove() {
        return bestMove;
    }
}
