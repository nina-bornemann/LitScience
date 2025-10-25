import "./Dashboard.css"
import {useEffect, useState} from "react";
import axios from "axios";
import type {Paper} from "../model/Paper.tsx";
import DashboardCard from "./DashboardCard.tsx";

export default function Dashboard() {

    const [papers, setPapers] = useState<Paper[]>([])

    let favorites = papers.filter((paper) => paper.isFav)

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
                <DashboardCard emoji={"📑"} count={papers.length} title={"Total Entries"}/>
                <DashboardCard emoji={"📂"} count={0} title={"Groups"}/>
                <DashboardCard emoji={"❤️"} count={favorites.length} title={"Favorites"}/>
            </div>
    )
}