package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;

public class MyAi implements Ai {
	@Nonnull @Override public String name() { return "An Englishman, an Irishman and a Scotsman walk into a bar"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair
	) {
		Dijkstra.createInstance(board.getSetup().graph);
		MinimumDistance.createInstance(board.getSetup().graph);
		AllMoves.createInstance(board.getSetup().graph);
		if (mrXToMove(board)) {
			return pickMrXMove(board);
		} else {
			return pickDetectiveMove(board);
		}
	}

	private boolean mrXToMove(final Board board) {
		return board.getAvailableMoves().stream()
				.anyMatch(move -> move.commencedBy().isMrX());
	}

	private Move pickMrXMove(final Board board) {
		GameTree gameTree = new GameTree(
				board.getSetup(),
				createMrX(board),
				createDetectives(board),
				createRemaining(),
				1
		);
		return gameTree.determineBestMove();
	}

	private Player createMrX(final Board board) {
		return new Player(
				Piece.MrX.MRX,
				createPlayerTickets(board, Piece.MrX.MRX),
				getMrXLocation(board));
	}

	private ImmutableMap<ScotlandYard.Ticket, Integer> createPlayerTickets(final Board board, final Piece piece) {
		Map<ScotlandYard.Ticket, Integer> tickets = new HashMap<>();
		board.getPlayerTickets(piece).ifPresent(ticketBoard -> {
			for (ScotlandYard.Ticket ticket : ScotlandYard.Ticket.values())
				tickets.put(ticket, ticketBoard.getCount(ticket));
		});
		return ImmutableMap.copyOf(tickets);
	}

	private int getMrXLocation(final Board board) {
		return board.getAvailableMoves().stream()
				.findAny()
				.get()
				.source();
	}

	private ImmutableList<Player> createDetectives(final Board board) {
		return ImmutableList.copyOf(
				board.getPlayers().stream()
						.filter(Piece::isDetective)
						.map(piece -> new Player(
								piece,
								createPlayerTickets(board, piece),
								board.getDetectiveLocation((Piece.Detective) piece).get())
						).collect(Collectors.toList())
		);
	}

	private ImmutableList<Piece> createRemaining() {
		return ImmutableList.of(Piece.MrX.MRX);
	}

	private Move pickDetectiveMove(Board board) {
		// returns a random move, replace with your own implementation
		var moves = board.getAvailableMoves().asList();
		return moves.get(new Random().nextInt(moves.size()));
	}
}
