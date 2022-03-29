package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AiBoardAdapter implements AiBoard {
    private final Board board;
    private final AiPlayer mrX;
    private final List<AiPlayer> detectives;

    public AiBoardAdapter(Board board) {
        Objects.requireNonNull(board);
        this.board = board;
        mrX = createMrX(board);
        detectives = createDetectives(board);
    }

    private AiPlayer createMrX(Board board) {
        // MrX to move
        // Find any move, the source is MrX's location
        Optional<Integer> location = board.getAvailableMoves().stream()
                .findAny().stream()
                .map(Move::source)
                .findAny();
        if (location.isEmpty())
            throw new NoSuchElementException("Piece not found");
        return new AiPlayerAdapter(
                board.getSetup().graph,
                new Player(
                    Piece.MrX.MRX,
                    createPlayerTickets(board, Piece.MrX.MRX),
                    location.get()
                )
        );
    }

    private List<AiPlayer> createDetectives(Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> {
                    Optional<Integer> detectiveLocation = board.getDetectiveLocation((Piece.Detective) piece);
                    if (detectiveLocation.isEmpty())
                        throw new NoSuchElementException("Piece not found");
                    return new AiPlayerAdapter(
                            board.getSetup().graph,
                            new Player(
                                    piece,
                                    createPlayerTickets(board, piece),
                                    detectiveLocation.get()
                            )
                    );
                }).collect(Collectors.toList());
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(Board board, Piece piece) {
        ImmutableMap.Builder<ScotlandYard.Ticket, Integer> tickets = ImmutableMap.builder();
        Optional<Board.TicketBoard> ticketBoard = board.getPlayerTickets(piece);
        if (ticketBoard.isEmpty())
            throw new NoSuchElementException("Piece not found");
        else {
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values())
                tickets.put(ticket, ticketBoard.get().getCount(ticket));
        }
        return tickets.build();
    }

    @Override
    public ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph() {
        return board.getSetup().graph;
    }

    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        return board.getAvailableMoves();
    }

    @Override
    public AiPlayer getMrX() {
        return mrX;
    }

    @Override
    public ImmutableList<AiPlayer> getDetectives() {
        return ImmutableList.copyOf(detectives);
    }
}
