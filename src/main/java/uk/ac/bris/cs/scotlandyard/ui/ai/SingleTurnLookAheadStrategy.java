package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.SingleTurnLookAheadEval;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

/**
 * {@link BestMoveStrategy} which looks one move ahead.
 */
public class SingleTurnLookAheadStrategy extends SingleTurnLookAheadEval implements BestMoveStrategy {
    public SingleTurnLookAheadStrategy(StaticPosEvalStrategy strategy, boolean maximise) {
        super(strategy, maximise);
    }

    @Override
    public Move determineBestMove(StandardAiBoard board) {
        super.evaluate(board);
        return getBestMove();
    }

    @Override
    public Move determineBestMove(LocationAiBoard board) {
        super.evaluate(board);
        return getBestMove();
    }
}
