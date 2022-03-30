package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.BreadthFirstSearchMinimumDistance;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MemoizedMinimumDistance;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinimumDistanceWithTickets;
import uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy.MinimumDistanceSingleTurnLookAhead;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = board.getSetup().graph;

		return determineBestMove(new MinimumDistanceSingleTurnLookAhead(
				board,
				new BreadthFirstSearchMinimumDistance(graph)
		));
	}

	private Move determineBestMove(BestMoveStrategy strategy) {
		return strategy.determineBestMove();
	}
}
