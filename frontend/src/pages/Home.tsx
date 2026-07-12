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
        <div
            style={{
                height: "100vh",
                padding: "20px",
                boxSizing: "border-box",
                background: "#121212",
                display: "flex",
                flexDirection: "column"
            }}
        >
            {/* Header */}
            <div
                style={{
                    textAlign: "center",
                    marginBottom: "24px"
                }}
            >
                <h1
                    style={{
                        margin: 0,
                        fontSize: "4rem",
                        color: "white"
                    }}
                >
                    VisualSQL
                </h1>

                <p
                    style={{
                        marginTop: "25px",
                        color: "#9ca3af",
                        fontSize: "1.1rem"
                    }}
                >
                    Visualize SQL schemas instantly
                </p>
            </div>

            {/* Main Content */}
            <div
                style={{
                    display: "flex",
                    flex: 1,
                    gap: "15px"
                }}
            >
                <div
                    style={{
                        flex: 3,
                        display: "flex",
                        flexDirection: "column"
                    }}
                >
                    <SqlEditor
                        onVisualize={handleVisualize}
                    />
                </div>

                <div
                    style={{
                        flex: 7
                    }}
                >
                    <SchemaCanvas graph={graph} />
                </div>
            </div>
        </div>
    );
}