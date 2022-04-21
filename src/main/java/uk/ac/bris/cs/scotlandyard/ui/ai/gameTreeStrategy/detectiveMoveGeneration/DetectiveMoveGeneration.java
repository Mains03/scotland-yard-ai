package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.List;

public interface DetectiveMoveGeneration {
    List<AiBoard> moveDetectives(AiBoard aiBoard);
}

