package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.Optional;

public class DefaultBoard implements Board {
    protected final Board board;

    public DefaultBoard(Board board) {
        this.board = board;
    }

    @Nonnull
    @Override
    public GameSetup getSetup() {
        return board.getSetup();
    }

    @Nonnull
    @Override
    public ImmutableSet<Piece> getPlayers() {
        return board.getPlayers();
    }

    @Nonnull
    @Override
    public Optional<Integer> getDetectiveLocation(Piece.Detective detective) {
        return board.getDetectiveLocation(detective);
    }

    @Nonnull
    @Override
    public Optional<TicketBoard> getPlayerTickets(Piece piece) {
        return board.getPlayerTickets(piece);
    }

    @Nonnull
    @Override
    public ImmutableList<LogEntry> getMrXTravelLog() {
        return board.getMrXTravelLog();
    }

    @Nonnull
    @Override
    public ImmutableSet<Piece> getWinner() {
        return board.getWinner();
    }

    @Nonnull
    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        return board.getAvailableMoves();
    }
}
