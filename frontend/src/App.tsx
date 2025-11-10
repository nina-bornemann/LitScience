import './App.css'
import NavBar from "./components/NavBar.tsx";
import Footer from "./components/Footer.tsx";
import {Route, Routes} from "react-router-dom";
import Home from "./components/Home.tsx";
import Sidebar from "./components/Sidebar.tsx";
import AppContent from "./components/AppContent.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import ProtectedRoute from "./components/ProtectedRoute.tsx";

export default function App() {

    const [user, setUser] = useState<string | null | undefined>(undefined)

    const loadUser = () => {
        axios.get('/api/auth')
            .then(response => {
                setUser(response.data)
            })
            .catch(error => {
                console.log(error)
                setUser(null)
            })
    }

    useEffect(() => {
        loadUser()
    }, [])

    return (
        <div>
            <NavBar />
            <Sidebar />
                <div className={"app-layout"}>
                <Routes>
                    <Route path={"/"} element={<Home />}/>
                    <Route element={<ProtectedRoute user={user} />}>
                        <Route path={"/*"} element={<AppContent />}/>
                    </Route>
                </Routes>
                </div>
            <Footer/>
        </div>
    )
}