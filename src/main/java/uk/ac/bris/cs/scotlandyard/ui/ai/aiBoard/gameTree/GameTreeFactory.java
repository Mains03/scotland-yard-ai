package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PotentialDetectiveLocationsAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited.DepthLimitedGameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited.WinnerLimitedGameTree;

public class GameTreeFactory implements AiBoard.Visitor<GameTree> {
    private static GameTreeFactory instance;

    public static GameTreeFactory getInstance() {
        if (instance == null)
            instance = new GameTreeFactory();
        return instance;
    }

    private GameTreeFactory() {}

    public GameTree build(AiBoard board) {
        return board.accept(this);
    }

    @Override
    public GameTree visit(StandardAiBoard board) {
        return new DepthLimitedGameTree(board);
    }

    @Override
    public GameTree visit(LocationAiBoard board) {
        return new DepthLimitedGameTree(board);
    }

    @Override
    public GameTree visit(PotentialDetectiveLocationsAiBoard board) {
        return new WinnerLimitedGameTree(board);
    }
}
