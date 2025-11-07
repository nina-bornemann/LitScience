import "./Home.css"

export default function Home() {

    return (
        <div className={"home"}>
            <div className={"home-title"}>
                <h1>🧪    Welcome to LitScience! </h1>
                <img src={"/Smiley_Cell.png"} alt={"Cell-Smiley-Logo"} className={"smiley"}/>
            </div>
            <div className={"description"}>
                <h2 className={"descriptionHeader"}> ✨ What you can do here: </h2>
                <p>📚 Build your personal paper collection</p>
                <p>🔍 Find and import papers online by DOI</p>
                <p>❤️ Keep your favorite papers close </p>
                <p>🧠 Add notes and tags for every paper</p>
                <p>🗑 Clean up with one-click delete</p>
                <p>🧾 Generate AI-powered summaries and reports</p>
                <br/>
                <h3 className={"descriptionFooter"}> Stay curious, stay organized — and let LitScience take care of the messy part of science reading 💡</h3>
            </div>
        </div>
    )
}