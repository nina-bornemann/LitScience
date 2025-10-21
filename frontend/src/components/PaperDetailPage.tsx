import type {Paper} from "./Paper.tsx";

export type PaperDetailPageProps = {
    paper:Paper
}

export default function PaperDetailPage(props:Readonly<PaperDetailPageProps>) {

    return (
        <>
            <div>
                <h1>Title: {props.paper.title}</h1>
                <p><h2>Author: </h2> {props.paper.author}</p>
                <p>DOI: {props.paper.doi}</p>
                <p>Publication year: {props.paper.year}</p>
                <p>Group Tags: {props.paper.group}</p>
                <p>Notes: {props.paper.notes}</p>
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