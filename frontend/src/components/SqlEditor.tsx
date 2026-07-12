import { useState } from "react";

interface SqlEditorProps {
    onVisualize: (sql: string) => void;
}

export default function SqlEditor({
                                      onVisualize
                                  }: SqlEditorProps) {

    const [sql, setSql] = useState(`CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);`);

    return (
        <div
            style={{
                display: "flex",
                flexDirection: "column",
                height: "100%",
                gap: "16px"
            }}
        >
            <h2 style={{ margin: 0 }}>SQL Editor</h2>

            <textarea
                value={sql}
                onChange={(e) => setSql(e.target.value)}
                style={{
                    flex: 1,
                    width: "100%",
                    resize: "none",
                    fontFamily: "Consolas, monospace",
                    fontSize: "14px",
                    padding: "12px",
                    borderRadius: "8px",
                    border: "1px solid #444",
                    background: "#1f2937",
                    color: "white",
                    boxSizing: "border-box"
                }}
            />

            <button
                onClick={() => onVisualize(sql)}
                style={{
                    padding: "12px",
                    cursor: "pointer"
                }}
            >
                Visualize
            </button>
        </div>
    );
}