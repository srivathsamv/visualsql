interface Props {
    onVisualize: () => void;
    onClear: () => void;
    onOpenFile: () => void;
}

export default function SqlToolbar({
                                       onVisualize,
                                       onClear,
                                       onOpenFile,
                                   }: Props) {
    return (
        <div
            style={{
                display: "flex",
                gap: "8px",
                marginBottom: "10px",
            }}
        >
            <button onClick={onOpenFile}
                    style={{
                        display: "flex",
                        marginLeft: "auto",
                        marginRight: "auto",
                        background: "black",
                        color: "white",
                        fontSize: "100%",
                        textAlign: "center",
                        borderRadius: "10px"
                    }}>
                Open SQL
            </button>

            <button onClick={onClear}
                    style={{
                        display: "flex",
                        marginLeft: "auto",
                        marginRight: "auto",
                        background: "black",
                        color: "white",
                        borderRadius: "10px"
                    }}>
                Clear
            </button>

            <button
                onClick={onVisualize}
                style={{
                    display: "flex",
                    marginLeft: "auto",
                    marginRight: "auto",
                    background: "green",
                    color: "white",
                    borderRadius: "10px"
                }}
            >
                ▶ Visualize
            </button>
        </div>
    );
}