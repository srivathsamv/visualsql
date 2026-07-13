import { useState } from "react";
import Editor from "@monaco-editor/react";
import SqlToolbar from "./SqlToolbar";
import { useRef } from "react";

interface SqlEditorProps {
    onVisualize: (sql: string) => void;
}

export default function SqlEditor({
                                      onVisualize,
                                  }: SqlEditorProps) {

    const [sql, setSql] = useState(`
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);`);

    const fileInputRef = useRef<HTMLInputElement>(null);

    const handleOpenFile = (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {

        const file = event.target.files?.[0];

        if (!file) return;

        const reader = new FileReader();

        reader.onload = (e) => {

            const contents = e.target?.result as string;

            setSql(contents);

        };

        reader.readAsText(file);

    };

    return (
        <div
            style={{
                display: "flex",
                flexDirection: "column",
                flex: 1,
                gap: "5px",
            }}
        >
            <h2
                style={{
                    margin: 0,
                    color: "#F5E8D8",
                    textAlign: "center",
                }}
            >
                SQL Editor
            </h2>

            <SqlToolbar
                onVisualize={() => onVisualize(sql)}
                onClear={() => setSql("/*Type SQL statements or Open a file to get started*/")}
                onOpenFile={() => fileInputRef.current?.click()}
            />

            <div
                style={{
                    flex: 1,
                    borderRadius: "10px",
                    overflow: "hidden",
                    border: "1px solid #374151",
                }}
            >
                <Editor
                    height="100%"
                    defaultLanguage="sql"
                    theme="vs-dark"
                    value={sql}
                    onChange={(value) => setSql(value ?? "")}
                    options={{
                        minimap: {
                            enabled: false,
                        },
                        fontSize: 12,
                        fontFamily: "JetBrains Mono, Consolas, monospace",
                        wordWrap: "on",
                        automaticLayout: true,
                        scrollBeyondLastLine: false,
                        roundedSelection: true,
                        padding: {
                            top: 12,
                        },
                        tabSize: 2,
                        insertSpaces: true,
                    }}
                />

                <input
                    ref={fileInputRef}
                    type="file"
                    accept=".sql"
                    style={{ display: "none" }}
                    onChange={handleOpenFile}
                />
            </div>
        </div>
    );
}