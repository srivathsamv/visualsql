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
                background: "#F5E8D8",
                color: "#1C1C1C",
                border: "1px solid #4B5563",
                borderRadius: 8,
                overflow: "hidden",
                minWidth: 300,
                fontFamily: "monospace"
            }}
        >
            {/* Table Header */}
            <div
                style={{
                    background: "#1C1C1C",
                    color: "#F5E8D8",
                    padding: "12px",
                    textAlign: "center",
                    fontWeight: "bold",
                    fontSize: "16px"
                }}
            >
                {table.tableName}
            </div>

            {/* Table Columns */}
            {table.columns.map((column) => {

                // CHANGED 1: one handle ID per column, same format as the backend ("users-email")
                const handleId = `${table.id}-${column.name.replace(/["`]/g, "").toLowerCase()}`;

                return (
                    <div
                        key={column.name}
                        style={{
                            position: "relative",
                            display: "grid",
                            gridTemplateColumns: "50px 1fr 150px",
                            alignItems: "center",
                            textAlign: "center",
                            padding: "10px 12px",
                            borderTop: "1px solid #4B5563",
                            fontSize: "14px"
                        }}
                    >
                        {/* CHANGED 2: incoming handle on any column a FK points at (PK or UNIQUE) */}
                        {column.referenced && (
                            <Handle
                                type="target"
                                position={Position.Left}
                                id={handleId}
                                style={{
                                    width: 8,
                                    height: 8
                                }}
                            />
                        )}

                        {/* PK / FK */}
                        <span
                            style={{
                                fontWeight: "bold",
                                color: "#2563EB",
                                textAlign: "center"
                            }}
                        >
                            {column.primaryKey
                                ? "PK"
                                : column.foreignKey
                                    ? "FK"
                                    : ""}
                        </span>

                        {/* Column Name */}
                        <span
                            style={{
                                textAlign: "center",
                                paddingLeft: "8px"
                            }}
                        >
                            {column.name}
                        </span>

                        {/* Data Type */}
                        <span
                            style={{
                                textAlign: "center",
                                color: "#374151"
                            }}
                        >
                            {column.type}
                        </span>

                        {/* Outgoing Handle (FK) */}
                        {column.foreignKey && (
                            <Handle
                                type="source"
                                position={Position.Right}
                                id={handleId}  // CHANGED 3: same normalized ID
                                style={{
                                    width: 8,
                                    height: 8
                                }}
                            />
                        )}
                    </div>
                );
            })}
        </div>
    );
}