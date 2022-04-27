package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import javax.annotation.Nonnull;

public class DefaultGameState extends DefaultBoard implements Board.GameState {
    protected final GameState gameState;

    public DefaultGameState(Board board) {
        super(board);
        gameState = GameStateFactory.getInstance().build(board);
    }

    private DefaultGameState(GameState gameState) {
        super(gameState);
        this.gameState = gameState;
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        return new DefaultGameState(gameState.advance(move));
    }
}
