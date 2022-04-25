package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.Optional;

/**
 * {@link Board} adapter providing default functionality.
 */
public abstract class DefaultAiBoard implements AiBoard {
    private final Board board;

    public DefaultAiBoard(Board board) {
        this.board = board;
    }

    @Override
    public GameSetup getSetup() {
        return board.getSetup();
    }

    @Override
    public ImmutableSet<Piece> getPlayers() {
        return board.getPlayers();
    }

    @Override
    public Optional<Integer> getDetectiveLocation(Piece.Detective detective) {
        return board.getDetectiveLocation(detective);
    }

    @Override
    public Optional<TicketBoard> getPlayerTickets(Piece piece) {
        return board.getPlayerTickets(piece);
    }

    @Override
    public ImmutableList<LogEntry> getMrXTravelLog() {
        return board.getMrXTravelLog();
    }

    @Override
    public ImmutableSet<Piece> getWinner() {
        return board.getWinner();
    }

    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        return board.getAvailableMoves();
    }
}
