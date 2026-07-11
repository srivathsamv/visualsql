import { useMemo } from "react";
import {
    Background,
    Controls,
    ReactFlow,
    type Edge,
    type Node
} from "@xyflow/react";
import "@xyflow/react/dist/style.css";
import TableNode from "./TableNode.tsx";

import type { GraphResponse } from "../types/graph";

interface SchemaCanvasProps {
    graph: GraphResponse | null;
}

export default function SchemaCanvas({
                                         graph
                                     }: SchemaCanvasProps) {

    const nodeTypes = {
        tableNode: TableNode
    };

    const nodes: Node[] = useMemo(() => {

        if (!graph) return [];

        return graph.nodes.map((table, index) => ({

            id: table.id,

            type: "tableNode",

            position: {
                x: index * 450,
                y: 150
            },

            data: {
                table
            }

        }));

    }, [graph]);

    const edges: Edge[] = useMemo(() => {

        if (!graph) return [];

        return graph.edges.map(edge => ({

            id: edge.id,

            source: edge.source,

            target: edge.target,

            label: edge.label,

            animated: true

        }));

    }, [graph]);

    return (
        <div
            style={{
                width: "100%",
                height: "700px",
                border: "1px solid #444",
                marginTop: "20px"
            }}
        >
            <ReactFlow
                nodes={nodes}
                edges={edges}
                nodeTypes={nodeTypes}
                fitView
            >
                <Background />
                <Controls />
            </ReactFlow>
        </div>
    );
}