package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class PiecePlayerFactoryAdapter implements PiecePlayerFactory {
    private final Board board;

    public PiecePlayerFactoryAdapter(Board board) {
        this.board = Objects.requireNonNull(board);
    }

    @Override
    public Optional<Player> createPlayer(Piece piece) {
        Optional<Player> player;
        if (piece.isMrX())
            player = createMrX(piece);
        else
            player = createDetective((Piece.Detective) piece);
        return player;
    }

    private Optional<Player> createMrX(Piece piece) {
        // MrX to move
        // Find any MrX move, the source is MrX's location
        Optional<Integer> location = board.getAvailableMoves().stream()
                .filter(move -> move.commencedBy().isMrX())
                .findAny().stream()
                .map(Move::source)
                .findAny();
        if (location.isEmpty())
            return Optional.empty();
        else {
            Player mrX = new Player(
                    Piece.MrX.MRX,
                    createPlayerTickets(board, Piece.MrX.MRX),
                    location.get()
            );
            return Optional.of(mrX);
        }
    }

    private Optional<Player> createDetective(Piece.Detective piece) {
        Optional<Integer> location = board.getDetectiveLocation(piece);
        if (location.isEmpty())
            return Optional.empty();
        else {
            Player detective = new Player(
                    piece,
                    createPlayerTickets(board, piece),
                    location.get()
            );
            return Optional.of(detective);
        }
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
}
