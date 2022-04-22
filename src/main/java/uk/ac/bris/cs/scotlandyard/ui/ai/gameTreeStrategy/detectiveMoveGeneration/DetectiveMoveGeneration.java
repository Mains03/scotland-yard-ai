package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.List;
import java.util.Set;

public interface DetectiveMoveGeneration {
    /**
     * Generates all sets of detective moves
     * @param aiBoard the current game board
     * @return a set of {@link AiBoard} with detectives in new locations
     */
    Set<AiBoard> moveDetectives(AiBoard aiBoard);
}

