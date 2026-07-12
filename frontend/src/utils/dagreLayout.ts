import dagre from "dagre";
import type { Edge, Node } from "@xyflow/react";

const dagreGraph = new dagre.graphlib.Graph();

dagreGraph.setDefaultEdgeLabel(() => ({}));

const NODE_WIDTH = 280;
const NODE_HEIGHT = 180;

export function getLayoutedElements(
    nodes: Node[],
    edges: Edge[]
) {

    dagreGraph.setGraph({
        rankdir: "LR",
        nodesep: 150,
        ranksep: 250
    });

    nodes.forEach((node) => {

        dagreGraph.setNode(node.id, {
            width: NODE_WIDTH,
            height: NODE_HEIGHT
        });

    });

    edges.forEach((edge) => {

        dagreGraph.setEdge(edge.source, edge.target);

    });

    dagre.layout(dagreGraph);

    const layoutedNodes = nodes.map((node) => {

        const position = dagreGraph.node(node.id);

        return {

            ...node,

            position: {
                x: position.x - NODE_WIDTH / 2,
                y: position.y - NODE_HEIGHT / 2
            }

        };

    });

    return {

        nodes: layoutedNodes,
        edges

    };

}