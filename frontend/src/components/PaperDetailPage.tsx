import "./PaperDetailPage.css"
import {useEffect, useState, useRef} from "react";
import type {Paper, PaperDto} from "../model/Paper.tsx";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import { Toast } from 'primereact/toast';
import MDEditor from "@uiw/react-md-editor";
import rehypeSanitize from "rehype-sanitize";
import GroupSelect from "./GroupSelect.tsx";

type PaperDetailPageProps = {
    onDelete: (id?:string) => void;
    onUpdate: () => void;
}

export default function PaperDetailPage(props:Readonly<PaperDetailPageProps>) {

    const {id} = useParams<{id:string}>()
    const [paper, setPaper] = useState<Paper | undefined>(undefined)
    const [notes, setNotes] = useState<string>("");
    const toast = useRef<Toast>(null);
    const nav = useNavigate();
    const [isFav, setIsFav] = useState<boolean>()

    useEffect(() => {
        if (id) {
            axios.get(`/api/paper/${id}`)
                .then((response) => {
                    setPaper(response.data)
                    setNotes(response.data.notes)
                    setIsFav(response.data.isFav)
                })
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
                    life: 5000,
                });
                props.onDelete(paper?.id);
                setTimeout(() => navigateToAll(), 1000);
            })
            .catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Could not delete the paper.',
                    life: 5000,
                });
                console.error(error);
            })
    }

    function handleChange(){
        const dto:PaperDto = {
            doi: undefined,
            title: undefined,
            author: undefined,
            year: undefined,
            group: undefined,
            notes: notes
        };
        axios.put(`/api/paper/${paper?.id}`, dto)
            .then(()=> {
                toast.current?.show({
                    severity: 'success',
                    summary: 'Updated',
                    detail: 'The notes were successfully updated.',
                    life: 5000,
                });
                props.onUpdate();
            })
            .catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Could not edit notes.',
                    life: 5000,
                });
                console.error(error);
            })
    }

    function toggleFavorite() {
        axios.put(`/api/paper/${paper?.id}/favorite`)
            .then((response) => {
                setIsFav(response.data.isFav)
                props.onUpdate()
            })
            .catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Could not add to favorites.',
                    life: 5000,
                });
                console.error(error);
            })
    }

    function handleGroupChange(tags:string[]) {
        axios.put(`/api/paper/${paper?.id}/group`, JSON.stringify(tags), {
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(()=> {
                props.onUpdate()
            })
            .catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Could not update Group Tags.',
                    life: 5000,
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
                        <button className={"detail-action-button"} onClick={toggleFavorite}>{isFav && "❤️"}️{!isFav && "🩶"}</button>
                        <button className={"detail-action-button"} onClick={handleDelete}> 🗑 </button>
                    </div>
                </div>

                <div className="card">
                    <h2 className={"title"}>Title:</h2>
                    <h2>{paper.title}</h2>
                    <h2> <b>Author: </b>{paper.author}</h2>
                    <p><b>DOI: </b> {paper.doi}</p>
                    <p><b>Publication year: </b>{paper.year}</p>
                    <p className={"group-title"}><b>Group Tags: </b> </p>

                    <div className={"group"}>
                        <GroupSelect onGroupUpdate={handleGroupChange} paper={paper} />
                    </div>

                    <p><b>Notes: </b></p>
                    <div className="md-container">
                        <MDEditor
                            value={notes}
                            onChange={(value) => setNotes(value || "")}
                            previewOptions={{
                                rehypePlugins: [[rehypeSanitize]],
                            }}
                        />
                </div>
                <button onClick={handleChange} className={"saveButton"}>Save Notes</button>
                <p><b>PDF available: </b></p>
                <p><b>Report: </b></p>
                </div>
            </div>
        </>
    )
}