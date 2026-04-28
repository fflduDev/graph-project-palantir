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
		if (node == null || node.getValue() == null)
			return false;

		GraphNode target = getNode(node.getValue());
		if (target == null)
			return false;

		for (GraphNode thisNode : nodeList) {
			thisNode.removeNeighbor(target);
		}

		return nodeList.remove(target);
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		if (node == null || node.getValue() == null || newNodeValue == null)
			return false;

		GraphNode target = getNode(node.getValue());
		if (target == null)
			return false;

		if (target.getValue().equals(newNodeValue))
			return true;

		GraphNode existing = getNode(newNodeValue);
		if (existing != null)
			return false;

		target.setValue(newNodeValue);
		return true;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		GraphNode target = getNode(node.getValue());
		if (target == null)
			return null;

		return target.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());

		targetFromNode.addNeighbor(targetToNode, weight);

		return true;
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());

		return targetFromNode.removeNeighbor(targetToNode);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());

		if (from == null || to == null) {
			return false;
		}

		from.removeNeighbor(to);
		from.addNeighbor(to, newWeight);

		return true;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());

		if (from == null || to == null) {
			return null;
		}

		return from.getDistanceToNeighbor(to);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		GraphNode target = getNode(node.getValue());

		if (target == null) {
			return null;
		}

		return target.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());

		if (from == null || to == null) {
			return false;
		}

		return from.getDistanceToNeighbor(to) != null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		return fewestHops(fromNode, toNode) != -1;
	}

	@Override
	public Boolean hasCycles() {
		for (GraphNode start : nodeList) {
			if (cycleCheck(start, start, new ArrayList<>())) {
				return true;
			}
		}
		return false;
	}

	private Boolean cycleCheck(GraphNode current, GraphNode target, List<GraphNode> visited) {
		visited.add(current);

		for (GraphNode neighbor : current.getNeighbors()) {
			if (neighbor.equals(target)) {
				return true;
			}

			if (!visited.contains(neighbor)) {
				if (cycleCheck(neighbor, target, visited)) {
					return true;
				}
			}
		}

		return false;
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
		GraphNode start = getNode(fromNode.getValue());
		GraphNode target = getNode(toNode.getValue());

		if (start == null || target == null) {
			return -1;
		}

		List<GraphNode> unvisited = new ArrayList<>(nodeList);
		List<Integer> distances = new ArrayList<>();

		for (GraphNode node : nodeList) {
			if (node.equals(start)) {
				distances.add(0);
			} else {
				distances.add(Integer.MAX_VALUE);
			}
		}

		while (!unvisited.isEmpty()) {
			int minIndex = -1;
			int minDistance = Integer.MAX_VALUE;

			for (int i = 0; i < nodeList.size(); i++) {
				if (unvisited.contains(nodeList.get(i)) && distances.get(i) < minDistance) {
					minDistance = distances.get(i);
					minIndex = i;
				}
			}

			if (minIndex == -1) {
				break;
			}

			GraphNode current = nodeList.get(minIndex);
			unvisited.remove(current);

			for (GraphNode neighbor : current.getNeighbors()) {
				int neighborIndex = nodeList.indexOf(neighbor);
				int newDist = distances.get(minIndex) + current.getDistanceToNeighbor(neighbor);

				if (newDist < distances.get(neighborIndex)) {
					distances.set(neighborIndex, newDist);
				}
			}
		}

		int result = distances.get(nodeList.indexOf(target));
		return result == Integer.MAX_VALUE ? -1 : result;
	}
}