package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.SingleTurnLookAheadEvaluation;

/**
 * {@link BestMoveStrategy} which looks one move ahead.
 */
public class SingleTurnLookAheadStrategy extends SingleTurnLookAheadEvaluation implements BestMoveStrategy {
    private static SingleTurnLookAheadStrategy instance;

    public static SingleTurnLookAheadStrategy getInstance() {
        if (instance == null)
            instance = new SingleTurnLookAheadStrategy();
        return instance;
    }

    private SingleTurnLookAheadStrategy() {}

    @Override
    public Move bestMove(AiBoard board) {
        evaluate(board);
        return bestMove;
    }
}
