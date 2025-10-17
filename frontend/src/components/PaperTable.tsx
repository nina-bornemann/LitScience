import {useState} from "react";
import type {Paper} from "./Paper.tsx";
import axios from "axios";

export type PaperTableProps = {
    papers:Paper[]
}

export default function PaperTable(props:Readonly<PaperTableProps>) {

    const [error, setError] = useState<string>("")
    const [papers, setPapers] = useState<Paper[]>(props.papers)

    function getAllPapers() {
        axios
            .get("api/paper")
            .then((response) => setPapers(response.data))
            .catch(() => setError("No papers in your collection"))
    }

    return (
        <>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>DOI</th>
                        <th>Year</th>
                        <th>Group</th>
                        <th>Notes</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                        {papers.map((paper) => {
                            return <tr>
                                <td>{paper.title}</td>
                                <td>{paper.author}</td>
                                <td>{paper.doi}</td>
                                <td>{paper.year}</td>
                                <td>{paper.group}</td>
                                <td>{paper.notes}</td>
                                <td></td>
                            </tr>
                             })}
                    </tbody>
                </table>
            </div>
        </>
    )
}