import "./Dashboard.css"
import {useEffect, useState} from "react";
import axios from "axios";
import type {Paper} from "../model/Paper.tsx";

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
                <div className={"stats-card"}>
                    <h1> 📑 </h1>
                    <div className={"stats-text"}>
                        <h2> {papers.length}</h2>
                        <h3>Total Entries</h3>
                    </div>
                </div>

                <div className={"stats-card"}>
                    <h1> 📂 </h1>
                    <div className={"stats-text"}>
                        <h2>(number)</h2>
                        <h3>Groups</h3>
                    </div>
                </div>

                <div className={"stats-card"}>
                    <h1> ❤️ </h1>
                    <div className={"stats-text"}>
                        <h2>{favorites.length}</h2>
                        <h3>Favorites</h3>
                    </div>
                </div>
            </div>
    )
}