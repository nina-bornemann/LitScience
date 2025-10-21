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
                    <img src={"/src/assets/logo.png"} alt={"Logo"} className={"logo"} onClick={navToAllPapers}/>
                    <h2 onClick={navToAllPapers} className={"logo-text"}>LitScience</h2>
                </div>

                <nav>
                    <Link to={"/all"}>My Collection</Link>
                </nav>
            </header>
        </>
    )
}