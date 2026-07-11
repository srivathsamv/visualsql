import type { GraphResponse } from "../types/graph";

interface SchemaCanvasProps {
    graph: GraphResponse | null;
}

export default function SchemaCanvas({
                                         graph
                                     }: SchemaCanvasProps) {

    if (!graph) {
        return (
            <p>No schema generated.</p>
        );
    }

    return (

        <div>

            <h2>Graph Response</h2>

            <pre>
                {JSON.stringify(graph, null, 2)}
            </pre>

        </div>

    );
}