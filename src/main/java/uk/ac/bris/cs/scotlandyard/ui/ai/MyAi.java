package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors.MinimaxVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.BreadthFirstSearchMinimumDistance;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinDistStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinDistStrategyFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinimumDistanceAlgorithmStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinimumDistanceStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.BreadthFirstSearch;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.MinimumDistance;
import uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy.MinimumDistanceSingleTurnLookAhead;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies.MinimumDistanceStaticPositionEvaluation;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		MinDistStrategyFactory minDistStrategyFactory = new MinDistStrategyFactory(board);
		MinDistStrategy minDistStrategy = minDistStrategyFactory.createSimpleBFSStrategy();
		GameTreeVisitor gameTreeVisitor = new MinimaxVisitor(evaluationStrategy);
		BestMoveStrategy bestMoveStrategy = new GameTreeStrategy(
				board,
				3,
				gameTreeVisitor
		);
		return bestMoveStrategy.determineBestMove();
	}
}
