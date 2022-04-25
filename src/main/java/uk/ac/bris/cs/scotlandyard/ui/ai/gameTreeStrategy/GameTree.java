package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures.GameTreeNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures.InnerNodeWithMrXMove;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
    // each GameTreeNode corresponds to a MrX move
    private final List<GameTreeNode> gameTreeNodes;

    public GameTree(Board board) {
        StandardAiBoard aiBoard = new StandardAiBoard(board);
        gameTreeNodes = createGameTreeNodes(aiBoard);
    }

    private List<GameTreeNode> createGameTreeNodes(StandardAiBoard board) {
        List<GameTreeNode> gameTreeNodes = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            GameTreeNode gameTreeNode = createGameTreeNode(board, move);
            gameTreeNodes.add(gameTreeNode);
        }
        return gameTreeNodes;
    }

    private GameTreeNode createGameTreeNode(StandardAiBoard board, Move move) {
        StandardAiBoard newBoard = (StandardAiBoard) board.advance(move);
        GameTreeNode node = new InnerNodeWithMrXMove(newBoard, 2, move);
        return node;
    }

    public List<GameTreeNode> getGameTreeNodes() {
        return List.copyOf(gameTreeNodes);
    }
}
