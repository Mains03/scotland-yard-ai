package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.GameTreeEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.MinimaxEvaluation;

/**
 * {@link BestMoveStrategy} using minimax algorithm.
 */
public class GameTreeBestMove implements BestMoveStrategy {
    private static GameTreeBestMove instance;

    public static GameTreeBestMove getInstance() {
        if (instance == null)
            instance = new GameTreeBestMove();
        return instance;
    }

    private GameTreeEvaluationStrategy strategy = MinimaxEvaluation.getInstance();

    @Override
    public Move bestMove(AiBoard board) {
        GameTree tree = GameTreeFactory.getInstance().build(board);
        return strategy.evaluate(tree);
    }
}
