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

    public PotentialDetectiveLocationsAiBoard(Board board) {
        super(board);
        mrX = PlayerFactory.getInstance().createMrX(board);
        potentialDetectiveLocations = ImmutableSet.copyOf(
                PlayerFactory.getInstance().createDetectives(board).stream()
                        .map(Player::location)
                        .collect(Collectors.toSet())
        );
    }

    private PotentialDetectiveLocationsAiBoard(GameState gameState, Player mrX, ImmutableSet<Integer> potentialDetectiveLocations) {
        super(gameState);
        this.mrX = mrX;
        this.potentialDetectiveLocations = potentialDetectiveLocations;
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        if (move.commencedBy().isMrX()) {
            Player mrX = PlayerMoveAdvance.getInstance().applyMove(this.mrX, move);
            return new PotentialDetectiveLocationsAiBoard(super.advance(move), mrX, potentialDetectiveLocations);
        } else {
            // update set of potential detective locations
            Set<Integer> potentialDetectiveLocations = new HashSet<>();
            for (int location : this.potentialDetectiveLocations) {
                potentialDetectiveLocations.add(location);
                potentialDetectiveLocations.addAll(board.getSetup().graph.adjacentNodes(location));
            }
            return new PotentialDetectiveLocationsAiBoard(gameState, mrX, ImmutableSet.copyOf(potentialDetectiveLocations));
        }
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
    public ImmutableSet<Move> getAvailableMoves() {
        return MrXMoveFactory.getInstance().getAvailableMoves(board.getSetup().graph, mrX);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
