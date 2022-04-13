package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.aiPlayerData;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;

import java.util.Set;

public interface AiPlayerData extends AiGameState {
    Set<Move> getAvailableMoves();

    AiPlayerData advance(Move move);
}
