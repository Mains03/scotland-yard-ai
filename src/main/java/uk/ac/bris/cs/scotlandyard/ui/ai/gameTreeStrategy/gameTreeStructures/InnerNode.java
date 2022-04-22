package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration.DetectiveMoveGeneration;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration.SimpleDetectiveMoveGen;

import java.util.*;

/**
 * Node with children.
 */
public class InnerNode implements GameTreeNode {
    private final Set<GameTreeNode> children;

    public InnerNode(AiBoard board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<GameTreeNode> createChildren(AiBoard board, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException();
        Set<GameTreeNode> children = new HashSet<>();
        Set<AiBoard> newBoards = generateNewBoards(board);
        for (AiBoard newBoard : newBoards) {
            GameTreeNode child = createChild(newBoard, depth);
            children.add(child);
        }
        return children;
    }

    // possible boards reached by either moving MrX or all the detectives
    private Set<AiBoard> generateNewBoards(AiBoard board) {
        Set<AiBoard> newBoards;
        if (isMrXMove(board))
            newBoards = moveMrX(board);
        else
            newBoards = moveDetectives(board);
        return newBoards;
    }

    private boolean isMrXMove(AiBoard board) {
        Move move = getAnyMove(board);
        Piece piece = move.commencedBy();
        return piece.isMrX();
    }

    private Move getAnyMove(AiBoard board) {
        Optional<Move> move = board.getAvailableMoves().stream()
                .findAny();
        if (move.isEmpty())
            throw new NoSuchElementException("No moves");
        return move.get();
    }

    // possible boards by moving MrX
    private Set<AiBoard> moveMrX(AiBoard board) {
        Set<AiBoard> boards = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = board.applyMove(move);
            boards.add(newBoard);
        }
        return boards;
    }

    // possible boards by moving detectives
    private Set<AiBoard> moveDetectives(AiBoard board) {
        DetectiveMoveGeneration moveGeneration = getDetectiveMoveGeneration();
        return moveGeneration.moveDetectives(board);
    }

    private DetectiveMoveGeneration getDetectiveMoveGeneration() {
        return SimpleDetectiveMoveGen.getInstance();
        //return new CombinationDetectiveMoveGen();
    }

    private GameTreeNode createChild(AiBoard board, int depth) {
        GameTreeNode child;
        if (depth == 1)
            child = new LeafNode(board);
        else
            child = new InnerNode(board, depth-1);
        return child;
    }

    @Override
    public int accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> getMrXMove() {
        // MrX didn't make a move to get here
        return Optional.empty();
    }

    public Set<GameTreeNode> getChildren() {
        return Set.copyOf(children);
    }
}
