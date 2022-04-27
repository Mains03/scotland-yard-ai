package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

import java.util.HashSet;
import java.util.Set;

public class Node {
    public final ImmutableSet<Node> children;

    public Node(AiBoard board) {
        Set<Node> children = new HashSet<>();
        if (board.getWinner().isEmpty()) {
            for (Move move : board.getAvailableMoves()) {
                AiBoard newBoard = (AiBoard) board.advance(move);
                children.add(new Node(newBoard));
            }
        }
        this.children = ImmutableSet.copyOf(children);
    }
}
