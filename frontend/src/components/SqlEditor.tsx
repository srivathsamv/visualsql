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
        <div>

            <h2>SQL Editor</h2>

            <textarea
                rows={20}
                cols={100}
                value={sql}
                onChange={(e) => setSql(e.target.value)}
            />

            <br />

            <button
                onClick={() => onVisualize(sql)}
            >
                Visualize
            </button>

        </div>
    );
}