package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors.MinimaxVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinDistStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies.MinDistStaticPosEval;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		MinDistStrategyFactory minDistStrategyFactory = new MinDistStrategyFactory(board);
		MinDistStrategy<Pair<Integer, Integer>> minDistStrategy = minDistStrategyFactory.createSimpleBFSStrategy();
		StaticPosEvalStrategy evaluationStrategy = new MinDistStaticPosEval(minDistStrategy);
		GameTreeVisitor gameTreeVisitor = new MinimaxVisitor(evaluationStrategy);
		BestMoveStrategy bestMoveStrategy = new GameTree(
				board,
				3,
				gameTreeVisitor
		);
		return bestMoveStrategy.determineBestMove();
	}
}
