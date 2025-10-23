import type {Paper} from "../model/Paper.tsx";
import { DataTable, type DataTableRowClickEvent } from 'primereact/datatable';
import { Column } from 'primereact/column';
import "primereact/resources/themes/bootstrap4-dark-blue/theme.css";
import "./PaperTable.css"
import {useNavigate} from "react-router-dom";

export type PaperTableProps = {
    papers:Paper[]
}

export default function PaperTable(props:Readonly<PaperTableProps>) {

    const footer = "There are " + props.papers.length + " papers in total. ";
    const nav = useNavigate()

    function navToDetails(event:DataTableRowClickEvent) {
        const paperId = event.data.id
        nav(`/paper/${paperId}`)
    }

    return (
        <>
            <DataTable onRowClick={navToDetails} rowClassName={() => "clickable"} value={props.papers} footer={footer} dataKey="id" removableSort scrollable scrollHeight="500px" resizableColumns showGridlines={true} tableStyle={{ minWidth: '40rem'}}>
                <Column field="title" header="Title" sortable style={{ width: '20%' }} className={"truncate-2"}></Column>
                <Column field="author" header="Author" sortable style={{ width: '15%' }} className={"truncate-2"}></Column>
                <Column field="doi" header="DOI" sortable style={{ width: '15%' }} className={"truncate-3"}></Column>
                <Column field="year" header="Year" sortable style={{ width: '10%' }}></Column>
                <Column field="group" header="Group" sortable style={{ width: '20%' }} className={"truncate-2"}></Column>
                <Column field="notes" header="Notes" sortable style={{ width: '20%' }} className={"truncate-2"}></Column>
            </DataTable>
        </>
    )
}