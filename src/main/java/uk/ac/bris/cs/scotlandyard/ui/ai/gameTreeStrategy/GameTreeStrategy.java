package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2Adapter;

import java.util.NoSuchElementException;
import java.util.Optional;

public class GameTreeStrategy implements BestMoveStrategy {
    private final Optional<Move> mBestMove;

    public GameTreeStrategy(Board board, int depth, GameTreeVisitor gameTreeVisitor) {
        if (depth < 1)
            throw new IllegalArgumentException();
        AiBoardV2 aiBoard = new AiBoardV2Adapter(board);
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
