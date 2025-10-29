export type DashboardCardProps = {
    emoji:string,
    count:number,
    title:string
    onClick: () => void
}

export default function DashboardCard(props:Readonly<DashboardCardProps>) {


    return (
        <div className={"stats-card clickable"} onClick={props.onClick}>
            <h1>{props.emoji}</h1>
            <div className={"stats-text"}>
                <h2>{props.count}</h2>
                <h3>{props.title}</h3>
            </div>
        </div>
    )
}