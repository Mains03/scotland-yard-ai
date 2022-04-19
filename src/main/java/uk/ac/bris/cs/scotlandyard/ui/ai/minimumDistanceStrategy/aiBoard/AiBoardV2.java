package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Move;
import java.util.Set;

public interface AiBoardV2 {
    Set<Move> getAvailableMoves();

    AiBoardV2 applyMove(Move move);
}
