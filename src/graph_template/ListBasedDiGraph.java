
package graph_template;

import java.util.ArrayList;
import java.util.List;

public class ListBasedDiGraph implements DiGraph {
	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {

		nodeList.add(node);
		return true;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		// GOOD
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());

		targetFromNode.addNeighbor(targetToNode, weight);

		return true;
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasCycles() {
		// TODO Auto-generated method stun
		return null;
	}

	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override

	public GraphNode getNode(String nodeValue) {
		for (GraphNode thisNode : nodeList) {
			if (thisNode.getValue().equals(nodeValue))
				return thisNode;
		}
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		GraphNode start = getNode(fromNode.getValue());
		GraphNode target = getNode(toNode.getValue());

		if (start == null || target == null) {
			return -1;
		}

		if (start.equals(target)) {
			return 0;
		}

		List<GraphNode> visited = new ArrayList<>();
		List<GraphNode> toVisit = new ArrayList<>();
		List<Integer> hops = new ArrayList<>();

		toVisit.add(start);
		hops.add(0);

		while (!toVisit.isEmpty()) {
			GraphNode current = toVisit.remove(0);
			int currHops = hops.remove(0);

			if (current.equals(target)) {
				return currHops;
			}

			visited.add(current);

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor) && !toVisit.contains(neighbor)) {
					toVisit.add(neighbor);
					hops.add(currHops + 1);
				}
			}
		}
		return -1;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return 0;
	}

}
