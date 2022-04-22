package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

public class PlayerMoveAdvance {
    private static PlayerMoveAdvance moveApplyFactory;

    public static PlayerMoveAdvance getInstance() {
        if (moveApplyFactory == null)
            moveApplyFactory = new PlayerMoveAdvance();
        return moveApplyFactory;
    }

    private PlayerMoveAdvance() {}

    public Player applyMove(Player player, Move move) {
        player = player.use(move.tickets());
        int destination = moveDestination(move);
        return player.at(destination);
    }

    private int moveDestination(Move move) {
        return move.accept(new Move.Visitor<Integer>() {
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
