import './NavBar.css'
import {Link, useNavigate} from "react-router-dom";
import Login from "./Login.tsx";

export default function NavBar() {

    const nav = useNavigate()

    function navToDashboard() {
        nav("/dashboard")
    }

    return (
        <>
            <header>
                <div className={"logo-container"}>
                    <a href="#" onClick={navToDashboard} className={"clickable"}>
                        <img src="/logo.png" alt="Logo" className="logo" />
                    </a>
                    <a href="#" onClick={navToDashboard} className={"clickable"}>
                        <h2 className={"logo-text"}>LitScience</h2>
                    </a>
                </div>

                <nav>
                    <Link to={"/"} className={"clickable"}>  Home  </Link>
                    <Link to={"/dashboard"} className={"clickable"}> Dashboard </Link>
                    <Link to={"/all"} className={"clickable"}>  My Collection  </Link>
                </nav>

                <Login />
            </header>
        </>
    )
}