import "./PaperDetailPage.css"
import {useEffect, useState} from "react";
import type {Paper} from "./Paper.tsx";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import { Toast } from 'primereact/toast';
import { useRef } from 'react';

export default function PaperDetailPage() {

    const {id} = useParams<{id:string}>()
    const [paper, setPaper] = useState<Paper | undefined>(undefined)
    const toast = useRef<Toast>(null);
    const nav = useNavigate();

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

    function navigateToAll() {
        nav("/all")
    }

    function handleDelete() {
        axios.delete(`/api/paper/${paper?.id}`)
            .then(() => {
                toast.current?.show({
                    severity: 'success',
                    summary: 'Deleted',
                    detail: 'The paper was successfully deleted.',
                    life: 3000,
                });
                setTimeout(() => navigateToAll(), 1000);
            })
            .catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Could not delete the paper.',
                    life: 3000,
                });
                console.error(error);
            })
    }

    return (
        <>
            <Toast ref={toast} />
            <div className={"detail-wrapper"}>
                <div className={"detail-buttons"}>
                    <button onClick={navigateToAll}> ← Back </button>
                    <div>
                        <button className={"detail-action-button"}>Get AI report</button>
                        <button className={"detail-action-button"}> ✏️ </button>
                        <button className={"detail-action-button"} onClick={handleDelete}> 🗑 </button>
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