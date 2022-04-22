package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.Optional;

public class AiBoard implements Board.GameState {
    public final Board.GameState gameState;

    public final Player mrX;

    public AiBoard(Board board) {
        GameSetup gameSetup = board.getSetup();
        Player mrX = PlayerFactory.getInstance().createMrX(board);
        ImmutableList<Player> detectives = ImmutableList.copyOf(
                PlayerFactory.getInstance().createDetectives(board)
        );
        gameState = MyGameStateFactory.a(
                gameSetup,
                mrX,
                detectives
        );
        this.mrX = mrX;
    }

    private AiBoard(GameState gameState, Player mrX) {
        this.gameState = gameState;
        this.mrX = mrX;
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        GameState gameState = this.gameState.advance(move);
        Player mrX = this.mrX;
        if (move.commencedBy().isMrX())
            mrX = PlayerMoveAdvance.getInstance().applyMove(this.mrX, move);
        return new AiBoard(gameState, mrX);
    }

    @Nonnull
    @Override
    public GameSetup getSetup() {
        return gameState.getSetup();
    }

    @Nonnull
    @Override
    public ImmutableSet<Piece> getPlayers() {
        return gameState.getPlayers();
    }

    @Nonnull
    @Override
    public Optional<Integer> getDetectiveLocation(Piece.Detective detective) {
        return gameState.getDetectiveLocation(detective);
    }

    @Nonnull
    @Override
    public Optional<TicketBoard> getPlayerTickets(Piece piece) {
        return gameState.getPlayerTickets(piece);
    }

    @Nonnull
    @Override
    public ImmutableList<LogEntry> getMrXTravelLog() {
        return gameState.getMrXTravelLog();
    }

    @Nonnull
    @Override
    public ImmutableSet<Piece> getWinner() {
        return gameState.getWinner();
    }

    @Nonnull
    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        return gameState.getAvailableMoves();
    }
}
