import './AddNewPaper.css'
import {useState} from "react";
import axios from "axios";
import type {Paper} from "../model/Paper.tsx";

export type AddNewPaperProps = {
    onAdd : (paper:Paper) => void;
}

export default function AddNewPaper(props:Readonly<AddNewPaperProps>) {

    const [doi, setDoi] = useState<string>("");

    function handleDoi() {
        axios
            .get(`api/paper/import/${doi}`)
            .then((response) => {
                props.onAdd(response.data)
                setDoi("")
            })
            .catch(() => alert("Please enter a valid DOI... 👀"))

    }

    return (
        <>
            <div className={"addDoi"}>
                <input className={"addDoiInput"} onChange={(r) => setDoi(r.target.value)} value={doi} placeholder={"Enter DOI"} />
                <button className={"addButton"} onClick={handleDoi}>Add paper by DOI</button>
            </div>
        </>
    )
}