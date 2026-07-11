import type { TableNode as TableNodeDto } from "../types/graph";
import { Handle, Position } from "@xyflow/react";

interface Props {
    data: {
        table: TableNodeDto;
    };
}

export default function TableNode({ data }: Props) {

    const table = data.table;

    return (

        <div
            style={{
                background: "#1f2937",
                color: "white",
                border: "1px solid #4b5563",
                borderRadius: 8,
                minWidth: 250,
                overflow: "hidden",
                fontFamily: "monospace"
            }}
        >

            <div
                style={{
                    background: "#374151",
                    padding: "10px",
                    fontWeight: "bold",
                    textAlign: "center"
                }}
            >
                {table.tableName}
            </div>

            {table.columns.map((column) => (

                <div
                    key={column.name}
                    style={{
                        position: "relative",
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        padding: "8px 12px",
                        borderTop: "1px solid #374151",
                        fontSize: 13
                    }}
                >

                    {column.primaryKey && (
                        <Handle
                            type="target"
                            position={Position.Left}
                            id={`${table.id}-${column.name}`}
                            style={{
                                width: 8,
                                height: 8
                            }}
                        />
                    )}

                    <span>

            {column.primaryKey && "🔑 "}

                        {column.foreignKey && "🔗 "}

                        {column.name}

        </span>

                    <span
                        style={{
                            color: "#9ca3af"
                        }}
                    >
            {column.type}
        </span>

                    {column.foreignKey && (
                        <Handle
                            type="source"
                            position={Position.Right}
                            id={`${table.id}-${column.name}`}
                            style={{
                                width: 8,
                                height: 8
                            }}
                        />
                    )}

                </div>

            ))}

        </div>

    );

}