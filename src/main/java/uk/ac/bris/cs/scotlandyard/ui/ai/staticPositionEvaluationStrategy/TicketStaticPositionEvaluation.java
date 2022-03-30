package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class TicketStaticPositionEvaluation implements StaticPositionEvaluationStrategy {
    private final Function<ScotlandYard.Ticket, Integer> ticketEvaluation;

    public TicketStaticPositionEvaluation(
            Function<ScotlandYard.Ticket, Integer> ticketEvaluation
    ) {
        Objects.requireNonNull(ticketEvaluation);
        this.ticketEvaluation = ticketEvaluation;
    }

    @Override
    public int evaluate(AiGameState gameState) {
        int mrXEval = mrXTicketEvaluation(gameState.getMrX());
        int detectivesEval = detectivesTicketEvaluation(gameState.getDetectives());
        return mrXEval - detectivesEval;
    }

    private int mrXTicketEvaluation(Player mrX) {
        int evaluation = 0;
        ImmutableMap<ScotlandYard.Ticket, Integer> mrXTickets = mrX.tickets();
        for (ScotlandYard.Ticket ticket : mrXTickets.keySet())
            evaluation += ticketEvaluation.apply(ticket);
        return evaluation;
    }

    private int detectivesTicketEvaluation(List<Player> detectives) {
        int evaluation = 0;
        for (Player detective : detectives) {
            ImmutableMap<ScotlandYard.Ticket, Integer> tickets = detective.tickets();
            for (ScotlandYard.Ticket ticket : tickets.keySet())
                evaluation += ticketEvaluation.apply(ticket);
        }
        return evaluation;
    }
}
