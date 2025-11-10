import axios from "axios";
import {useEffect} from "react";

export default function Login() {
    console.log("test")
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
            .then(response => console.log(response.data))
    }

    useEffect(() => {
        loadUser()
        }, []);

    return (

        <>
            <button onClick={login}><span>LOGIN</span> </button>
        </>
    )
}