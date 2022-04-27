package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.bestMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.MinimaxEvaluation;

/**
 * {@link BestMoveStrategy} using minimax algorithm.
 */
public class MinimaxBestMove extends MinimaxEvaluation implements BestMoveStrategy {
    private static MinimaxBestMove instance;

    public static MinimaxBestMove getInstance() {
        if (instance == null)
            instance = new MinimaxBestMove();
        return instance;
    }

    private MinimaxBestMove() {
        super();
    }

    @Override
    public Move bestMove(AiBoard board) {
        GameTree tree = new GameTree(board);
        evaluate(tree);
        return bestMove;
    }
}
