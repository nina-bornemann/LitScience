import {useEffect, useState} from "react";
import axios from "axios";
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
                console.log("getting groups", response.data)
                setAllGroups(response.data)
            })
            .catch((error) => console.log(error))
    }

    useEffect(() =>{
        getAllGroups()
    }, [])

    useEffect(() => {
        if (allGroups.length === 0) return;

        const fetchGroupInfos = async () => {
            try {
                const infos = await Promise.all(
                    allGroups.map(async (groupName) => {
                        const response = await axios.get(`/api/paper?group=${groupName}`);
                        return { groupName, quantity: response.data.length };
                    })
                );
                setGroupInfos(infos);
            } catch (error) {
                console.log(error);
            }
        };

        fetchGroupInfos();
    }, [allGroups]);

    return (
        <>
            <div className={"overview-container"}>
                <h2>You have {allGroups.length} groups in your collection!</h2>

                <div className={"group-cards"}>
                    {groupInfos.map((group, index) => {
                         return <div key={`${group.groupName}-${index}`} className={"group-card"}>
                                    <p> <span className={"group-name"}>{group.groupName} :</span> {group.quantity}</p>
                                    <button className={"group-card-btn"}> 🔍</button>
                                </div>
                    })}
                </div>
            </div>
        </>
    )
}