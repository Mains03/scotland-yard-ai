package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayerV2;

import java.util.List;
import java.util.Set;

public interface AiBoardV2 {
    AiPlayerV2 getMrX();

    List<AiPlayerV2> getDetectives();

    Set<Move> getAvailableMoves();

    AiBoardV2 applyMove(Move move);
}
