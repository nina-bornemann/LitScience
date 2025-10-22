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

function App() {

    const [papers, setPapers] = useState<Paper[]>([])

    function getAllPapers() {
        axios
            .get("/api/paper")
            .then((response) => setPapers(response.data))
            .catch((e) => console.log(e))
    }
    useEffect(() => {getAllPapers()}, [])

    return (
        <div className={"app-layout"}>
            <NavBar/>

            <Routes>
                <Route path={"/home"} element={<Home />}/>
                <Route path={"/all"} element={
                    <div>
                        <AddNewPaper onAdd={(paper) => {
                        setPapers(prevState => [...prevState, paper])
                        }}/>
                        <PaperTable papers={papers}/>
                    </div>
                        }/>
                <Route path="/paper/:id" element={
                    <PaperDetailPage
                        onDelete={(id) => setPapers(prev => prev.filter(p => p.id !== id))}
                    />
                }/>
            </Routes>

            <Footer/>
        </div>
    )
}

export default App
