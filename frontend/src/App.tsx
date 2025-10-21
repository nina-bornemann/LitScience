import './App.css'
import NavBar from "./components/NavBar.tsx";
import Footer from "./components/Footer.tsx";
import PaperTable from "./components/PaperTable.tsx";
import {useEffect, useState} from "react";
import type {Paper} from "./components/Paper.tsx";
import axios from "axios";
import AddNewPaper from "./components/AddNewPaper.tsx";
import {Route, Routes} from "react-router-dom";
import PaperDetailPage from "./components/PaperDetailPage.tsx";

function App() {

    const [papers, setPapers] = useState<Paper[]>([])

    function getAllPapers() {
        axios
            .get("api/paper")
            .then((response) => setPapers(response.data))
            .catch((e) => console.log(e))
    }
    useEffect(() => {getAllPapers()}, [])

    return (
        <>
            <NavBar/>

            <Routes>
                <Route path={"/all"} element={
                    <div>
                        <AddNewPaper onAdd={(paper) => {
                        setPapers(prevState => [...prevState, paper])
                        }}/>
                        <PaperTable papers={papers}/>
                    </div>
                        }/>
            </Routes>

            <Footer/>
        </>
    )
}

export default App
