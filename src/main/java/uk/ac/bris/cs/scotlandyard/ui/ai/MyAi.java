package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.minimax.MinimaxVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinDistStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.algorithms.SimpleBFS;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies.MinDistStaticPosEval;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		AiBoard aiBoard = new AiBoardAdapter(board);
		MinDistStrategy minDistStrategy = new SimpleBFS();
		StaticPosEvalStrategy evaluationStrategy = new MinDistStaticPosEval(minDistStrategy);
		GameTreeVisitor gameTreeVisitor = new MinimaxVisitor(false, evaluationStrategy);
		BestMoveStrategy bestMoveStrategy = new GameTree(
				board,
				3,
				gameTreeVisitor
		);
		return bestMoveStrategy.determineBestMove();
	}
}
