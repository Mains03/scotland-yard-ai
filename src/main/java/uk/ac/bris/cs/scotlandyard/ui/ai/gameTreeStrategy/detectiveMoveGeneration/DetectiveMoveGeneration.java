package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Set;

public interface DetectiveMoveGeneration {
    /**
     * Generates all sets of detective moves
     * @param aiBoard the current game board
     * @return a set of {@link StandardAiBoard} with detectives in new locations
     */
    Set<StandardAiBoard> moveDetectives(StandardAiBoard aiBoard);
}

