import './App.css'
import NavBar from "./components/NavBar.tsx";
import Footer from "./components/Footer.tsx";
import PaperTable from "./components/PaperTable.tsx";
import {useState} from "react";
import type {Paper} from "./components/Paper.tsx";

function App() {
    const [papers, setPapers] = useState<Paper[]>([
                                                    {
                                                        "id": "1",
                                                        "doi": "1.2/3",
                                                        "title": "Test1",
                                                        "author": "Tester",
                                                        "year": 2024,
                                                        "group": "Bio",
                                                        "notes": "nice"
                                                    },
                                                    {
                                                        "id": "2",
                                                        "doi": "1.3/5",
                                                        "title": "Test2",
                                                        "author": "Prof",
                                                        "year": 2019,
                                                        "group": "Physics",
                                                        "notes": "cool"
                                                    }
                                                ])
    return (
        <>
            <NavBar/>
            <PaperTable papers={papers}/>
            <Footer/>
        </>
    )
}

export default App
