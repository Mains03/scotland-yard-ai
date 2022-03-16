package uk.ac.bris.cs.scotlandyard.ui.ai;

import ch.qos.logback.core.util.InvocationGate;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.google.common.collect.ImmutableList;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

public class TestsAI {
    TestsAI(ImmutableValueGraph<Integer, ImmutableList<ScotlandYard.Transport>> graph, Player mrX, ImmutableList<Player> detectives) {
        /*Integer source = mrX.location();
        Dijkstra dijkstra = new Dijkstra(graph);

        Integer destination = graph.adjacentNodes(source).stream()
                .max(node -> (detectives.stream()
                                        .min(detective -> dijkstra.shortest(source, node, detective))));
        System.out.println("Best node is: " + destination);*/

    }
}
