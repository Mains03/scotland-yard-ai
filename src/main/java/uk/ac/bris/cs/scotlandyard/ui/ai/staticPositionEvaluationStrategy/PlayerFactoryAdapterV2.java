package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerFactoryAdapterV2 implements PlayerFactory {
    /**
     * Throws UnsupportedOperationException since the method is deprecated.
     */
    @Override
    public Player createMrX() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Player createMrX(Board board) {
        Piece piece = Piece.MrX.MRX;
        ImmutableMap<ScotlandYard.Ticket, Integer> tickets = createPlayerTickets(board, piece);
        int location = getMrXLocation(board);
        return new Player(
                piece,
                tickets,
                location
        );
    }

    private int getMrXLocation(Board board) {
        Optional<Integer> mMrxLocation = board.getAvailableMoves().stream()
                .filter(move -> move.commencedBy().isMrX())
                .findAny().stream()
                .map(Move::source)
                .findAny();
        if (mMrxLocation.isEmpty())
            throw new NoSuchElementException("No MrX moves");
        else
            return mMrxLocation.get();
    }

    /**
     * Throws UnsupportedOperationException since the method is deprecated.
     */
    @Override
    public List<Player> createDetectives() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Player> createDetectives(Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> createDetective(board, (Piece.Detective) piece))
                .collect(Collectors.toList());
    }

    private Player createDetective(Board board, Piece.Detective detective) {
        ImmutableMap<ScotlandYard.Ticket, Integer> tickets = createPlayerTickets(board, detective);
        int location = getDetectiveLocation(board, detective);
        return new Player(
                detective,
                tickets,
                location
        );
    }

    private int getDetectiveLocation(Board board, Piece.Detective detective) {
        Optional<Integer> mLocation = board.getDetectiveLocation(detective);
        if (mLocation.isEmpty())
            throw new NoSuchElementException("Failed to find " + detective);
        else
            return mLocation.get();
    }

    /**
     * Throws UnsupportedOperationException since the method is deprecated.
     */
    @Override
    public Player createFromPiece(Piece piece) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Player createFromPiece(Board board, Piece piece) {
        if (piece.isMrX())
            return createMrX(board);
        else
            return createDetective(board, (Piece.Detective) piece);
    }

    private ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(Board board, Piece piece) {
        ImmutableMap.Builder<ScotlandYard.Ticket, Integer> bTickets = ImmutableMap.builder();
        Optional<Board.TicketBoard> mTicketBoard = board.getPlayerTickets(piece);
        if (mTicketBoard.isEmpty())
            throw new NoSuchElementException("Failed to find " + piece);
        else {
            Board.TicketBoard ticketBoard = mTicketBoard.get();
            for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values())
                bTickets.put(ticket, ticketBoard.getCount(ticket));
            return bTickets.build();
        }
    }
}
