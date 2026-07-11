import { useState } from "react";
import SqlEditor from "../components/SqlEditor";
import SchemaCanvas from "../components/SchemaCanvas";
import { visualize } from "../api/visualizerApi";
import type { GraphResponse } from "../types/graph";

export default function Home() {

    const [graph, setGraph] =
        useState<GraphResponse | null>(null);

    const handleVisualize = async (
        sql: string
    ) => {

        try {

            const response = await visualize(sql);

            setGraph(response);

        } catch (error) {

            console.error(error);

            alert("Failed to visualize SQL.");

        }

    };

    return (

        <div>

            <h1>VisualSQL</h1>

            <SqlEditor
                onVisualize={handleVisualize}
            />

            <SchemaCanvas
                graph={graph}
            />

        </div>

    );
}