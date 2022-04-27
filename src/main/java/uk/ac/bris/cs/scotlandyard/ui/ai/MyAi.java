package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.MinimumDistanceEvaluation;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.bestMove.GameTreeBestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.bestMove.MinimaxBestMove;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		BestMoveStrategy strategy = createBestMoveStrategy();
		AiBoard aiBoard = new LocationAiBoard(board);
		return strategy.bestMove(aiBoard);
	}

	private BestMoveStrategy createBestMoveStrategy() {
		MinimumDistanceEvaluation evaluation = MinimumDistanceEvaluation.getInstance();
		GameTreeBestMoveStrategy strategy = new MinimaxBestMove(evaluation, true);
		return new GameTree(strategy);
	}
}
