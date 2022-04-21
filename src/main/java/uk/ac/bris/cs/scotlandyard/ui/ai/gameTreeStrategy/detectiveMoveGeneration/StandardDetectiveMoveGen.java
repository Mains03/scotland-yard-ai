package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.List;

public class StandardDetectiveMoveGen implements DetectiveMoveGeneration {
    private static StandardDetectiveMoveGen instance;

    public static StandardDetectiveMoveGen getInstance() {
        if (instance == null)
            instance = new StandardDetectiveMoveGen();
        return instance;
    }

    private StandardDetectiveMoveGen() {}

    @Override
    public List<AiBoard> moveDetectives(AiBoard aiBoard) {
        throw new RuntimeException("Not implemented");
    }
}
