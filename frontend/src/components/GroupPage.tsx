import type {Paper} from "../model/Paper.tsx";
import PaperTable from "./PaperTable.tsx";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";


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
            <PaperTable papers={papers}/>
    )
}