package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "D:"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair) {
		if (mrXToMove(board)) {
			return pickMrXMove(board);
		} else {
			return pickDetectiveMove(board);
		}
	}

	private boolean mrXToMove(Board board) {
		return board.getAvailableMoves().stream()
				.anyMatch(move -> move.commencedBy().isMrX());
	}

	private Move pickMrXMove(Board board) {
		return null;
	}

	private Move pickDetectiveMove(Board board) {
		// returns a random move, replace with your own implementation
		var moves = board.getAvailableMoves().asList();
		return moves.get(new Random().nextInt(moves.size()));
	}
}
