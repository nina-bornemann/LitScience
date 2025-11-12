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
                if (map.has(group)){
                    map.set(group, map.get(group)! + 1)
                } else {
                    map.set(group, 1)
                }
            }
        }
        const documentStyle = getComputedStyle(document.documentElement);
        const data = {
            labels: [...map.keys()],
            datasets: [
                {
                    data: [...map.values()],
                    backgroundColor: [
                        documentStyle.getPropertyValue('--blue-500'),
                        documentStyle.getPropertyValue('--yellow-500'),
                        documentStyle.getPropertyValue('--green-500')
                    ],
                    hoverBackgroundColor: [
                        documentStyle.getPropertyValue('--blue-400'),
                        documentStyle.getPropertyValue('--yellow-400'),
                        documentStyle.getPropertyValue('--green-400')
                    ]
                }
            ]
        }
        const options = {
            plugins: {
                legend: {
                    labels: {
                        usePointStyle: true
                    }
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
            <div className={"stats"}>
                <DashboardCard emoji={"📑"} count={papers.length} title={"Total Entries"} onClick={() => nav("/all")}/>
                <DashboardCard emoji={"📂"} count={groups.size} title={"Groups"} onClick={() => nav("/groups/overview")}/>
                <DashboardCard emoji={"❤️"} count={favorites.length} title={"Favorites"} onClick={() => nav("/favorites")}/>
                <div className="card flex justify-content-center">
                    <Chart type="pie" data={chartData} options={chartOptions} className="w-full md:w-30rem" />
                </div>
            </div>
    )
}