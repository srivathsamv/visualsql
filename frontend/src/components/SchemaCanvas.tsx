import { useMemo } from "react";
import {
    Background,
    Controls,
    ReactFlow,
    type Edge,
    type Node, MarkerType
} from "@xyflow/react";
import "@xyflow/react/dist/style.css";
import TableNode from "./TableNode.tsx";
import {getLayoutedElements} from "../utils/dagreLayout.ts";

import type { GraphResponse } from "../types/graph";

interface SchemaCanvasProps {
    graph: GraphResponse | null;
}

export default function SchemaCanvas({
                                         graph
                                     }: SchemaCanvasProps) {

    const colors = [
        "#3B82F6", // blue
        "#10B981", // green
        "#F59E0B", // amber
        "#EF4444", // red
        "#8B5CF6", // purple
        "#EC4899", // pink
        "#06B6D4", // cyan
        "#84CC16", // lime
    ];

    const nodeTypes = {
        tableNode: TableNode
    };

    const nodes: Node[] = useMemo(() => {

        if (!graph) return [];

        return graph.nodes.map((table) => ({

            id: table.id,

            type: "tableNode",

            position: {
                x: 0,
                y: 0
            },

            data: {
                table
            }

        }));

    }, [graph]);

    const edges: Edge[] = useMemo(() => {

        if (!graph) return [];

        return graph.edges.map((edge, index) => ({

            id: edge.id,

            source: edge.source,

            sourceHandle: edge.sourceHandle,

            target: edge.target,

            targetHandle: edge.targetHandle,

            animated: true,

            type: "simplebezier",

            style: {
                stroke: colors[index % colors.length],
                strokeWidth: 2,
            },

            markerEnd: {
                type: MarkerType.ArrowClosed,
                color: colors[index % colors.length]
            }

        }));

    }, [graph]);

    const { nodes: layoutedNodes, edges: layoutedEdges } = useMemo(() => {
        return getLayoutedElements(nodes, edges);
    }, [nodes, edges]);

    return (
        <div
            style={{
                width: "100%",
                height: "86.5%",
                backgroundColor: "#1A1A1A",
                border: "1px solid #444",
                borderRadius: "8px",
                borderColor: "white",
                marginTop: "45px"
            }}
        >
            <ReactFlow
                nodes={layoutedNodes}
                edges={layoutedEdges}
                nodeTypes={nodeTypes}
                fitView
            >
                <Background />
                <Controls />
            </ReactFlow>
        </div>
    );
}