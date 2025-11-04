import {useEffect, useState} from "react";
import axios from "axios";
import type {Paper} from "../model/Paper.tsx";
import "./GroupOverview.css"

interface GroupInfo {
    groupName:string;
    quantity:number;
}

export default function GroupOverview() {

    const [allGroups, setAllGroups] = useState<string[]>([])
    const [groupInfos, setGroupInfos] = useState<GroupInfo[]>([])

    function getAllGroups() {
        axios
            .get("/api/paper/groups")
            .then((response) => {
                console.log(response.data)
                setAllGroups(response.data)
            })
            .catch((error) => console.log(error))
    }

    useEffect(() =>{
        getAllGroups()
    }, [])

    useEffect(() => {
        const gis:GroupInfo[] = [];
        allGroups.map((groupName) => {
            let papers: Paper[] = [];
            axios.get(`/api/paper?group=${groupName}`)
                .then((response) => {
                    papers = response.data
                    console.log(papers)
                    gis.push( {groupName: groupName, quantity: papers.length})
                })
                .catch((error) => console.log(error))
        });
        setGroupInfos(gis);
    }, [allGroups])

    return (
        <>
            <div className={"overview-container"}>
                <h2>You have {allGroups.length} groups in your collection!</h2>

                <div className={"group-cards"}>
                    {groupInfos.map((group) => {
                         return <div className={"group-card"}>
                                    <p> <span className={"group-name"}>{group.groupName} :</span> {group.quantity}</p>
                                    <button> 🔍</button>
                                </div>
                    })}
                </div>
            </div>
        </>
    )
}