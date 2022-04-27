package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PotentialDetectiveLocationsAiBoard extends DefaultGameState implements AiBoard {
    private final Player mrX;

    private final ImmutableSet<Integer> potentialDetectiveLocations;

    private final boolean mrXTurn;

    public PotentialDetectiveLocationsAiBoard(Board board) {
        super(GameStateFactory.getInstance().build(board));
        mrX = PlayerFactory.getInstance().createMrX(board);
        potentialDetectiveLocations = ImmutableSet.copyOf(
                PlayerFactory.getInstance().createDetectives(board).stream()
                        .map(Player::location)
                        .collect(Collectors.toSet())
        );
        this.mrXTurn = true;
    }

    private PotentialDetectiveLocationsAiBoard(GameState gameState, Player mrX, ImmutableSet<Integer> potentialDetectiveLocations, boolean mrXTurn) {
        super(gameState);
        this.mrX = mrX;
        this.potentialDetectiveLocations = potentialDetectiveLocations;
        this.mrXTurn = mrXTurn;
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        if (move.commencedBy().isMrX()) {
            Player mrX = PlayerMoveAdvance.getInstance().applyMove(this.mrX, move);
            return new PotentialDetectiveLocationsAiBoard(super.advance(move), mrX, potentialDetectiveLocations, false);
        } else {
            // update set of potential detective locations
            Set<Integer> potentialDetectiveLocations = new HashSet<>();
            for (int location : this.potentialDetectiveLocations) {
                potentialDetectiveLocations.add(location);
                potentialDetectiveLocations.addAll(board.getSetup().graph.adjacentNodes(location));
            }
            return new PotentialDetectiveLocationsAiBoard(gameState, mrX, ImmutableSet.copyOf(potentialDetectiveLocations), true);
        }
    }

    public GameState advanceDetectives() {
        // random values since doesn't matter
        return advance(new Move.SingleMove(Piece.Detective.BLUE, 1, ScotlandYard.Ticket.TAXI, 2));
    }

    @Nonnull
    @Override
    public Optional<Integer> getDetectiveLocation(Piece.Detective detective) {
        return Optional.empty();
    }

    @Nonnull
    @Override
    public Optional<TicketBoard> getPlayerTickets(Piece piece) {
        Optional<TicketBoard> mTicketBoard;
        if (piece.isMrX())
            mTicketBoard = super.getPlayerTickets(piece);
        else
            mTicketBoard = Optional.empty();
        return mTicketBoard;
    }

    @Nonnull
    @Override
    public ImmutableSet<Piece> getWinner() {
        Set<Piece> winner = new HashSet<>();
        if (potentialDetectiveLocations.contains(mrX.location()))
            winner.addAll(getPlayers().stream().filter(Piece::isDetective).collect(Collectors.toSet()));
        return ImmutableSet.copyOf(winner);
    }

    @Nonnull
    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        if (mrXTurn)
            return MrXMoveFactory.getInstance().getAvailableMoves(board.getSetup().graph, mrX);
        else
            // random values since doesn't matter
            return ImmutableSet.of(new Move.SingleMove(Piece.Detective.BLUE, 1, ScotlandYard.Ticket.TAXI, 2));
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
