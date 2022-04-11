package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;

public class GameTreeBoardAdapter implements GameTreeBoard {
    public GameTreeBoardAdapter(Board board) {

    }

    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        return null;
    }

    @Override
    public GameTreeBoard advance(Move move) {
        return null;
    }

    @Override
    public AiGameState asAiGameState() {
        return null;
    }
}
