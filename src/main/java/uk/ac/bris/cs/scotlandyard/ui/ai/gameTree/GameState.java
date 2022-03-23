package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

final class GameState {
    private final Optional<Move> move;

    private final Player mrX;
    private final List<Player> detectives;

    GameState(final Board board, final Move move) {
        this.move = Optional.of(move);
        this.mrX = createMrX(board, move);
        this.detectives = createDetectives(board);
    }

    private Player createMrX(final Board board, final Move move) {
        return PlayerFactory.getInstance().createMrX(board, move);
    }

    private List<Player> createDetectives(final Board board) {
        return PlayerFactory.getInstance().createDetectives(board);
    }

    Collection<GameState> nextGameStates() {
        return null;
    }

    int minDistanceBetweenDetectivesAndMrX() {
        return 0;
    }

    Move getMove() {
        if (move.isEmpty()) throw new NoSuchElementException();
        return move.get();
    }
}
