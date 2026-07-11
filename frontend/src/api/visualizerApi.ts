import axios from "axios";
import type { GraphResponse } from "../types/graph";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1"
});

export async function visualize(sql: string): Promise<GraphResponse> {

    const response = await api.post<GraphResponse>(
        "/visualize",
            {
                sql
            }
    );

    return response.data;
}