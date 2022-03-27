package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTree.GameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.moves.AllMoves;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		MinimumDistance.createInstance(board.getSetup().graph);
		AllMoves.createInstance(board.getSetup().graph);
		// assume MrX to move
		return new GameTree(board).determineBestMove();
	}
}
