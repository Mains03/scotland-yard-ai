package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Set;

public interface DetectiveMoveGeneration {
    Set<StandardAiBoard> moveDetectives(StandardAiBoard aiBoard);
}

