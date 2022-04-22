package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.Set;

/**
 * Singleton extension of {@link SimpleDetectiveMoveGen} that records the time taken
 */
public class TimedSimpleDetectiveMoveGen extends SimpleDetectiveMoveGen implements DetectiveMoveGeneration {
    private static TimedSimpleDetectiveMoveGen instance;

    public static TimedSimpleDetectiveMoveGen getInstance() {
        if (instance == null)
            instance = new TimedSimpleDetectiveMoveGen();
        return instance;
    }

    private TimedSimpleDetectiveMoveGen() {}

    @Override
    public Set<AiBoard> moveDetectives(AiBoard board) {
        final long startTime = System.currentTimeMillis();
        Set<AiBoard> boards = super.moveDetectives(board);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        return boards;
    }
}
