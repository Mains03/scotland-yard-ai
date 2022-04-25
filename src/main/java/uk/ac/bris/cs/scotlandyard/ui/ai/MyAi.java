package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms.BFS;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms.MinDistAlgorithm;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.StaticPosEvalStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.minimumDistanceStrategy.MinDistStaticPosEval;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		BestMoveStrategy strategy = createBestMoveStrategy();
		return strategy.determineBestMove(new StandardAiBoard(board));
	}

	private BestMoveStrategy createBestMoveStrategy() {
		StaticPosEvalStrategy strategy = createStaticPosEvalStrategy();
		return new SingleTurnLookAheadStrategy(strategy, true);
	}

	private StaticPosEvalStrategy createStaticPosEvalStrategy() {
		MinDistAlgorithm minDistStrategy = new BFS();
		return new MinDistStaticPosEval(minDistStrategy);
	}
}
