package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Collection;
import java.util.Objects;

public interface GameState {
    Move pickMove();

    class MrXMoveGameState implements GameState {
        private final Player mrX;
        private final Collection<Player> detectives;
        private final Move move;

        public MrXMoveGameState(Board board) {
            Objects.requireNonNull(board);
            mrX = createMrX(board);
            detectives = createDetectives();
            move = createBestMove();
        }

        private Player createMrX(Board board) {
            return null;
        }

        private Collection<Player> createDetectives() {
            return null;
        }

        private Move createBestMove() {
            return null;
        }

        @Override
        public Move pickMove() {
            return move;
        }
    }

    class DetectiveMoveGameState implements GameState {
        @Override
        public Move pickMove() {
            return null;
        }
    }
}
