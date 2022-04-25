package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Objects;

/**
 * Singleton helper class to apply a {@link Move} to a {@link Player}.
 */
public class PlayerMoveAdvance {
    private static PlayerMoveAdvance moveApplyFactory;

    public static PlayerMoveAdvance getInstance() {
        if (moveApplyFactory == null)
            moveApplyFactory = new PlayerMoveAdvance();
        return moveApplyFactory;
    }

    private PlayerMoveAdvance() {}

    public Player applyMove(Player player, Move move) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(move);
        player = player.use(move.tickets());
        int destination = moveDestination(move);
        return player.at(destination);
    }

    public int moveDestination(Move move) {
        return move.accept(new Move.Visitor<>() {
            @Override
            public Integer visit(Move.SingleMove move) {
                return move.destination;
            }

            @Override
            public Integer visit(Move.DoubleMove move) {
                return move.destination2;
            }
        });
    }
}
