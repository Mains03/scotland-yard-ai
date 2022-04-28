package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;

/**
 * {@link Board} adapter, behaves exactly as you'd expect.
 */
public class StandardAiBoard extends DefaultGameState implements AiBoard {
    public final Player mrX;

    public StandardAiBoard(Board board) {
        super(GameStateFactory.getInstance().build(board));
        this.mrX = PlayerFactory.getInstance().createMrX(board);
    }

    private StandardAiBoard(GameState gameState, Player mrX) {
        super(gameState);
        this.mrX = mrX;
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        Player mrX;
        if (move.commencedBy().isMrX())
            mrX = PlayerMoveAdvance.getInstance().applyMove(this.mrX, move);
        else
            mrX = this.mrX;
        return new StandardAiBoard(super.advance(move), mrX);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
