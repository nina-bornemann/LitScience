import './Footer.css'
import {useNavigate} from "react-router-dom";

export default function Footer() {

    const nav = useNavigate();

    return (
        <>
            <footer>
                <p>
                    © Nina Bornemann 2025
                </p>
                <p>
                    LitScience — Where AI meets curiosity 🔬
                </p>
                <p>
                    <a href="https://github.com/nina-bornemann/LitScience">View source</a>
                </p>
                <button className={"impressum-btn"} onClick={() => nav("/impressum")}>Impressum</button>
            </footer>
        </>
    )
}