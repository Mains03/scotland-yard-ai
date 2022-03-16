package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Objects;

public interface GameState {
    public Move pickMove();

    public class MrXMoveGameState implements GameState {
        public MrXMoveGameState(Board board) {
            Objects.requireNonNull(board);
        }

        @Override
        public Move pickMove() {
            return null;
        }
    }

    public class DetectiveMoveGameState implements GameState {
        @Override
        public Move pickMove() {
            return null;
        }
    }
}
