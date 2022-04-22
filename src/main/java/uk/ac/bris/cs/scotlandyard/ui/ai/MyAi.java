package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.alphaBeta.AlphaBetaStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy.MinDistAlgorithm;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy.algorithms.SimpleBFS;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.minimumDistanceStrategy.MinDistStaticPosEval;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		BestMoveStrategy strategy = createBestMoveStrategy(board);
		return strategy.determineBestMove(board);
	}



	private BestMoveStrategy createBestMoveStrategy(Board board) {
		StaticPosEvalStrategy strategy = createStaticPosEvalStrategy(board);
		return new AlphaBetaStrategy(strategy);
	}

	private StaticPosEvalStrategy createStaticPosEvalStrategy(Board board) {
		MinDistAlgorithm minDistStrategy = createMinDistStrategy(board);
		return new MinDistStaticPosEval(minDistStrategy);
	}

	private MinDistAlgorithm createMinDistStrategy(Board board) {
		var graph = board.getSetup().graph;
		return new SimpleBFS();
	}
}
