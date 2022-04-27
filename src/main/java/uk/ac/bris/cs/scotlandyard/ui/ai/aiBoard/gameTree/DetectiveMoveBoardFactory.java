package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PotentialDetectiveLocationsAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Set;

/**
 * Generates the resulting positions after moving all the detectives.
 */
public class DetectiveMoveBoardFactory implements AiBoard.Visitor<Set<AiBoard>> {
    private static DetectiveMoveBoardFactory instance;

    public static DetectiveMoveBoardFactory getInstance() {
        if (instance == null)
            instance = new DetectiveMoveBoardFactory();
        return instance;
    }

    protected DetectiveMoveBoardFactory() {}

    public Set<AiBoard> generate(AiBoard board) {
        return board.accept(this);
    }

    @Override
    public Set<AiBoard> visit(StandardAiBoard board) {
        return StandardDetectiveMoveBoardFactory.getInstance().generate(board);
    }

    @Override
    public Set<AiBoard> visit(LocationAiBoard board) {
        return StandardDetectiveMoveBoardFactory.getInstance().generate(board);
    }

    @Override
    public Set<AiBoard> visit(PotentialDetectiveLocationsAiBoard board) {
        return Set.of((AiBoard) board.advanceDetectives());
    }
}
