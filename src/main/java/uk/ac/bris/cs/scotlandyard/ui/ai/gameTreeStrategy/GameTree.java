package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.InnerNodeWithMrXMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.LeafNodeWithMrXMove;

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
        if (depth < 1)
            throw new IllegalArgumentException();
        List<GameTreeNode> gameTreeNodes = new ArrayList<>();
        AiBoard aiBoard = new AiBoardAdapter(board);
        for (Move move : aiBoard.getAvailableMoves()) {
            GameTreeNode gameTreeNode = createGameTreeNode(aiBoard, depth-1, move);
            gameTreeNodes.add(gameTreeNode);
        }
        return gameTreeNodes;
    }

    private GameTreeNode createGameTreeNode(AiBoard board, int depth, Move move) {
        AiBoard newBoard = board.applyMove(move);
        GameTreeNode node;
        if (depth == 0)
            node = new LeafNodeWithMrXMove(newBoard, move);
        else
            node = new InnerNodeWithMrXMove(newBoard, depth, move);
        return node;
    }

    public List<GameTreeNode> getGameTreeNodes() {
        return List.copyOf(gameTreeNodes);
    }
}
