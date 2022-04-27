package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PotentialDetectiveLocationsAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.List;

public class GameTreeFactory implements AiBoard.Visitor<List<Node>> {
    private static GameTreeFactory instance;

    public static GameTreeFactory getInstance() {
        if (instance == null)
            instance = new GameTreeFactory();
        return instance;
    }

    private GameTreeFactory() {}

    public GameTree build(AiBoard board) {
        return new GameTree(board.accept(this));
    }

    @Override
    public List<Node> visit(StandardAiBoard board) {
        return null;
    }

    @Override
    public List<Node> visit(LocationAiBoard board) {
        return null;
    }

    @Override
    public List<Node> visit(PotentialDetectiveLocationsAiBoard board) {
        return null;
    }
}
