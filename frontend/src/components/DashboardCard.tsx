export type DashboardCardProps = {
    emoji:string,
    count:number,
    title:string
}

export default function DashboardCard(props:Readonly<DashboardCardProps>) {


    return (
        <div className={"stats-card"}>
            <h1>{props.emoji}</h1>
            <div className={"stats-text"}>
                <h2>{props.count}</h2>
                <h3>{props.title}</h3>
            </div>
        </div>
    )
}