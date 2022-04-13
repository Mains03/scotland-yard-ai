package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;

import java.util.Set;

public class GameTreePlayerAdapter implements GameTreePlayer {
    public GameTreePlayerAdapter(Board board, Piece piece) {

    }

    @Override
    public Set<Move> getAvailableMoves() {
        return null;
    }

    @Override
    public void markMoved() {

    }

    @Override
    public void unmarkMoved() {

    }

    @Override
    public boolean hasMoved() {
        return false;
    }
}
