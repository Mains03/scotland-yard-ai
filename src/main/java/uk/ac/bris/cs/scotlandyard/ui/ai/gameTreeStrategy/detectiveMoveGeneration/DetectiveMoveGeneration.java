package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.List;
import java.util.Set;

public interface DetectiveMoveGeneration {
    Set<AiBoard> moveDetectives(AiBoard aiBoard);
}

