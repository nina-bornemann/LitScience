import type {Paper} from "../model/Paper.tsx";
import PaperTable from "./PaperTable.tsx";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import "./GroupPage.css"

export default function GroupPage(){

    const {groupName} = useParams<{groupName:string}>()
    const [papers, setPapers] = useState<Paper[]>([])

    useEffect(() => {
        if (groupName) {
        axios.get(`/api/paper?group=${groupName}`)
            .then((response) => {
                setPapers(response.data)
            })
            .catch((error) => console.log(error))}
    }, [groupName])

    return (
        <>
            <div className={"group-header"}>
                <h2 className={"group-name"}>Group: </h2> <h2>{groupName}</h2>
            </div>
            <PaperTable papers={papers}/>
        </>

    )
}