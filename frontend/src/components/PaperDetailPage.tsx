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
            <div>
                <h1>Title: {paper.title}</h1>
                <p><h2>Author: </h2> {paper.author}</p>
                <p>DOI: {paper.doi}</p>
                <p>Publication year: {paper.year}</p>
                <p>Group Tags: {paper.group}</p>
                <p>Notes: {paper.notes}</p>
                <p>PDF available: </p>
                <p>Report: </p>
                <div>
                    <button>Get AI report</button>
                    <button> ✏️ </button>
                    <button> 🗑 </button>
                </div>
            </div>
        </>
    )
}