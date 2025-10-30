import "./Dashboard.css"
import {useEffect, useState} from "react";
import axios from "axios";
import type {Paper} from "../model/Paper.tsx";
import DashboardCard from "./DashboardCard.tsx";
import {useNavigate} from "react-router-dom";

export default function Dashboard() {

    const [papers, setPapers] = useState<Paper[]>([])
    const nav = useNavigate();
    const favorites = papers.filter((paper) => paper.isFav)

    const groups = new Set(papers.flatMap((paper) => paper.group))

    function getAllPapers() {
        axios
            .get("/api/paper")
            .then((response) => setPapers(response.data))
            .catch((e) => console.log(e))
    }

    useEffect(() => {
        getAllPapers()
    }, [])

    return (
            <div className={"stats"}>
                <DashboardCard emoji={"📑"} count={papers.length} title={"Total Entries"} onClick={() => nav("/all")}/>
                <DashboardCard emoji={"📂"} count={groups.size} title={"Groups"} onClick={() => nav("/groups/overview")}/>
                <DashboardCard emoji={"❤️"} count={favorites.length} title={"Favorites"} onClick={() => nav("/favorites")}/>
            </div>
    )
}