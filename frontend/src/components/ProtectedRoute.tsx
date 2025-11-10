import {Navigate, Outlet} from "react-router-dom";

type ProtectedRouteProps = {
    user:string | undefined | null
}

export default function ProtectedRoute(props:Readonly<ProtectedRouteProps>) {
 /*gb b b b n bhk break */
    if (props.user === undefined) {
        return <div>loading</div>
    }

    return (
        props.user ? <Outlet /> : <Navigate to = {"/"} />
    )
}