import './App.css'
import NavBar from "./components/NavBar.tsx";
import Footer from "./components/Footer.tsx";
import PaperTable from "./components/PaperTable.tsx";
import {useEffect, useState} from "react";
import type {Paper} from "./model/Paper.tsx";
import axios from "axios";
import AddNewPaper from "./components/AddNewPaper.tsx";
import {Route, Routes} from "react-router-dom";
import PaperDetailPage from "./components/PaperDetailPage.tsx";
import Home from "./components/Home.tsx";
import Dashboard from "./components/Dashboard.tsx";
import Sidebar from "./components/Sidebar.tsx";
import GroupPage from "./components/GroupPage.tsx";
import GroupOverview from "./components/GroupOverview.tsx";
import Login from "./components/Login.tsx";

export default function App() {

    const [papers, setPapers] = useState<Paper[]>([])

    function getAllPapers() {
        axios
            .get("/api/paper")
            .then((response) => {
                setPapers(response.data)
            })
            .catch((e) => console.log("error", e))
    }

    function getFavoritePapers() {
        console.log("papers:", papers)
        return papers?.filter((paper) => paper.isFav)
    }

    useEffect(() => {
        getAllPapers()
    }, [])

    return (
        <div>
            <NavBar />
            <Sidebar />
                <div className={"app-layout"}>
                <Routes>
                    <Route path={"/login"} element={<Login />}/>
                    <Route path={"/home"} element={<Home />}/>
                    <Route path={"/"} element={<Dashboard />}/>

                    <Route path={"/all"} element={
                        <div className={"allPage"}>
                            <AddNewPaper onAdd={(paper) => {
                                setPapers(prevState => [...prevState, paper])
                            }}/>
                            <PaperTable papers={papers}/>
                        </div>
                    }/>

                    <Route path="/paper/:id" element={
                        <PaperDetailPage
                            onDelete={(id) => setPapers(prev => prev.filter(p => p.id !== id))}
                            onUpdate={() => getAllPapers()}
                        />
                    }/>

                    <Route path={"/favorites"}
                           element={<PaperTable papers={getFavoritePapers()} />}/>

                    <Route path={"/group/:groupName"} element={<GroupPage/>}/>

                    <Route path={"/groups/overview"} element={<GroupOverview />}/>
                </Routes>
                </div>
            <Footer/>
        </div>
    )
}