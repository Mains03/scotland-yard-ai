package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.AbstractInnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.DetectiveMoveBoardFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

import java.util.*;

/**
 * Node with children.
 */
public class InnerNode extends AbstractInnerNode {
    private final Set<Node> children;

    public InnerNode(AiBoard board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<Node> createChildren(AiBoard board, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException();
        Set<Node> children = new HashSet<>();
        if (board.getWinner().size() == 0) {
            Set<AiBoard> newBoards = generateNewBoards(board);
            for (AiBoard newBoard : newBoards) {
                Node child = createChild(newBoard, depth);
                children.add(child);
            }
        }
        return children;
    }

    // possible boards reached by either moving MrX or all the detectives
    private Set<AiBoard> generateNewBoards(AiBoard board) {
        Set<AiBoard> newBoards = new HashSet<>();
        if (board.getAvailableMoves().size() > 0) {
            boolean mrXMove = board.getAvailableMoves().stream().findAny().get().commencedBy().isMrX();
            if (mrXMove) {
                for (Move move : board.getAvailableMoves()) {
                    AiBoard newBoard = (AiBoard) board.advance(move);
                    newBoards.add(newBoard);
                }
            } else
                newBoards = DetectiveMoveBoardFactory.getInstance().generate(board);
        }
        return newBoards;
    }

    private Node createChild(AiBoard board, int depth) {
        Node child;
        if (depth == 1)
            child = new LeafNode(board);
        else
            child = new InnerNode(board, depth-1);
        return child;
    }

    public Set<Node> getChildren() {
        return Set.copyOf(children);
    }
}
