import axios from "axios";
import {useEffect, useState} from "react";

export default function Login() {

    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)

    function login() {
        const host:string =
            window.location.host === "localhost:5173" ?
                "http://localhost:8080"
                :
                window.location.origin;
        window.open(host + "/oauth2/authorization/github", "_self")
    }

    function loadUser() {
        axios.get("/api/auth")
            .then(response => {
                setIsLoggedIn(!response.data.includes("html"))
            })
    }

    useEffect(() => {
        loadUser()
        });

    function logout() {
        const host:string =
            window.location.host === "localhost:5173" ?
                "http://localhost:8080"
                :
                window.location.origin;
        window.open(host + "/logout", "_self")
    }

    return (
        <>
            {!isLoggedIn && <button onClick={login}><span>LOGIN</span></button>}
            {isLoggedIn && <button onClick={logout}><span>LOGOUT</span></button>}
        </>
    )
}