package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import javax.annotation.Nonnull;

/**
 * {@link Board} adapter.
 */
public class StandardAiBoard extends DefaultAiBoard implements AiBoard {
    public final Board.GameState gameState;

    public final Player mrX;

    public StandardAiBoard(Board board) {
        super(board);
        GameSetup gameSetup = board.getSetup();
        GameStateFactory factory = GameStateFactory.getInstance();
        gameState = factory.build(board);
        this.mrX = PlayerFactory.getInstance().createMrX(board);
    }

    private StandardAiBoard(GameState gameState, Player mrX) {
        super(gameState);
        this.gameState = gameState;
        this.mrX = mrX;
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        GameState gameState = this.gameState.advance(move);
        Player mrX;
        if (move.commencedBy().isMrX())
            mrX = PlayerMoveAdvance.getInstance().applyMove(this.mrX, move);
        else
            mrX = this.mrX;
        return new StandardAiBoard(gameState, mrX);
    }

    @Override
    public int score(StaticPosEvalStrategy strategy) {
        return strategy.evaluate(this);
    }
}
