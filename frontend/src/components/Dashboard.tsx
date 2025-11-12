import "./Dashboard.css"
import {useEffect, useState} from "react";
import axios from "axios";
import type {Paper} from "../model/Paper.tsx";
import DashboardCard from "./DashboardCard.tsx";
import {useNavigate} from "react-router-dom";
import { Chart } from 'primereact/chart';

export default function Dashboard() {

    const [papers, setPapers] = useState<Paper[]>([])
    const nav = useNavigate();
    const favorites = papers.filter((paper) => paper.isFav)
    const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});

    const groups = new Set(papers.flatMap((paper) => paper.group))


    function calculatePieChartData(papers: Paper[]) {
        const map = new Map<string, number>();
        for (const paper of papers) {
            for (const group of paper.group) {
                if (map.has(group)) {
                    map.set(group, map.get(group)! + 1)
                } else {
                    map.set(group, 1)
                }
            }
        }
        const sortedEntries = [...map.entries()].sort((a, b) => b[1] - a[1]);

        const data = {
            labels: sortedEntries.map(([group]) => group),
            datasets: [
                {
                    data: sortedEntries.map(([_, count]) => count),
                    backgroundColor: ["#0B1E34", "#12344D", "#1D5C8A", "#2E7CBF", "#49A1DA",
                        "#3CB7C9", "#30C1AF", "#36B37E", "#2F9E67", "#258754"],
                    hoverBackgroundColor: ["#12345A", "#1B4F66", "#2773A5", "#4191D1", "#63B3E5",
                        "#55CBDD", "#4DD5C4", "#4FC18F", "#47B37D", "#3A9E6A"],
                    borderColor: "#0B1E34",
                    borderWidth: 0.5,
                }
            ]
        }
        const options = {
            plugins: {
                legend: {
                    position: "right",
                    labels: {
                        usePointStyle: true,
                        color: "white",
                        font: {
                            size: 16,
                            weight: "normal"
                        },
                        padding: 15,
                        boxWidth: 12,
                        boxHeight: 12
                    }
                },
                title: {
                    display: true,
                    text: "Paper Groups Overview",
                    align: "start",
                    color: "white",
                    font: {
                        size: 18,
                        weight: "600"
                    },
                    padding: {
                        top: 10,
                        bottom: 20,
                    },
                }
                }
        };
        setChartData(data)
        setChartOptions(options)
    }


    function getAllPapers() {
        axios
            .get("/api/paper")
            .then((response) => {

                calculatePieChartData(response.data)
                setPapers(response.data)
            })
            .catch((e) => console.log(e))
    }

    useEffect(() => {
        getAllPapers()
    }, [])

    return (
        <div className={"allStats"}>
            <div className={"stats"}>
                <DashboardCard emoji={"📑"} count={papers.length} title={"Total Entries"} onClick={() => nav("/all")}/>
                <DashboardCard emoji={"📂"} count={groups.size} title={"Groups"} onClick={() => nav("/groups/overview")}/>
                <DashboardCard emoji={"❤️"} count={favorites.length} title={"Favorites"} onClick={() => nav("/favorites")}/>
            </div>
            <div className={"chart-card"}>
                <Chart type="pie" data={chartData} options={chartOptions} className="w-full md:w-30rem" />
            </div>
        </div>
    )
}