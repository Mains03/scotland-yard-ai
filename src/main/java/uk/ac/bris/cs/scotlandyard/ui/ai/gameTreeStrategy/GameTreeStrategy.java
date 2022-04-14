package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardAdapter;

import java.util.NoSuchElementException;
import java.util.Optional;

public class GameTreeStrategy implements BestMoveStrategy {
    private final Optional<Move> mBestMove;

    public GameTreeStrategy(Board board, int depth, GameTreeVisitor gameTreeVisitor) {
        if (depth < 1)
            throw new IllegalArgumentException();
        AiBoard aiBoard = new AiBoardAdapter(board);
        GameTree gameTree = new GameTreeInnerNode(aiBoard, depth);
        mBestMove = gameTree.accept(gameTreeVisitor);
    }

    @Override
    public Move determineBestMove() {
        if (mBestMove.isEmpty())
            throw new NoSuchElementException();
        else
            return mBestMove.get();
    }
}
