package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Set;

public interface DetectiveMoveGeneration {
    Set<StandardAiBoard> moveDetectives(StandardAiBoard aiBoard);
}

