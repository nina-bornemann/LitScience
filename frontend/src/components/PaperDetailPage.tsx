import "./PaperDetailPage.css"
import {useEffect, useState} from "react";
import type {Paper} from "./Paper.tsx";
import {useParams} from "react-router-dom";
import axios from "axios";

export default function PaperDetailPage() {

    const {id} = useParams<{id:string}>()
    const [paper, setPaper] = useState<Paper | undefined>(undefined)

    useEffect(() => {
        if (id) {
            axios.get(`/api/paper/${id}`)
                .then((response) => setPaper(response.data))
                .catch((e) => console.log("Failed to load paper: " + e))
        }
    }, [id])

    if (!paper) {
        return <p>Paper was not found</p>;
    }

    return (
        <>
            <div className={"detail-wrapper"}>
                <div className={"detail-buttons"}>
                    <button> ← Back </button>
                    <div>
                        <button className={"detail-action-button"}>Get AI report</button>
                        <button className={"detail-action-button"}> ✏️ </button>
                        <button className={"detail-action-button"}> 🗑 </button>
                    </div>
                </div>
                <h1 className={"title"}>Title:</h1>
                <h1>{paper.title}</h1>
                <h2> <b>Author: </b>{paper.author}</h2>
                <p><b>DOI: </b> {paper.doi}</p>
                <p><b>Publication year: </b>{paper.year}</p>
                <p><b>Group Tags: </b> {paper.group}</p>
                <p><b>Notes: </b>{paper.notes}</p>
                <p><b>PDF available: </b></p>
                <p><b>Report: </b></p>
            </div>
        </>
    )
}