import './NavBar.css'
import {Link, useNavigate} from "react-router-dom";

export default function NavBar() {

    const nav = useNavigate()

    function navToAllPapers() {
        nav("/all")
    }

    return (
        <>
            <header>
                <div className={"logo-container"}>
                    <a href="#" onClick={navToAllPapers} className={"clickable"}>
                        <img src="/src/assets/logo.png" alt="Logo" className="logo" />
                    </a>
                    <a href="#" onClick={navToAllPapers} className={"clickable"}>
                        <h2 className={"logo-text"}>LitScience</h2>
                    </a>
                </div>

                <nav>
                    <Link to={"/home"} className={"clickable"}>  Home  </Link>
                    <Link to={"/all"} className={"clickable"}>  My Collection  </Link>
                </nav>
            </header>
        </>
    )
}