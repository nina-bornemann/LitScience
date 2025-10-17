import {useState} from "react";
import type {Paper} from "./Paper.tsx";
import axios from "axios";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import "primereact/resources/themes/bootstrap4-dark-blue/theme.css";


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

    const footer = "There are " + papers.length + " papers in total. "

    return (
        <>
            <DataTable value={papers} footer={footer} dataKey="id" stripedRows scrollable scrollHeight="400px" tableStyle={{ minWidth: '50rem' }}>
                <Column field="title" header="Title" sortable style={{ width: '25%' }}></Column>
                <Column field="author" header="Author" sortable style={{ width: '25%' }}></Column>
                <Column field="doi" header="DOI"></Column>
                <Column field="year" header="Year" sortable style={{ width: '25%' }}></Column>
                <Column field="group" header="Group" sortable style={{ width: '25%' }}></Column>
                <Column field="notes" header="Notes"></Column>
                <Column field="action" header="Actions"></Column>
            </DataTable>
        </>
    )
}