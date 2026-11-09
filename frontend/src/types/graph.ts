export interface ColumnDto {
    name: string;
    type: string;
    primaryKey: boolean;
    foreignKey: boolean;
}

export interface TableNode {
    id: string;
    tableName: string;
    columns: ColumnDto[];
}

export interface RelationshipEdge {
    id: string;
    source: string;
    sourceHandle: string;
    target: string;
    targetHandle: string;
    label: string;
}

export interface GraphResponse {
    nodes: TableNode[];
    edges: RelationshipEdge[];
}