import axios from "axios";
import type { GraphResponse } from "../types/graph";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1"
});

export async function visualize(sql: string): Promise<GraphResponse> {

    try {

        const response = await api.post<GraphResponse>(
            "/visualize",
            {
                sql
            }
        );

        return response.data;

    } catch (error) {

        if (axios.isAxiosError(error)) {

            throw new Error(
                error.response?.data?.message ??
                "Failed to visualize SQL."
            );

        }

        throw new Error("Unexpected error occurred.");
    }
}