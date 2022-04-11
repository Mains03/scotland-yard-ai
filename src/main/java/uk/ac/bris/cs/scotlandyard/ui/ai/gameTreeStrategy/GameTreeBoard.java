package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;

public interface GameTreeBoard {
    ImmutableSet<Move> getAvailableMoves();

    GameTreeBoard advance(Move move);

    AiGameState asAiGameState();
}
