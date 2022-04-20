package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2Adapter;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
    private static final int MAX_DEPTH = 3;

    // each GameTreeNode corresponds to a MrX move
    private final List<GameTreeNode> gameTreeNodes;

    public GameTree(Board board) {
        gameTreeNodes = createGameTreeNodes(board, MAX_DEPTH);
    }

    private List<GameTreeNode> createGameTreeNodes(Board board, int depth) {
        List<GameTreeNode> gameTreeNodes = new ArrayList<>();
        AiBoardV2 aiBoard = new AiBoardV2Adapter(board);
        for (Move move : aiBoard.getAvailableMoves()) {
            GameTreeNode gameTreeNode = createGameTreeNode(aiBoard, depth, move);
            gameTreeNodes.add(gameTreeNode);
        }
        return gameTreeNodes;
    }

    private GameTreeNode createGameTreeNode(AiBoardV2 board, int depth, Move move) {
        return new GameTreeNodeWithMrXMove(board, depth, move);
    }

    public List<GameTreeNode> getGameTreeNodes() {
        return List.copyOf(gameTreeNodes);
    }
}
