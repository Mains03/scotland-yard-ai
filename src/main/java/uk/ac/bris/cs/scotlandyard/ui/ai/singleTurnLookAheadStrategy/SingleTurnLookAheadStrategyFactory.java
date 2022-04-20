package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies.MinDistStaticPosEval;

/**
 * Simplifies single turn look ahead strategy instantiations.
 */
public class SingleTurnLookAheadStrategyFactory {
    public SingleTurnLookAheadStrategy createMinDistStrategy(Board board, MinDistStaticPosEval strategy) {
        return new SingleTurnLookAheadStrategy() {
            @Override
            public int staticEvaluation(AiBoard board) {
                return strategy.evaluate(board);
            }
        };
    }
}
